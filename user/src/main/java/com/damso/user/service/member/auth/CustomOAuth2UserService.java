package com.damso.user.service.member.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.domain.db.entity.member.Member;
import com.damso.domain.db.entity.member.MemberSocialAccount;
import com.damso.domain.db.repository.member.MemberSocialAccountRepository;
import com.damso.user.service.member.auth.command.SnsInfoCommand;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRegister memberRegister;
    private final MemberSocialAccountRepository memberSocialAccountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        MemberSocialAccountType provider = MemberSocialAccountType.findByName(userRequest.getClientRegistration().getRegistrationId());
        SnsInfoCommand userInfo = SnsInfoCommand.of(
                provider,
                oAuth2User.getName(),
                oAuth2User.getAttribute("email"),
                oAuth2User.getAttribute("name"));

        return memberSocialAccountRepository.existsByProviderAndProviderAccountId(userInfo.provider(), userInfo.providerAccountId()) ?
                login(userInfo, oAuth2User) :
                signup(userInfo, oAuth2User);
    }

    private OAuth2User login(SnsInfoCommand command,
                             OAuth2User oAuth2User) {
        MemberSocialAccount memberSocialAccount = memberSocialAccountRepository.findByProviderAndProviderAccountId(command.provider(), command.providerAccountId())
                .orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));
        return createOAuth2User(memberSocialAccount.getMember(), oAuth2User);
    }

    private OAuth2User signup(SnsInfoCommand command,
                              OAuth2User oAuth2User) {
        Member member = memberRegister.signup(command);
        return createOAuth2User(member, oAuth2User);
    }

    private OAuth2User createOAuth2User(Member member,
                                        OAuth2User oAuth2User) {
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
                oAuth2User.getAttributes(),
                "email"
        );
    }
}
