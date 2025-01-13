package com.damso.admin.service.member.request;

import com.damso.core.enums.member.MemberRoleType;

public record MemberRegisterRequest(String email,
                                    String name,
                                    MemberRoleType role) {
}
