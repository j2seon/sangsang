package iium.jjs.sansang_back.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RefreshToken {

    private String refreshToken;
    private String memberId;

}
