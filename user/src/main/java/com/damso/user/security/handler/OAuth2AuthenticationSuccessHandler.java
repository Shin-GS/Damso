package com.damso.user.security.handler;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.domain.db.entity.member.MemberSocialAccount;
import com.damso.domain.db.repository.member.MemberSocialAccountRepository;
import com.damso.user.client.auth.OAuth2ClientImpl;
import com.damso.user.client.auth.model.OAuth2Model;
import com.damso.user.security.token.JwtTokenProvider;
import com.damso.user.service.member.auth.MemberRegister;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Transactional
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final String REDIRECT_HOME = "/?auth=";
    private static final String REDIRECT_LOGIN_ERROR = "/login?error";
    private static final String REDIRECT_ROOT = "/";

    private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;
    private final MemberSocialAccountRepository memberSocialAccountRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRegister memberRegister;
    private final OAuth2ClientImpl oAuth2Client;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken oauth2Auth) {
            handleOAuth2Authentication(response, oauth2Auth);
        } else {
            response.sendRedirect(REDIRECT_LOGIN_ERROR);
        }
    }

    private void handleOAuth2Authentication(HttpServletResponse response, OAuth2AuthenticationToken oauth2Auth) throws IOException {
        String accessToken = fetchAuthorizedClient(oauth2Auth)
                .getAccessToken()
                .getTokenValue();

        MemberSocialAccountType provider = extractProvider(oauth2Auth);
        OAuth2Model snsUser = oAuth2Client.getUser(provider, accessToken);
        String authToken = memberSocialAccountRepository.findByProviderAndProviderAccountId(provider, snsUser.getProviderAccountId())
                .map(this::login)
                .orElseGet(() -> register(provider, snsUser));
        response.sendRedirect(StringUtils.hasText(authToken) ? REDIRECT_HOME + authToken : REDIRECT_ROOT);
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

    private String login(MemberSocialAccount socialAccount) {
        return jwtTokenProvider.generateAccessToken(socialAccount.getMember().getId());
    }

    private String register(MemberSocialAccountType provider, OAuth2Model snsUser) {
        Long memberId = memberRegister.signup(provider, snsUser.getProviderAccountId(), snsUser.getEmail(), snsUser.getName());
        return jwtTokenProvider.generateAccessToken(memberId);
    }
}
