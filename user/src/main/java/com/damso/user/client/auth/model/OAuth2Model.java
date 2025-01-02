package com.damso.user.client.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2Model {
    private String providerAccountId;
    private String name;
    private String email;
    private String profileImage;
}
