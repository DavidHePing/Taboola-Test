package com.taboola.api.repository;

import com.taboola.api.model.EventCounter;
import com.taboola.api.utils.IJsonUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository("counterDecorator")
public class CounterDecorator implements ICounterRepository {
    private final ICounterRepository repository;
    private final JedisPool jedisPool;
    private final IJsonUtil jsonUtil;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    public CounterDecorator(@Qualifier("counterRepository") ICounterRepository repository, JedisPool jedisPool, IJsonUtil jsonUtil) {
        this.repository = repository;
        this.jedisPool = jedisPool;
        this.jsonUtil = jsonUtil;
    }

    @Override
    public List<EventCounter> getEventCountersByTimestamp(Timestamp timeBucket) {
        String cacheKey = "eventCounters:timeBucket:" + simpleDateFormat.format(timeBucket);

        try (Jedis jedis = jedisPool.getResource()) {
            String cachedValue = jedis.get(cacheKey);
            if (cachedValue != null) {
                return jsonUtil.fromJsonList(cachedValue, EventCounter.class);
            }

            List<EventCounter> result = repository.getEventCountersByTimestamp(timeBucket);

            jedis.set(cacheKey, jsonUtil.toJson(result));
            jedis.expire(cacheKey, 300);

            return result;
        }
    }

    @Override
    public EventCounter getEventCounterByEventId(Timestamp timeBucket, Long eventId) {
        String cacheKey = "eventCounter:timeBucket:" + simpleDateFormat.format(timeBucket) + ":eventId:" + eventId;

        try (Jedis jedis = jedisPool.getResource()) {
            String cachedValue = jedis.get(cacheKey);
            if (cachedValue != null) {
                return jsonUtil.fromJson(cachedValue, EventCounter.class);
            }

            EventCounter result = repository.getEventCounterByEventId(timeBucket, eventId);

            jedis.set(cacheKey, jsonUtil.toJson(result));
            jedis.expire(cacheKey, 300);

            return result;
        }
    }
}
