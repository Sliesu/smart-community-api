package cn.edu.njuit.api.utils;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

public class AESUtil {
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String CHARSET_NAME = "UTF-8";
    private static final String AES_NAME = "AES";

    /**
     * 解密AES加密数据
     *
     * @param encryptedData 加密数据
     * @param sessionKey    会话密钥
     * @param vi            6
     * @return 解密后的字符串
     */
    public static String decrypt(String encryptedData, String sessionKey, String vi) {
        byte[] encData = Base64.decodeBase64(encryptedData);
        byte[] iv = Base64.decodeBase64(vi);
        byte[] key = Base64.decodeBase64(sessionKey);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key, AES_NAME);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return new String(cipher.doFinal(encData), CHARSET_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
