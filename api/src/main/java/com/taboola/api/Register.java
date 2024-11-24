package com.taboola.api;

import com.taboola.api.model.EventCounter;
import com.taboola.api.repository.CounterRepository;
import com.taboola.api.repository.ICounterRepository;
import com.taboola.api.service.CounterService;
import com.taboola.api.service.ICounterService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Register {
    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(EventCounter.class)
                .buildSessionFactory();
    }
}
