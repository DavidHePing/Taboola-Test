package com.taboola.api.repository;

import com.taboola.api.model.EventCounter;

import java.sql.Timestamp;
import java.util.List;

public interface ICounterRepository {
    List<EventCounter> getEventCountersByTimestamp(Timestamp timeBucket);

    EventCounter getEventCounterByEventId(Timestamp timeBucket, Long eventId);
}
