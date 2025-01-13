package com.damso.admin.service.member.request;

import com.damso.core.enums.member.MemberStatusType;

public record FilterOptionRequest(String value,
                                  String description) {
    public static FilterOptionRequest of(MemberStatusType type) {
        return new FilterOptionRequest(type.name(), type.getDescription());
    }
}
