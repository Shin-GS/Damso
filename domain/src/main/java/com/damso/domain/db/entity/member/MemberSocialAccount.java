package com.damso.domain.db.entity.member;

import com.damso.core.constant.member.MemberSocialAccountType;
import com.damso.domain.db.converter.PrivacyConverter;
import com.damso.domain.db.entity.base.CommonTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEMBER_SOCIAL_ACCOUNT",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"SOCIAL_TYPE", "SOCIAL_ID"}
        )
)
@Getter
@Setter
@NoArgsConstructor
public class MemberSocialAccount extends CommonTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_SOCIAL_ACCOUNT_NO", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", columnDefinition = "BIGINT", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "SOCIAL_TYPE", columnDefinition = "VARCHAR(50)", nullable = false)
    private MemberSocialAccountType provider;

    @Convert(converter = PrivacyConverter.class)
    @Column(name = "SOCIAL_ID", columnDefinition = "VARCHAR(255)", nullable = false)
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
