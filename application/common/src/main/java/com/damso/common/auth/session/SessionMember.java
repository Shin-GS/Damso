package com.damso.common.auth.session;

import com.damso.storage.entity.member.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Getter
public class SessionMember extends User {
    public static final String ROLE_PREFIX = "ROLE_";
    private final Long memberId;
    private final String name;
    private final String email;

    public SessionMember(Member member) {
        super(StringUtils.hasText(member.getEmail()) ? member.getEmail() : "",
                StringUtils.hasText(member.getPassword()) ? member.getPassword() : "",
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + member.getRole().name())));
        this.memberId = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!super.equals(o) || (getClass() != o.getClass())) {
            return false;
        }

        SessionMember sessionMember = (SessionMember) o;
        return Objects.equals(this.memberId, sessionMember.memberId) &&
                Objects.equals(this.name, sessionMember.name) &&
                Objects.equals(this.email, sessionMember.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.memberId, this.name, this.email);
    }

    @Override
    public String toString() {
        return "SessionMember{" +
                "memberId=" + this.memberId +
                ", name='" + this.name +
                ", email='" + this.email +
                '}';
    }
}
