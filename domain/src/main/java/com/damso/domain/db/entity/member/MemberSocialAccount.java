package com.damso.domain.db.entity.member;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.domain.db.converter.PrivacyConverter;
import com.damso.domain.db.entity.CommonTime;
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
    @Column(name = "MEMBER_SOCIAL_ACCOUNT_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_NO", nullable = false)
    private Member member;

    @Column(name = "SOCIAL_TYPE", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private MemberSocialAccountType provider;

    @Column(name = "SOCIAL_ID", nullable = false)
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
