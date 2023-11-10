package iium.jjs.sansang_back.member.dto;

import iium.jjs.sansang_back.member.entity.Member;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MemberDetailImpl implements UserDetails {

    private Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> member.getAuthority().toString());
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getMemberPwd();
    }

    @Override
    public String getUsername() {
        return member.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정 만료여부
        return this.getMember().getDeletedAt() == null;
    }

    @Override
    public boolean isAccountNonLocked() { // 계정이 잠겨있는지
        return this.getMember().getDeletedAt() == null;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 패스워드가 만료되었는지
        return true;
    }

    @Override
    public boolean isEnabled() { // 사용가능한 계정인지
        return this.getMember().getDeletedAt() == null;
    }
}
