package com.damso.user.service.member.auth;

import com.damso.core.constant.MemberSocialAccountType;
import com.damso.user.client.auth.OAuth2Client;
import com.damso.user.client.auth.model.OAuth2Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2ClientService {
    private final OAuth2Client oAuth2Client;

    public OAuth2Model getUser(MemberSocialAccountType provider,
                               String snsToken) {
        return oAuth2Client.fetchUserProfile(provider, snsToken);
    }
}
