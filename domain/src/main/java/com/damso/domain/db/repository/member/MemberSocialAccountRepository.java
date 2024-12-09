package com.damso.domain.db.repository.member;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.domain.db.entity.member.MemberSocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSocialAccountRepository extends JpaRepository<MemberSocialAccount, Long> {
    boolean existsByProviderAndProviderAccountId(MemberSocialAccountType provider,
                                                 String providerAccountId);

    Optional<MemberSocialAccount> findByProviderAndProviderAccountId(MemberSocialAccountType provider,
                                                                     String providerAccountId);
}
