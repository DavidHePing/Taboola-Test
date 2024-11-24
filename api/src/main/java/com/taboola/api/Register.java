package com.taboola.api;

import com.taboola.api.model.EventCounter;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class Register {
    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(EventCounter.class)
                .buildSessionFactory();
    }

    @Bean
    public JedisPool jedisPool() {
        return new JedisPool("host.docker.internal", 32101);
    }
}
