//package com.damso.user.security;
//
//import com.damso.domain.db.entity.member.Member;
//import com.damso.domain.db.repository.member.MemberRepository;
//import com.damso.user.security.model.SessionMember;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service("UserDetailsService")
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//    private final MemberRepository memberRepository;
//
//    @Transactional(readOnly = true)
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        long memberId = Long.parseLong(username);
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new UsernameNotFoundException("등록된 계정이 아닙니다."));
//        return new SessionMember(member);
//    }
//}
