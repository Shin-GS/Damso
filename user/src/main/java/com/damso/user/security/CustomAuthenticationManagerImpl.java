package com.damso.user.security;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.exception.BusinessException;
import com.damso.core.utils.crypto.CryptoUtil;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomAuthenticationManagerImpl implements CustomAuthenticationManager {
    private final MemberRepository memberRepository;
    private final CryptoUtil cryptoUtil;

    @Override
    public Long authenticate(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        if (cryptoUtil.matchesPassword(password, member.getPassword())) {
            return member.getId();
        }

        throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCHED);
    }
}
