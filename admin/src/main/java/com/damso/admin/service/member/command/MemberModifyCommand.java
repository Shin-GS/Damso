package com.damso.admin.service.member.command;

import com.damso.core.enums.member.MemberRoleType;
import com.damso.core.enums.member.MemberStatusType;

public record MemberModifyCommand(String email,
                                  String name,
                                  MemberRoleType role,
                                  MemberStatusType status) {
}
