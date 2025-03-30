package com.altkey.code.common.crypto.block;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * AbstractBlockCrypto
 * 블록 암호화 방식중 CBC(Cipher Block Chaining) 방식의 추상 클래스
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public abstract class AbstractCBCBlockTransformation extends AbstractBlockCrypto {
    //
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SecretKeyFactory pbkdf2Factory;

    private byte[] iv;

    protected AbstractCBCBlockTransformation(byte[] key) {
        super(key);
        try {
            this.pbkdf2Factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            // 예외가 발생했을경우 Java 환경 설정에서 제거를 했거나 JCE(Java Cryptographic Extension) Provider 를 점검
            // JCE 나 JVM 쪽에 특별한 작업을 하지 않앗을 경우 정상 작동하므로 별도의 추가 처리는 하지 않는다.
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * SecureRandom을 이용하여 iv와 salt를 생성
     *
     * @param iv
     * @param salt
     */
    protected void generateSecureRandomInitVector(final byte[] iv, final byte[] salt) {
        if (iv == null || salt == null || iv.length < 2 || salt.length < 2) {
            throw new IllegalArgumentException("IV and Salt cannot be null");
        }
        SecureRandom random = new WeakReference<>(new SecureRandom()).get();
        random.nextBytes(iv);
        random.nextBytes(salt);
    }

    /**
     * PBKDF2 알고리즘을 이용하여 비밀키를 생성
     *
     * @param salt generateSecureRandomInitVector 메소드에서 생성한 salt
     * @param iterationCount 10000 회 이상으로 설정
     * @param keyLength 128, 192, 256 비트
     * @return
     * @throws InvalidKeySpecException
     */
    protected byte[] generatePBKDF2Secret(final byte[] salt, final int iterationCount, final int keyLength) throws InvalidKeySpecException {
        return pbkdf2Factory.generateSecret(new PBEKeySpec(new String(super.getKey(), StandardCharsets.UTF_8).toCharArray(),
                salt,
                iterationCount,
                keyLength)).getEncoded();
    }
}
