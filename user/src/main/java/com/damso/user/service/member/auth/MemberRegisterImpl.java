package com.damso.user.service.member.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.core.utils.crypto.CryptoUtil;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import com.damso.domain.db.repository.member.MemberSocialAccountRepository;
import com.damso.user.service.member.auth.command.EmailSignupCommand;
import com.damso.user.service.member.auth.command.SNSSignupCommand;
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
    public void checkSNSDuplication(MemberSocialAccountType provider, String providerAccountId) {
        if (memberSocialAccountRepository.existsByProviderAndProviderAccountId(provider, providerAccountId)) {
            throw new BusinessException(ErrorCode.MEMBER_SNS_DUPLICATED);
        }
    }

    @Override
    public void signup(EmailSignupCommand command) {
        checkEmailDuplication(command.email());
        memberRepository.save(Member.ofEmailUser(
                command.email(),
                command.name(),
                cryptoUtil.hashPassword(command.password()))
        );
    }

    @Override
    public void signup(SNSSignupCommand command) {
        checkSNSDuplication(command.provider(), command.providerAccountId());
        memberRepository.save(Member.ofSnsUser(
                command.email(),
                command.name(),
                command.provider(),
                command.providerAccountId())
        );
    }
}
