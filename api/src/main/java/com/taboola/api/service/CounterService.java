package com.taboola.api.service;

import com.taboola.api.model.EventCounter;

import com.taboola.api.repository.ICounterRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CounterService implements ICounterService {
    private final ICounterRepository counterRepository;

    public CounterService(@Qualifier("counterDecorator") ICounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    @Override
    public Map<Long, Long> getCountersByTimestamp(String timestamp) {
        List<EventCounter> eventCounters = counterRepository.getEventCountersByTimestamp(
                Timestamp.valueOf(LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyyMMddHHmm"))));

        return eventCounters.stream()
                .collect(Collectors.toMap(
                        EventCounter::getEventId,
                        EventCounter::getCount
                ));
    }

    @Override
    public Long getEventDetailsByTimestampAndEventId(String timestamp, String eventId) {
        EventCounter eventCounter = counterRepository.getEventCounterByEventId(
                Timestamp.valueOf(LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyyMMddHHmm"))), Long.parseLong(eventId));

        return eventCounter == null ? 0 : eventCounter.getCount();
    }
}
