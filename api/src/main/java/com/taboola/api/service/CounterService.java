package com.taboola.api.service;

import com.taboola.api.model.EventCounter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CounterService {
    public Map<Long, Long> getCountersByTimestamp(String timestamp) {
        Timestamp timeBucket = Timestamp.valueOf(LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyyMMddHHmm")));

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(EventCounter.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        List<EventCounter> eventCounters = null;

        try (Session session = sessionFactory.openSession()) {

            String hql = "FROM EventCounter e WHERE e.timeBucket = :timeBucket";
            Query<EventCounter> query = session.createQuery(hql, EventCounter.class);
            query.setParameter("timeBucket", timeBucket);

            eventCounters = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }

        return eventCounters.stream()
                .collect(Collectors.toMap(
                        EventCounter::getEventId,
                        EventCounter::getCount
                ));
    }

    public Long getEventDetailsByTimestampAndEventId(String timestamp, String eventId) {

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(EventCounter.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        EventCounter eventCounter = null;

        try (Session session = sessionFactory.openSession()) {

            String hql = "FROM EventCounter e WHERE e.timeBucket = :timeBucket and e.eventId = :eventId";
            Query<EventCounter> query = session.createQuery(hql, EventCounter.class);
            query.setParameter("timeBucket", Timestamp.valueOf(LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyyMMddHHmm"))));
            query.setParameter("eventId", Long.parseLong(eventId));

            eventCounter = query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }

        return eventCounter == null ? 0 : eventCounter.getCount();
    }
}
