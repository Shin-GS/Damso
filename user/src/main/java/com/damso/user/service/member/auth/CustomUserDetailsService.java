//package com.damso.user.service.member.auth;
//
//import com.damso.domain.db.entity.member.Member;
//import com.damso.domain.db.repository.member.MemberRepository;
//import com.damso.user.service.member.auth.model.CustomUserDetailsModel;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//    private final MemberRepository memberRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            Long memberId = Long.parseLong(username);
//            Member member = memberRepository.findById(memberId)
//                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + memberId));
//            return new CustomUserDetailsModel(member);
//        } catch (NumberFormatException e) {
//            throw new UsernameNotFoundException("회원번호 형식이 올바르지 않습니다: " + username);
//        }
//    }
//}
