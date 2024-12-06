package com.damso.core.utils.crypto;

import com.damso.core.response.exception.CryptoException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class CryptoUtil {
    private final SecretKeySpec secretKey;
    private final byte[] iv;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String AES_ALGORITHM = "AES/GCM/NoPadding";

    public CryptoUtil(CryptoProperties cryptoProperties) {
        this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(cryptoProperties.getAesKey()), "AES");
        this.iv = Base64.getDecoder().decode(cryptoProperties.getAesIv());
        this.passwordEncoder = new BCryptPasswordEncoder(cryptoProperties.getBcryptStrength());
    }

    public String encrypt(String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);
            byte[] encrypted = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new CryptoException("Encryption failed", e);
        }
    }

    public String decrypt(String encryptedText) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decrypted);
        } catch (Exception e) {
            throw new CryptoException("Decryption failed", e);
        }
    }

    public String encryptEmail(String email) {
        return encrypt(email);
    }

    public String decryptEmail(String encryptedEmail) {
        return decrypt(encryptedEmail);
    }

    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean matchesPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
