package com.damso.user.service.member.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.user.service.member.auth.command.EmailSignupCommand;
import com.damso.user.service.member.auth.command.SNSSignupCommand;

public interface MemberRegister {
    void checkEmailDuplication(String email);

    void checkSNSDuplication(MemberSocialAccountType provider, String providerAccountId);

    void signup(EmailSignupCommand command);

    void signup(SNSSignupCommand command);
}
