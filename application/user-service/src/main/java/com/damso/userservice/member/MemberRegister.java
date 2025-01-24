package com.damso.userservice.member;

import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.userservice.auth.request.EmailSignupRequest;

public interface MemberRegister {
    void checkEmailDuplication(String email);

    void checkSNSDuplication(MemberSocialAccountType provider, String providerAccountId);

    Long signup(EmailSignupRequest request);

    Long signup(MemberSocialAccountType provider, String providerAccountId, String email, String name);
}
