package com.damso.repository.db.converter;

import com.damso.core.utils.crypto.CryptoUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.StringUtils;

@Converter
public class EmailConverter implements AttributeConverter<String, String> {
    private final CryptoUtil cryptoUtil;

    public EmailConverter(CryptoUtil cryptoUtil) {
        this.cryptoUtil = cryptoUtil;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return StringUtils.hasText(attribute) ? cryptoUtil.encryptEmail(attribute) : null;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? cryptoUtil.decryptEmail(dbData) : null;
    }
}
