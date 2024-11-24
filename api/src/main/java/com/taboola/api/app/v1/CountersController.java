package com.taboola.api.app.v1;

import com.taboola.api.model.EventCounter;
import com.taboola.api.service.CounterService;
import com.taboola.api.service.ICounterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/counters")
public class CountersController {
    private final ICounterService counterService;

    public CountersController(ICounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/time/{timestamp}")
    public Map<Long, Long> getCountersByTimestamp(@PathVariable("timestamp") String timestamp) {
        return counterService.getCountersByTimestamp(timestamp);
    }

    @GetMapping("/time/{timestamp}/eventId/{eventId}")
    public Long getEventDetailsByTimestampAndEventId(
            @PathVariable("timestamp") String timestamp,
            @PathVariable("eventId") String eventId) {

        return counterService.getEventDetailsByTimestampAndEventId(timestamp, eventId);
    }

}
