package pers.kksg.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * @project lc-open-api
 * @description 3DES加密工具类
 * @author lvqiang
 * @date 2023/5/25 10:35:24
 * @version 1.0
 */
public class TripleDESEncryptionUtils {
    private static final String ENCRYPTION_ALGORITHM = "DESede/ECB/PKCS5Padding";

    public static String encrypt(String plaintext, String secretKey) throws Exception {
        KeySpec keySpec = new DESedeKeySpec(Arrays.copyOf(secretKey.getBytes(StandardCharsets.UTF_8), 24));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey key = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String ciphertext, String secretKey){
        try {
            KeySpec keySpec = new DESedeKeySpec(Arrays.copyOf(secretKey.getBytes(StandardCharsets.UTF_8), 24));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey key = keyFactory.generateSecret(keySpec);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return ciphertext;
        }
    }

    public static void main(String[] args) throws Exception {
        String plaintext = "441602198809220433";
        //！！！秘钥使用签名时分配秘钥即可！！！
//        String secretKey = bytesToHex(generateKey().getEncoded());
        String secretKey = "W#E1f@rrtewe3$8^";
        System.out.println("Generated Key: " + secretKey);
        // Encrypt
        String encrypted = encrypt(plaintext, secretKey);
        System.out.println("Encrypted: " + encrypted);

        // Decrypt
        String decrypted = decrypt("IWZRZnTPAhJNrYLm/ZQ0fQ==", "bxFAMoR8xWsTkr2JWEz3TtjUptZjXS");
        System.out.println("Decrypted: " + decrypted);
    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
        return keyGenerator.generateKey();
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
