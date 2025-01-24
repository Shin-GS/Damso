package com.damso.user.security;

import com.damso.common.auth.JwtTokenProvider;
import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.user.client.auth.OAuth2ClientImpl;
import com.damso.user.client.auth.response.OAuth2Response;
import com.damso.userservice.member.MemberFinder;
import com.damso.userservice.member.MemberRegister;
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
    private final MemberFinder memberFinder;
    private final MemberRegister memberRegister;
    private final JwtTokenProvider jwtTokenProvider;
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
        OAuth2Response snsUser = oAuth2Client.getUser(provider, accessToken);
        memberFinder.findMemberId(provider, snsUser.getProviderAccountId())
                .ifPresentOrElse(
                        memberId -> this.login(response, memberId),
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
                       Long memberId) {
        jwtTokenProvider.generateJwtCookie(response, memberId);
    }

    private void register(HttpServletResponse response,
                          MemberSocialAccountType provider,
                          OAuth2Response snsUser) {
        Long memberId = memberRegister.signup(provider, snsUser.getProviderAccountId(), snsUser.getEmail(), snsUser.getName());
        jwtTokenProvider.generateJwtCookie(response, memberId);
    }
}
