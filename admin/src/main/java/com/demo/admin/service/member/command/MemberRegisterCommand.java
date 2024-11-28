package com.demo.admin.service.member.command;

import com.demo.admin.core.constant.MemberRoleType;

public record MemberRegisterCommand(String email,
                                    String name,
                                    MemberRoleType role) {
}
