package com.altkey.code.common.crypto.enums;

import com.altkey.code.common.enums.KeyEnum;

/**
 * CryptoAlgorithm
 * 암호화 알고리즘
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public enum CryptoAlgorithm implements KeyEnum<String> {
    //
    AES("AES"),
    ;

    final String key;

    CryptoAlgorithm(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
