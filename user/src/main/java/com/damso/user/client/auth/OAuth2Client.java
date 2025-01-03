package com.damso.user.client.auth;

import com.damso.core.constant.member.MemberSocialAccountType;
import com.damso.user.client.auth.model.OAuth2Model;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OAuth2Client {
    public OAuth2Model fetchUserProfile(MemberSocialAccountType provider,
                                        String snsToken) {
        return switch (provider) {
            case GOOGLE -> fetchGoogleUserProfile(snsToken);
            case X -> fetchXUserProfile(snsToken);
            case INSTAGRAM -> fetchInstagramUserProfile(snsToken);
            case NAVER -> fetchNaverUserProfile(snsToken);
        };
    }

    private OAuth2Model fetchGoogleUserProfile(String snsToken) {
        String apiUrl = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + snsToken;
        ResponseEntity<Map<String, Object>> response = makeGetRequest(apiUrl);
        Map<String, Object> body = response.getBody();
        return new OAuth2Model(
                (String) body.get("id"),
                (String) body.get("name"),
                (String) body.get("email"),
                (String) body.get("picture")
        );
    }

    private OAuth2Model fetchXUserProfile(String snsToken) {
        String apiUrl = "https://api.twitter.com/2/users/me";
        ResponseEntity<Map<String, Object>> response = makeGetRequest(apiUrl, snsToken);
        Map<String, Object> body = response.getBody();
        return new OAuth2Model(
                (String) body.get("id"),
                (String) body.get("name"),
                (String) body.getOrDefault("email", null),
                null
        );
    }

    private OAuth2Model fetchInstagramUserProfile(String snsToken) {
        String apiUrl = "https://graph.instagram.com/me?fields=id,username,account_type";
        ResponseEntity<Map<String, Object>> response = makeGetRequest(apiUrl, snsToken);
        Map<String, Object> body = response.getBody();
        return new OAuth2Model(
                (String) body.get("id"),
                (String) body.get("username"),
                null,
                null
        );
    }

    private OAuth2Model fetchNaverUserProfile(String snsToken) {
        String apiUrl = "https://openapi.naver.com/v1/nid/me";
        ResponseEntity<Map<String, Object>> response = makeGetRequest(apiUrl, snsToken);
        Map<String, Object> body = (Map<String, Object>) response.getBody().get("response");
        return new OAuth2Model(
                (String) body.get("id"),
                (String) body.get("name"),
                (String) body.get("email"),
                (String) body.get("profile_image")
        );
    }

    private ResponseEntity<Map<String, Object>> makeGetRequest(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
    }

    private ResponseEntity<Map<String, Object>> makeGetRequest(String apiUrl,
                                                               String snsToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + snsToken);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });
    }
}
