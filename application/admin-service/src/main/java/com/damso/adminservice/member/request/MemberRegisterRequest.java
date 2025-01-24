package com.damso.adminservice.member.request;

import com.damso.core.enums.member.MemberRoleType;

public record MemberRegisterRequest(String email,
                                    String name,
                                    MemberRoleType role) {
}
