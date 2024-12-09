package com.damso.user.service.member.auth;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.core.utils.crypto.CryptoUtil;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import com.damso.user.service.member.auth.command.SignupCommand;
import com.damso.user.service.member.auth.command.SnsInfoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberRegisterImpl implements MemberRegister {
    private final MemberRepository memberRepository;
    private final CryptoUtil cryptoUtil;

    @Override
    public void checkEmailDuplication(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.MEMBER_EMAIL_DUPLICATED);
        }
    }

    @Override
    public void signup(SignupCommand command) {
        checkEmailDuplication(command.email());
        memberRepository.save(Member.ofEmailUser(
                command.email(),
                command.name(),
                cryptoUtil.hashPassword(command.password()))
        );
    }

    @Override
    public Member signup(SnsInfoCommand command) {
        checkEmailDuplication(command.email());
        if (command.provider() == null || !StringUtils.hasText(command.providerAccountId())) {
            throw new BusinessException(ErrorCode.OAUTH_INFO_NOT_VALID);
        }

        return memberRepository.save(Member.ofSnsUser(
                command.email(),
                command.name(),
                command.provider(),
                command.providerAccountId())
        );
    }
}
