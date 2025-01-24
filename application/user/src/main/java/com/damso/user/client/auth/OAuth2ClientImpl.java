package com.damso.user.client.auth;

import com.damso.core.enums.member.MemberSocialAccountType;
import com.damso.user.client.auth.response.OAuth2Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2ClientImpl {
    private final OAuth2Client oAuth2Client;

    public OAuth2Response getUser(MemberSocialAccountType provider,
                                  String snsToken) {
        return oAuth2Client.fetchUserProfile(provider, snsToken);
    }
}
