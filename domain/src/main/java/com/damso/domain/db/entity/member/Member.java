package com.damso.domain.db.entity.member;

import com.damso.core.constant.member.MemberRoleType;
import com.damso.core.constant.member.MemberSocialAccountType;
import com.damso.core.constant.member.MemberStatusType;
import com.damso.domain.db.converter.EmailConverter;
import com.damso.domain.db.entity.base.CommonTime;
import com.damso.domain.db.entity.subscribe.SubscriptionPlan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor
public class Member extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @Convert(converter = EmailConverter.class)
    @Column(name = "EMAIL", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String email;

    @Column(name = "NAME", columnDefinition = "VARCHAR(100)", nullable = false)
    private String name;

    @Column(name = "PASSWORD", columnDefinition = "VARCHAR(255)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", columnDefinition = "VARCHAR(20)", nullable = false)
    private MemberRoleType role;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", columnDefinition = "VARCHAR(20)", nullable = false)
    private MemberStatusType status;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberSocialAccount> socialAccounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubscriptionPlan> managedPlans = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberSubscription> subscriptions = new ArrayList<>();

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

    public boolean isActive() {
        return this.status.equals(MemberStatusType.ACTIVE);
    }

    public boolean isAdmin() {
        return this.role.equals(MemberRoleType.ADMIN);
    }
}
