package com.altkey.code.common.crypto.block;

import com.altkey.code.common.crypto.Crypto;
import com.altkey.code.common.crypto.enums.BlockTransformation;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

/**
 * AES256 CBC 암호화
 * Javascript 클라이언트에서도 사용하고 복호화 할 수 있도록 CBC방식을 제공
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public class AES256CBCCrypto extends AbstractCBCBlockTransformation implements Crypto {
    //
    private Cipher cipher;

    private final String transformation = "AES/CBC/PKCS5Padding";

    public AES256CBCCrypto(byte[] key) {
        super(key);
        try {
            this.cipher = Cipher.getInstance(this.transformation);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            // 예외가 발생했을경우 Java 환경 설정에서 제거를 했거나 JCE(Java Cryptographic Extension) Provider 를 점검
            // JCE 나 JVM 쪽에 특별한 작업을 하지 않앗을 경우 정상 작동하므로 별도의 추가 처리는 하지 않는다.
        }
    }

    @Override
    public String encrypt(String value) {
        // 16바이트씩 생성
        byte[] iv = new byte[16];
        byte[] salt = new byte[16];

        try {
            super.generateSecureRandomInitVector(iv, salt);
            // 키 생성
            SecretKey key = new SecretKeySpec(super.generatePBKDF2Secret(salt, 10000, 256), "AES");

            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] encrypted = cipher.doFinal(value.getBytes());

            // 조합
            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + salt.length + encrypted.length);
            byteBuffer.put(iv);
            byteBuffer.put(salt);
            byteBuffer.put(encrypted);

            // URL에서도 사용할 수 있으므로 URL Safe Base64로 인코딩
            return Base64.getUrlEncoder().encodeToString(byteBuffer.array());
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } finally {
            // 사용한 뒤 메모리에서 삭제
            Arrays.fill(iv, (byte) 0);
            iv = null;
            Arrays.fill(salt, (byte) 0);
            salt = null;
        }
    }

    @Override
    public String decrypt(String value) {
        byte[] iv = new byte[16];
        byte[] salt = new byte[16];

        try {
            // 조합식에 따라 분리
            byte[] decode = Base64.getUrlDecoder().decode(value);
            ByteBuffer byteBuffer = ByteBuffer.wrap(decode);
            byteBuffer.get(iv);
            byteBuffer.get(salt);
            byte[] data = new byte[byteBuffer.remaining()];
            byteBuffer.get(data);

            // 키 생성
            SecretKey key = new SecretKeySpec(super.generatePBKDF2Secret(salt, 10000, 256), "AES");

            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            byte[] decrypted = cipher.doFinal(data);

            return new String(decrypted);
        } catch (InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException | InvalidKeyException e) {
            throw new RuntimeException(e);
        } finally {
            Arrays.fill(iv, (byte) 0);
            iv = null;
            Arrays.fill(salt, (byte) 0);
            salt = null;
        }
    }
}