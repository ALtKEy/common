package com.altkey.code.common.crypto;

/**
 * Crypto interface
 * 암호화 또는 복호화를 위한 인터페이스
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public interface Crypto {
    //
    String encrypt(String value);

    String decrypt(String value) throws UnsupportedOperationException;
}
