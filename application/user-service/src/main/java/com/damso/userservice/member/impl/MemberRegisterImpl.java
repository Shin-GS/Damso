package com.damso.userservice.member.impl;

import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.core.utils.crypto.CryptoUtil;
import com.damso.storage.entity.member.Member;
import com.damso.storage.repository.member.MemberRepository;
import com.damso.storage.repository.member.MemberSocialAccountRepository;
import com.damso.userservice.auth.request.EmailSignupRequest;
import com.damso.userservice.member.MemberRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberRegisterImpl implements MemberRegister {
    private final MemberRepository memberRepository;
    private final MemberSocialAccountRepository memberSocialAccountRepository;
    private final CryptoUtil cryptoUtil;

    @Override
    public void checkEmailDuplication(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.MEMBER_EMAIL_DUPLICATED);
        }
    }

    @Override
    public void checkSNSDuplication(MemberSocialAccountType provider,
                                    String providerAccountId) {
        if (memberSocialAccountRepository.existsByProviderAndProviderAccountId(provider, providerAccountId)) {
            throw new BusinessException(ErrorCode.MEMBER_SNS_DUPLICATED);
        }
    }

    @Override
    public Long signup(EmailSignupRequest request) {
        checkEmailDuplication(request.email());
        return memberRepository.save(Member.ofEmailUser(
                request.name(),
                request.email(),
                cryptoUtil.hashPassword(request.password()))
        ).getId();
    }

    @Override
    public Long signup(MemberSocialAccountType provider,
                       String providerAccountId,
                       String email,
                       String name) {
        checkSNSDuplication(provider, providerAccountId);
        return memberRepository.save(Member.ofSnsUser(
                name,
                email,
                provider,
                providerAccountId)
        ).getId();
    }
}
