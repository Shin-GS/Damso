package com.damso.user.filter;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.core.utils.crypto.CryptoUtil;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import com.damso.domain.db.repository.member.MemberSocialAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomAuthenticationManagerImpl implements CustomAuthenticationManager {
    private final MemberRepository memberRepository;
    private final MemberSocialAccountRepository memberSocialAccountRepository;
    private final CryptoUtil cryptoUtil;

    @Override
    public Long authenticateByEmail(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        if (cryptoUtil.matchesPassword(password, member.getPassword())) {
            return member.getId();
        }

        throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCHED);
    }

    @Override
    public Long authenticateBySns(MemberSocialAccountType provider, String snsToken) {
        return memberSocialAccountRepository.findByProviderAndProviderAccountId(provider, snsToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND))
                .getMember()
                .getId();
    }
}
