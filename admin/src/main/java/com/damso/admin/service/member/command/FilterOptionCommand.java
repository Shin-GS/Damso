package com.damso.admin.service.member.command;

import com.damso.core.constant.MemberStatusType;

public record FilterOptionCommand(String value,
                                  String description) {
    public static FilterOptionCommand of(MemberStatusType type) {
        return new FilterOptionCommand(type.name(), type.getDescription());
    }
}
