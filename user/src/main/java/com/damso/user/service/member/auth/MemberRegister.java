package com.damso.user.service.member.auth;

import com.damso.domain.db.entity.member.Member;
import com.damso.user.service.member.auth.command.SignupCommand;
import com.damso.user.service.member.auth.command.SnsInfoCommand;

public interface MemberRegister {
    void checkEmailDuplication(String email);

    void signup(SignupCommand command);

    Member signup(SnsInfoCommand command);
}
