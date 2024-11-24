package com.taboola.api.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/counters")
public class CountersController {
    @GetMapping("/time/{timestamp}")
    public String getCountersByTimestamp(@PathVariable("timestamp") String timestamp) {

        return timestamp.toString();
    }

    @GetMapping("/time/{timestamp}/eventId/{eventId}")
    public String getEventDetailsByTimestampAndEventId(
            @PathVariable("timestamp") String timestamp,
            @PathVariable("eventId") String eventId) {

        return timestamp.toString() + eventId.toString();
    }

}
