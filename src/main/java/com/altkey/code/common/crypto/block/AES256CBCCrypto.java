package com.altkey.code.common.crypto.block;

import com.altkey.code.common.crypto.Crypto;
import com.altkey.code.common.crypto.enums.BlockTransformation;
import com.altkey.code.common.crypto.enums.CryptoAlgorithm;
import com.altkey.code.common.utils.EnumUtils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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

    private SecretKeyFactory pbkdf2Factory;

    private BlockTransformation blockTransformation;

    public AES256CBCCrypto(byte[] key) {
        super(key);
    }

    @Override
    public String encrypt(String value) {
        // 16바이트씩 생성
        byte[] iv = new byte[16];
        byte[] salt = new byte[16];

        try {
            super.generateSecureRandomInitVector(iv, salt);
            CryptoAlgorithm algorithm = EnumUtils.getEnum(CryptoAlgorithm.class, "AES");
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