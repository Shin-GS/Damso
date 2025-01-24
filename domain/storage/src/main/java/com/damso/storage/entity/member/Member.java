package com.damso.storage.entity.member;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.core.enums.member.MemberStatusType;
import com.damso.storage.converter.EmailConverter;
import com.damso.storage.entity.base.CommonTime;
import com.damso.storage.entity.subscribe.SubscriptionPlan;
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

    @Column(name = "NAME", columnDefinition = "VARCHAR(100)")
    private String name;

    @Convert(converter = EmailConverter.class)
    @Column(name = "EMAIL", columnDefinition = "VARCHAR(255)", nullable = false, unique = true)
    private String email;

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

    public static Member ofEmailUser(String name,
                                     String email,
                                     String password) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setRole(MemberRoleType.USER);
        member.setStatus(MemberStatusType.ACTIVE);
        return member;
    }

    public static Member ofSnsUser(String name,
                                   String email,
                                   MemberSocialAccountType provider,
                                   String providerAccountId) {
        Member member = new Member();
        member.setName(name);
        member.setEmail(email);
        member.linkSns(provider, providerAccountId);
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
