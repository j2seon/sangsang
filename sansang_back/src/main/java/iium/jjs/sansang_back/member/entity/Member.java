package iium.jjs.sansang_back.member.entity;

import iium.jjs.sansang_back.member.dto.request.JoinDto;
import iium.jjs.sansang_back.member.dto.request.MemberInfoDto;
import iium.jjs.sansang_back.member.dto.response.MemberDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_member")
@Getter
@Setter // 지양하는 것이 좋은데..
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @Column(name = "MEMBER_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberCode;

    @Column(name = "MEMBER_PWD")
    private String memberPwd;

    @Column(name = "MEMBER_NAME")
    private String memberName;

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEMBER_AUTH")
    private Authority authority; // 권한 enum -> 추후 여러 권한을 가져할 경우 테이블로 분리해야함

    @Embedded
    private Address address; // 주소 임베디드 타입 정의

    @Column(name = "MEMBER_CREATEDAT")
    private LocalDateTime createdAt;

    @Column(name = "MEMBER_DELETEDAT")
    private LocalDateTime deletedAt;

    @Column(name = "MEMBER_PROFILE")
    private String profile;


}
