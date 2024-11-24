package com.taboola.api.service;

import org.springframework.stereotype.Service;

@Service
public class TestService implements ITestService {
    @Override
    public String Test()
    {
        return "Test";
    }

}
