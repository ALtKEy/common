package com.altkey.code.common.crypto.block;

import com.altkey.code.common.crypto.Crypto;

/**
 * AbstractBlockCrypto
 * 블록 암호화 (Block Cipher) 알고리즘을 위한 추상 클래스
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public abstract class AbstractBlockCrypto implements Crypto {
    //
    private final byte[] key;

    protected AbstractBlockCrypto(byte[] key) {
        this.key = key;
    }

    protected AbstractBlockCrypto(byte[] key, byte[] iv) {
        this.key = key;
    }

    protected byte[] getKey() {
        return key;
    }

    public abstract String encrypt(String value);

    public abstract String decrypt(String value);
}