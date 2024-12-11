package com.damso.user.filter;

import com.damso.core.constant.MemberSocialAccountType;

public interface CustomAuthenticationManager {
    Long authenticateByEmail(String email, String password);

    Long authenticateBySns(MemberSocialAccountType provider, String snsToken);
}
