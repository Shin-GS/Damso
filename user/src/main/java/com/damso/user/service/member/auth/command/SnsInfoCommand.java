package com.damso.user.service.member.auth.command;

import com.damso.core.constant.MemberSocialAccountType;

public record SnsInfoCommand(MemberSocialAccountType provider,
                             String providerAccountId,
                             String email,
                             String name) {
    public static SnsInfoCommand of(MemberSocialAccountType provider,
                                    String providerAccountId,
                                    String email,
                                    String name) {
        return new SnsInfoCommand(provider,
                providerAccountId,
                email,
                name);
    }
}
