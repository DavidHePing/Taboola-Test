package com.taboola.spark;

import java.util.concurrent.TimeUnit;

import org.apache.spark.sql.functions;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.streaming.Trigger;
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
        events.printSchema();

        // REPLACE THIS CODE
        // The spark stream continuously receives messages. Each message has 2 fields:
        // * timestamp
        // * event id (valid values: from 0 to 99)
        //
        // The spark stream should collect, in the database, for each time bucket and event id, a counter of all the messages received.
        // The time bucket has a granularity of 1 minute.
        events
                .writeStream()
                .format("console")
                .trigger(Trigger.ProcessingTime(10, TimeUnit.SECONDS))
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
