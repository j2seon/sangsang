package iium.jjs.sansang_back.member.dto.response;

import iium.jjs.sansang_back.member.dto.request.JoinDto;
import iium.jjs.sansang_back.member.entity.Member;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

//member 조회용  dto
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public MemberDto(Member member){
        this.memberId = member.getMemberId();
        this.memberPwd = member.getMemberPwd();
        this.memberName = member.getMemberName();
        this.zipCode = member.getAddress().getZipCode();
        this.address = member.getAddress().getAddress();
        this.addressDetail = member.getAddress().getAddressDetail();
        this.profile =  member.getProfile();
        this.auth = member.getAuthority().toString();
        this.createdAt = member.getCreatedAt();
        this.deletedAt = member.getDeletedAt();
    }
}
