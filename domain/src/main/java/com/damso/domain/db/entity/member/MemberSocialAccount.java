package com.damso.domain.db.entity.member;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.domain.db.converter.PrivacyConverter;
import com.damso.domain.db.entity.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MemberSocialAccount",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"social_provider", "social_account"}
        )
)
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

    @Column(name = "social_account", nullable = false)
    @Convert(converter = PrivacyConverter.class)
    private String providerAccountId;

    public static MemberSocialAccount of(Member member,
                                         MemberSocialAccountType provider,
                                         String providerAccountId) {
        MemberSocialAccount memberSocialAccount = new MemberSocialAccount();
        memberSocialAccount.member = member;
        memberSocialAccount.provider = provider;
        memberSocialAccount.providerAccountId = providerAccountId;
        return memberSocialAccount;
    }
}
