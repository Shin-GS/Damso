package com.damso.common.auth.impl;

import com.damso.common.auth.CustomAuthenticationManager;
import com.damso.core.code.ErrorCode;
import com.damso.core.exception.BusinessException;
import com.damso.core.utils.crypto.CryptoUtil;
import com.damso.storage.entity.member.Member;
import com.damso.storage.repository.member.MemberRepository;
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
    public Long authenticateNotAdmin(String email, String password) {
        Member member = authenticate(email, password);
        if (member.isAdmin()) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        return member.getId();
    }

    @Override
    public Long authenticateAdmin(String email, String password) {
        Member member = authenticate(email, password);
        if (!member.isAdmin()) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }

        return member.getId();
    }

    private Member authenticate(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        if (!cryptoUtil.matchesPassword(password, member.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_NOT_MATCHED);
        }

        return member;
    }
}
