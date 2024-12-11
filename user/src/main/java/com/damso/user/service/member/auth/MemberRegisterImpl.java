package com.damso.user.service.member.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.core.utils.crypto.CryptoUtil;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import com.damso.domain.db.repository.member.MemberSocialAccountRepository;
import com.damso.user.client.auth.OAuth2ClientImpl;
import com.damso.user.service.member.auth.command.EmailSignupCommand;
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
    private final OAuth2ClientImpl oAuth2Client;

    @Override
    public void checkEmailDuplication(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.MEMBER_EMAIL_DUPLICATED);
        }
    }

    @Override
    public void checkSNSDuplication(MemberSocialAccountType provider, String providerAccountId) {
        if (memberSocialAccountRepository.existsByProviderAndProviderAccountId(provider, providerAccountId)) {
            throw new BusinessException(ErrorCode.MEMBER_SNS_DUPLICATED);
        }
    }

    @Override
    public Long signup(EmailSignupCommand command) {
        checkEmailDuplication(command.email());
        return memberRepository.save(Member.ofEmailUser(
                command.email(),
                command.name(),
                cryptoUtil.hashPassword(command.password()))
        ).getId();
    }

    @Override
    public Long signup(MemberSocialAccountType provider, String providerAccountId, String email, String name) {
        checkSNSDuplication(provider, providerAccountId);
        return memberRepository.save(Member.ofSnsUser(
                provider,
                providerAccountId,
                email,
                name)
        ).getId();
    }
}
