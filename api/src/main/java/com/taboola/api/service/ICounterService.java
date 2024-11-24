package com.taboola.api.service;

import java.util.Map;

public interface ICounterService {
    Map<Long, Long> getCountersByTimestamp(String timestamp);

    Long getEventDetailsByTimestampAndEventId(String timestamp, String eventId);
}
