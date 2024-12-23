package com.damso.domain.db.entity.member;

import com.damso.core.constant.MemberRoleType;
import com.damso.core.constant.MemberSocialAccountType;
import com.damso.core.constant.MemberStatusType;
import com.damso.domain.db.converter.EmailConverter;
import com.damso.domain.db.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor
public class Member extends CommonTime {
    @Id
    @Column(name = "MEMBER_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", unique = true, nullable = false)
    @Convert(converter = EmailConverter.class)
    private String email;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRoleType role;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatusType status;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberSocialAccount> socialAccounts = new LinkedHashSet<>();

    public static Member ofEmailUser(String email,
                                     String name,
                                     String password) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setPassword(password);
        member.setRole(MemberRoleType.USER);
        member.setStatus(MemberStatusType.ACTIVE);
        return member;
    }

    public static Member ofSnsUser(MemberSocialAccountType provider,
                                   String providerAccountId,
                                   String email,
                                   String name) {
        Member member = new Member();
        member.linkSns(provider, providerAccountId);
        member.setEmail(email);
        member.setName(name);
        member.setRole(MemberRoleType.USER);
        member.setStatus(MemberStatusType.ACTIVE);
        return member;
    }

    private void linkSns(MemberSocialAccountType provider,
                         String providerAccountId) {
        if (this.socialAccounts.stream()
                .filter(socialAccount -> socialAccount.getProvider().equals(provider) && socialAccount.getProviderAccountId().equals(providerAccountId))
                .count() > 1) {
            return;
        }

        this.socialAccounts.add(MemberSocialAccount.of(this,
                provider,
                providerAccountId));
    }
}
