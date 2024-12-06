package com.damso.repository.db.entity.member;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.repository.db.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MemberSocialAccount")
@Getter
@Setter
@NoArgsConstructor
public class MemberSocialAccount extends CommonTime {
    @Id
    @Column(name = "member_social_account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "social_provider", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberSocialAccountType provider;

    @Column(name = "social_account", nullable = false, unique = true)
    private String providerAccountId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;
}
