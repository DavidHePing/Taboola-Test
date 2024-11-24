package com.taboola.spark.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "event_counters")
@IdClass(EventCounterKey.class)
public class EventCounter {

    @Id
    @Column(name = "time_bucket", nullable = false)
    private Timestamp timeBucket;

    @Id
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "count", nullable = false)
    private Long count;

    // Getters and Setters
    public Timestamp getTimeBucket() {
        return timeBucket;
    }

    public void setTimeBucket(Timestamp timeBucket) {
        this.timeBucket = timeBucket;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}

// Composite Key Class
class EventCounterKey implements Serializable {
    private Timestamp timeBucket;
    private Long eventId;

    // Default constructor
    public EventCounterKey() {}

    // Constructor with fields
    public EventCounterKey(Timestamp timeBucket, Long eventId) {
        this.timeBucket = timeBucket;
        this.eventId = eventId;
    }

    // hashCode and equals
    @Override
    public int hashCode() {
        return timeBucket.hashCode() + eventId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EventCounterKey that = (EventCounterKey) obj;
        return timeBucket.equals(that.timeBucket) && eventId.equals(that.eventId);
    }
}
