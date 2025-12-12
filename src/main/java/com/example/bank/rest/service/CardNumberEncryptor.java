package com.example.bank.rest.service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class CardNumberEncryptor {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;

    private static SecretKey secretKey;

    static {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String encrypt(String plainText) throws Exception {
        byte[] clean = plainText.getBytes();

        // 1. Генерация случайного Initialization Vector (IV)
        byte[] iv = new byte[IV_LENGTH_BYTE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        // 2. Инициализация шифра
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        // 3. Шифрование данных
        byte[] cipherText = cipher.doFinal(clean);

        // 4. Объединение IV и зашифрованного текста для хранения/передачи
        byte[] encryptedWithIV = ByteBuffer.allocate(iv.length + cipherText.length)
            .put(iv)
            .put(cipherText)
            .array();

        // 5. Кодирование в Base64 для удобства хранения в виде строки
        return Base64.getEncoder().encodeToString(encryptedWithIV);
    }

    public static String decrypt(String encryptedText) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encryptedText);

        // 1. Извлечение IV и зашифрованного текста
        ByteBuffer bb = ByteBuffer.wrap(decoded);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);
        byte[] cipherText = new byte[bb.remaining()];
        bb.get(cipherText);

        // 2. Инициализация шифра для дешифрования
        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        GCMParameterSpec ivSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        // 3. Дешифрование данных
        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText);
    }

    public static String maskCardNumber(String number){
        return "**** **** **** " + number.split(" ")[3];
    }
}
