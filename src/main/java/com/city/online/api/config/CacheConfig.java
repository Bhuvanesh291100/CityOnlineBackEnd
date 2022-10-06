package com.city.online.api.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {

    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS);
        caffeineCacheManager.setCaffeine(caffeine);
        caffeineCacheManager.setAllowNullValues(false);
        return null;
    }

    @Cacheable(value = "testDTOS", key = "#empId", unless = "#result == null")
    public String test() {
        return null;
    }

    @Scheduled(fixedRate = Timer.ONE_DAY)
    @CacheEvict(value = {"testDTOS"}, allEntries = true)
    public void testCacheEvict() {
        log.info("testDTOS Cache Cleared");
    }
}
