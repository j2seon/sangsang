package iium.jjs.sansang_back.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash("refreshToken")
public class RefreshToken {

    @Id
    private String memberId;
    private String refreshToken;
    private LocalDateTime expiredTime;
}
