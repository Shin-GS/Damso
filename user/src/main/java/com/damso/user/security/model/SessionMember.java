package com.damso.user.security.model;

import com.damso.domain.db.entity.member.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
public class SessionMember extends User {
    private static final String ROLE_PREFIX = "ROLE_";
    private final Long memberId;
    private final String name;
    private final String email;

    public SessionMember(Member member) {
        super(StringUtils.hasText(member.getEmail()) ? member.getEmail() : "",
                StringUtils.hasText(member.getPassword()) ? member.getPassword() : "",
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + member.getRole())));
        this.memberId = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }

    @Override
    public String toString() {
        return "SessionMember{" +
                "memberId=" + memberId +
                ", name=" + name +
                ", email=" + email +
                '}';
    }
}
