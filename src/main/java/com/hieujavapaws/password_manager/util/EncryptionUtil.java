package com.hieujavapaws.password_manager.util;

import io.github.cdimascio.dotenv.Dotenv;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {
    private static final String AES = "AES";

    private static String getSecretKey() {
        Dotenv dotenv = Dotenv.load();
        String key = dotenv.get("ENCRYPTION_SECRET_KEY");
        if (key == null || key.length() <= 16) {
            throw new RuntimeException("Invalid or missing encryption secret key. " +
                    "Ensure it's set in the .env file and at least 16 characters long.");
        }
        return key;
    }

    public static String encrypt(String plain) throws Exception {
        String secretKey = getSecretKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(plain.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encrypted) throws Exception {
        String secretKey = getSecretKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encrypted));
        return new String(decryptedBytes);
    }
}