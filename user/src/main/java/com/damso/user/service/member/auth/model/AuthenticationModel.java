package com.damso.user.service.member.auth.model;

import com.damso.core.constant.MemberRoleType;

public record AuthenticationModel(Long memberId,
                                  MemberRoleType memberRole) {
}
