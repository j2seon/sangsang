package iium.jjs.sansang_back.member.dto.response;

import iium.jjs.sansang_back.member.dto.request.JoinDto;
import iium.jjs.sansang_back.member.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

//member 조회용  dto
@Getter
@Setter
@ToString
public class MemberDto {

    private String memberId;

    private String memberPwd;

    private String memberName;

    private String zipCode;

    private String address;

    private String addressDetail;

    private String profile;

    private String auth;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    @Builder
    public MemberDto(String memberId, String memberPwd, String memberName, String zipCode, String address, String addressDetail, String profile, String auth, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberName = memberName;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.profile = profile;
        this.auth = auth;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
