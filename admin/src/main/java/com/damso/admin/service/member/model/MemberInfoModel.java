//package com.damso.admin.service.member.model;
//
//import com.damso.admin.core.constant.MemberRoleType;
//import com.damso.admin.core.constant.MemberStatusType;
//import com.damso.admin.storage.entity.member.Member;
//
//public record MemberInfoModel(Long memberId,
//                              String email,
//                              String name,
//                              MemberRoleType role,
//                              MemberStatusType status) {
//    public static MemberInfoModel of(Member member) {
//        return new MemberInfoModel(member.getId(),
//                member.getEmail(),
//                member.getName(),
//                member.getRole(),
//                member.getStatus());
//    }
//}
