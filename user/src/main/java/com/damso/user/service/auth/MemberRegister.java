package com.damso.user.service.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.user.service.auth.command.EmailSignupCommand;

public interface MemberRegister {
    void checkEmailDuplication(String email);

    void checkSNSDuplication(MemberSocialAccountType provider, String providerAccountId);

    Long signup(EmailSignupCommand command);

    Long signup(MemberSocialAccountType provider, String providerAccountId, String email, String name);
}
