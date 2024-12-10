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
@Table(name = "Member")
@Getter
@Setter
@NoArgsConstructor
public class Member extends CommonTime {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_email", unique = true, nullable = false)
    @Convert(converter = EmailConverter.class)
    private String email;

    @Column(name = "member_name", nullable = false)
    private String name;

    @Column(name = "member_password")
    private String password;

    @Column(name = "member_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRoleType role;

    @Column(name = "member_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberStatusType status;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberSocialAccount> socialAccounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberNotificationSetting> notificationSettings = new LinkedHashSet<>();

    public Member(String email,
                  String name,
                  MemberRoleType role) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = MemberStatusType.ACTIVE;
    }

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

    public static Member ofSnsUser(String email,
                                   String name,
                                   MemberSocialAccountType provider,
                                   String providerAccountId) {
        Member member = new Member();
        member.setEmail(email);
        member.setName(name);
        member.setRole(MemberRoleType.USER);
        member.setStatus(MemberStatusType.ACTIVE);
        member.linkSns(provider, providerAccountId);
        return member;
    }

    public void update(String email,
                       String name,
                       MemberRoleType role,
                       MemberStatusType status) {
        this.email = email;
        this.name = name;
        this.role = role;
        this.status = status;
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
        return this.status == MemberStatusType.ACTIVE;
    }
}
