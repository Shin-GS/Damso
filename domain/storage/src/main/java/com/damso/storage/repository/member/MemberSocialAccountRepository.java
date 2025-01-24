package com.damso.storage.repository.member;

import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.storage.entity.member.MemberSocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSocialAccountRepository extends JpaRepository<MemberSocialAccount, Long> {
    boolean existsByProviderAndProviderAccountId(MemberSocialAccountType provider,
                                                 String providerAccountId);

    Optional<MemberSocialAccount> findByProviderAndProviderAccountId(MemberSocialAccountType provider,
                                                                     String providerAccountId);
}
