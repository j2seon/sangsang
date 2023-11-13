package iium.jjs.sansang_back;

import iium.jjs.sansang_back.common.service.RedisService;
import iium.jjs.sansang_back.member.dto.request.JoinDto;
import iium.jjs.sansang_back.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
@Slf4j
@Transactional
class SansangBackApplicationTests {

    @Autowired
    private MemberService memberService;


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
    @Rollback(value = false)
    void current() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

        System.out.println(simpleDateFormat.format(new Date(System.currentTimeMillis() + 43200000)));
        System.out.println(simpleDateFormat.format(System.currentTimeMillis()));

    }



}
