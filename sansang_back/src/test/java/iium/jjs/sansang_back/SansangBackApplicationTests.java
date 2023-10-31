package iium.jjs.sansang_back;

import iium.jjs.sansang_back.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
@Slf4j
class SansangBackApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    void string_redis() {
        String key = "string_redis";
        String value = "string";
        redisService.setValuesWithTimeout(key, value, 10);

        log.info("### Redis Key => {} | value => {}", key, redisService.getValues(key));
    }


    @Test
    void current() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

        System.out.println(simpleDateFormat.format(new Date(System.currentTimeMillis() + 43200000)));
        System.out.println(simpleDateFormat.format(System.currentTimeMillis()));

    }


}
