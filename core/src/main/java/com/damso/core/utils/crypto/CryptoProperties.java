package com.damso.core.utils.crypto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "core.crypto")
public class CryptoProperties {
    private String aesKey;
    private String aesIv;
    private int bcryptStrength;
}
