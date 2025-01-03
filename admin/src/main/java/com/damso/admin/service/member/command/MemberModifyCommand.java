package com.damso.admin.service.member.command;

import com.damso.core.constant.member.MemberRoleType;
import com.damso.core.constant.member.MemberStatusType;

public record MemberModifyCommand(String email,
                                  String name,
                                  MemberRoleType role,
                                  MemberStatusType status) {
}
