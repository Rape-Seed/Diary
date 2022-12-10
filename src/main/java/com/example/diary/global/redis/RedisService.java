package com.example.diary.global.redis;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RedisService {

    public static final String BLACKLIST = "BLACKLIST ";
    private final RedisTemplate redisTemplate;

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public String findBlackList(String token) {
        return (String) redisTemplate.opsForValue().get(BLACKLIST + token.substring(0, 10));
    }

    public void setDataWithExpiration(String key, String value, Long time) {
        if (this.getData(key) != null) {
            this.deleteData(key);
        }
        Duration expireDuration = Duration.ofMillis(time);
        redisTemplate.opsForValue().set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
