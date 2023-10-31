package iium.jjs.sansang_back.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private final long expiredTime;

    public RedisService(RedisTemplate<String, String> redisTemplate,
                        @Value("${jwt.refresh-token-expired}") long expiredTime) {
        this.redisTemplate = redisTemplate;
        this.expiredTime = expiredTime * 1000;
    }

    @Transactional
    public void setValues(String key, String value){ //저장
        redisTemplate.opsForValue().set(key, value, expiredTime, TimeUnit.MILLISECONDS);
    }

    @Transactional
    public void setValuesWithTimeout(String key, String value, long timeout){
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    public String getValues(String key){
        return redisTemplate.opsForValue().get(key);
    }

    @Transactional
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

}
