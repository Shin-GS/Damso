package com.damso.admin.service.member.command;

import com.damso.core.constant.member.MemberRoleType;

public record MemberRegisterCommand(String email,
                                    String name,
                                    MemberRoleType role) {
}
