package com.taboola.api.app.v1;

import com.taboola.api.model.EventCounter;
import com.taboola.api.service.CounterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/v1/api/counters")
public class CountersController {
    @GetMapping("/time/{timestamp}")
    public Map<Long, Long> getCountersByTimestamp(@PathVariable("timestamp") String timestamp) {
        CounterService service = new CounterService();

        return service.getCountersByTimestamp(timestamp);
    }

    @GetMapping("/time/{timestamp}/eventId/{eventId}")
    public Long getEventDetailsByTimestampAndEventId(
            @PathVariable("timestamp") String timestamp,
            @PathVariable("eventId") String eventId) {

        CounterService service = new CounterService();

        return service.getEventDetailsByTimestampAndEventId(timestamp, eventId);
    }

}
