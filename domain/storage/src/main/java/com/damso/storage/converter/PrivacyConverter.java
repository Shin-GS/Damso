package com.damso.storage.converter;

import com.damso.core.utils.crypto.CryptoUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.StringUtils;

@Converter
public class PrivacyConverter implements AttributeConverter<String, String> {
    private final CryptoUtil cryptoUtil;

    public PrivacyConverter(CryptoUtil cryptoUtil) {
        this.cryptoUtil = cryptoUtil;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return StringUtils.hasText(attribute) ? cryptoUtil.encrypt(attribute) : null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? cryptoUtil.decrypt(dbData) : null;
    }
}
