package com.taboola.spark;

import java.sql.Timestamp;

import com.taboola.spark.model.EventCounter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.apache.spark.api.java.function.ForeachPartitionFunction;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;


public class SparkApp {

    public static void main(String[] args) throws StreamingQueryException {
        //set hadoop env
        System.setProperty("hadoop.home.dir", "E:\\hadoop");

        SparkSession spark = SparkSession.builder().master("local[4]").getOrCreate();

        // generate events
        // each event has an id (eventId) and a timestamp
        // an eventId is a number between 0 an 99
        Dataset<Row> events = getEvents(spark);

        // REPLACE THIS CODE
        // The spark stream continuously receives messages. Each message has 2 fields:
        // * timestamp
        // * event id (valid values: from 0 to 99)
        //
        // The spark stream should collect, in the database, for each time bucket and event id, a counter of all the messages received.
        // The time bucket has a granularity of 1 minute.
        Dataset<Row> aggregatedEvents = events
                .withWatermark("timestamp", "5 minutes") // Allow up to 5 minutes of late data
                .groupBy(
                        functions.window(functions.col("timestamp"), "1 minute"),
                        functions.col("eventId")
                )
                .count()
                .withColumn("time_bucket", functions.col("window.start"))
                .select("time_bucket", "eventId", "count");

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(EventCounter.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        aggregatedEvents.writeStream()
            .foreachBatch((dataset, batchId) -> {
                dataset.foreachPartition((ForeachPartitionFunction<Row>) partition -> {
                    try (Session session = sessionFactory.openSession()) {
                        Transaction transaction = session.beginTransaction();

                        // Iterate through the partition and insert/update each row
                        while (partition.hasNext()) {
                            Row row = partition.next();

                            EventCounter eventCounter = new EventCounter();
                            eventCounter.setTimeBucket(Timestamp.valueOf(row.getAs("time_bucket").toString()));
                            eventCounter.setEventId(row.getAs("eventId"));
                            eventCounter.setCount(row.getAs("count"));

                            // Use saveOrUpdate to insert or update
                            session.saveOrUpdate(eventCounter);
                        }

                        transaction.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            })
            .outputMode("update") // Use append or update mode as needed
            .start();


        // the stream will run forever
        spark.streams().awaitAnyTermination();
    }

    private static Dataset<Row> getEvents(SparkSession spark) {
        return spark
                .readStream()
                .format("rate")
                .option("rowsPerSecond", "10000")
                .load()
                .withColumn("eventId", functions.rand(System.currentTimeMillis()).multiply(functions.lit(100)).cast(DataTypes.LongType))
                .select("eventId", "timestamp");
    }

}
