package com.damso.user.client.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2Response {
    private String providerAccountId;
    private String name;
    private String email;
    private String profileImage;
}
