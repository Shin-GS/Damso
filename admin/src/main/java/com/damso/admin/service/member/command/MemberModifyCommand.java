package com.damso.admin.service.member.command;

import com.damso.admin.core.constant.MemberRoleType;
import com.damso.admin.core.constant.MemberStatusType;

public record MemberModifyCommand(String email,
                                  String name,
                                  MemberRoleType role,
                                  MemberStatusType status) {
}
