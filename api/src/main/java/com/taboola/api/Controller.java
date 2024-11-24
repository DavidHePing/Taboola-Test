package com.taboola.api;

import java.time.Instant;

import com.taboola.api.service.ITestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class Controller {

    private final ITestService testService;

    public Controller(ITestService testService) {
        this.testService = testService;
    }

    @GetMapping("/currentTime")
    public long time() {
        return Instant.now().toEpochMilli();
    }

}
