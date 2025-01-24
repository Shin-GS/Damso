package com.damso.core.utils.crypto;

import com.damso.core.exception.CryptoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CryptoUtilTest {
    private CryptoUtil cryptoUtil;

    @BeforeEach
    public void setup() {
        // application.yml에서 프로퍼티 로드
        Properties properties = loadYamlProperties("application-core.yml");

        // CryptoUtil 초기화
        cryptoUtil = new CryptoUtil(createCryptoProperties(properties));
    }

    private Properties loadYamlProperties(String yamlFileName) {
        try {
            Resource resource = new ClassPathResource(yamlFileName);
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            yamlFactory.setResources(resource);
            Properties properties = yamlFactory.getObject();

            if (properties == null) {
                throw new RuntimeException("YAML properties file is empty or cannot be loaded.");
            }

            return properties;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load YAML properties from file: " + yamlFileName, e);
        }
    }

    private CryptoProperties createCryptoProperties(Properties properties) {
        try {
            CryptoProperties cryptoProperties = new CryptoProperties();
            cryptoProperties.setAesKey(properties.getProperty("core.crypto.aes-key"));
            cryptoProperties.setAesIv(properties.getProperty("core.crypto.aes-iv"));
            cryptoProperties.setBcryptStrength(Integer.parseInt(properties.getProperty("core.crypto.bcrypt-strength")));
            return cryptoProperties;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create CryptoProperties from loaded properties.", e);
        }
    }

    @DisplayName("AES 암호화와 복호화가 동일한 원문을 반환해야 한다")
    @ParameterizedTest(name = "plaintext: {0}")
    @ValueSource(strings = {"안녕하세요, AES 테스트입니다!", "Hello, AES Test!", "1234567890", "!@#$%^&*()"})
    void testAesEncryptionAndDecryption(String plaintext) {
        // 암호화
        String encryptedText = cryptoUtil.encrypt(plaintext);
        System.out.println("Encrypted Text: " + encryptedText);

        // 복호화
        String decryptedText = cryptoUtil.decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);

        // 검증
        assertThat(decryptedText).isEqualTo(plaintext);
    }

    @DisplayName("AES 복호화 시 잘못된 데이터가 입력되면 예외를 던진다")
    @Test
    void testAesEncryptionWithInvalidData() {
        // 암호화되지 않은 데이터로 복호화 시도
        String invalidEncryptedText = "invalid data";

        // 예외 발생 확인
        assertThrows(CryptoException.class, () -> cryptoUtil.decrypt(invalidEncryptedText));
    }

    @DisplayName("BCrypt 해싱된 비밀번호는 원문 비밀번호와 매칭된다")
    @ParameterizedTest(name = "rawPassword: {0}")
    @ValueSource(strings = {"mySecretPassword", "anotherPassword123", "password!@#"})
    void testBcryptPasswordHashing(String rawPassword) {
        // 비밀번호 해싱
        String hashedPassword = cryptoUtil.hashPassword(rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        // 해시된 비밀번호 검증
        boolean matches = cryptoUtil.matchesPassword(rawPassword, hashedPassword);
        assertThat(matches).isTrue();
    }

    @DisplayName("다른 비밀번호는 BCrypt 해싱된 비밀번호와 매칭되지 않는다")
    @Test
    void testBcryptPasswordHashingMismatch() {
        String rawPassword = "mySecretPassword";
        String differentPassword = "differentPassword";

        // 비밀번호 해싱
        String hashedPassword = cryptoUtil.hashPassword(rawPassword);

        // 다른 비밀번호로 검증 실패 확인
        boolean matches = cryptoUtil.matchesPassword(differentPassword, hashedPassword);
        assertThat(matches).isFalse();
    }
}
