package com.taboola.api;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class Controller {

    @GetMapping("/currentTime")
    public long time() {
        return Instant.now().toEpochMilli();
    }

}
