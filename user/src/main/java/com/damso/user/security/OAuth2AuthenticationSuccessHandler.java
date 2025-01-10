package com.damso.user.security;

import com.damso.auth.service.JwtTokenProvider;
import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.domain.db.entity.member.MemberSocialAccount;
import com.damso.domain.db.repository.member.MemberSocialAccountRepository;
import com.damso.user.client.auth.OAuth2ClientImpl;
import com.damso.user.client.auth.model.OAuth2Model;
import com.damso.user.service.member.MemberRegister;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final String REDIRECT_HOME = "/";
    private static final String REDIRECT_LOGIN_ERROR = "/login?error";
    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final MemberSocialAccountRepository memberSocialAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRegister memberRegister;
    private final OAuth2ClientImpl oAuth2Client;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (!(authentication instanceof OAuth2AuthenticationToken oauth2Auth)) {
            response.sendRedirect(REDIRECT_LOGIN_ERROR);
            return;
        }

        String accessToken = fetchAuthorizedClient(oauth2Auth)
                .getAccessToken()
                .getTokenValue();

        MemberSocialAccountType provider = extractProvider(oauth2Auth);
        OAuth2Model snsUser = oAuth2Client.getUser(provider, accessToken);
        memberSocialAccountRepository.findByProviderAndProviderAccountId(provider, snsUser.getProviderAccountId())
                .ifPresentOrElse(
                        memberSocial -> this.login(response, memberSocial),
                        () -> this.register(response, provider, snsUser)
                );

        response.sendRedirect(REDIRECT_HOME);
    }

    private MemberSocialAccountType extractProvider(OAuth2AuthenticationToken oauth2Auth) {
        return MemberSocialAccountType.valueOf(oauth2Auth.getAuthorizedClientRegistrationId().toUpperCase());
    }

    private OAuth2AuthorizedClient fetchAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return oAuth2AuthorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName()
        );
    }

    private void login(HttpServletResponse response,
                       MemberSocialAccount socialAccount) {
        jwtTokenProvider.generateJwtCookie(response, socialAccount.getMember().getId());
    }

    private void register(HttpServletResponse response,
                          MemberSocialAccountType provider,
                          OAuth2Model snsUser) {
        Long memberId = memberRegister.signup(provider, snsUser.getProviderAccountId(), snsUser.getEmail(), snsUser.getName());
        jwtTokenProvider.generateJwtCookie(response, memberId);
    }
}
