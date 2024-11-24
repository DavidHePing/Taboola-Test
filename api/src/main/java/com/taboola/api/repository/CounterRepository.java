package com.taboola.api.repository;

import com.taboola.api.model.EventCounter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository("counterRepository")
public class CounterRepository implements ICounterRepository {

    private final SessionFactory sessionFactory;

    public CounterRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<EventCounter> getEventCountersByTimestamp(Timestamp timeBucket) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM EventCounter e WHERE e.timeBucket = :timeBucket";
            Query<EventCounter> query = session.createQuery(hql, EventCounter.class);
            query.setParameter("timeBucket", timeBucket);
            return query.getResultList();
        } catch (Exception e) {
            // Log and rethrow the exception
            throw new RuntimeException("Failed to fetch event counters by timestamp", e);
        }
    }

    @Override
    public EventCounter getEventCounterByEventId(Timestamp timeBucket, Long eventId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM EventCounter e WHERE e.timeBucket = :timeBucket AND e.eventId = :eventId";
            Query<EventCounter> query = session.createQuery(hql, EventCounter.class);
            query.setParameter("timeBucket", timeBucket);
            query.setParameter("eventId", eventId);
            return query.uniqueResult();
        } catch (Exception e) {
            // Log and rethrow the exception
            throw new RuntimeException("Failed to fetch event counter by event ID", e);
        }
    }
}