package com.damso.adminservice.member.request;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;

public record MemberModifyRequest(String email,
                                  String name,
                                  MemberRoleType role,
                                  MemberStatusType status) {
}
