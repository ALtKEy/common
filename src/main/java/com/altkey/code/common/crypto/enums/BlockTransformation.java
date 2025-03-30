package com.altkey.code.common.crypto.enums;

import com.altkey.code.common.enums.KeyEnum;

/**
 * BlockTransformation
 * 블록 암호화 (Block Cipher) 알고리즘의 변환 방식
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public enum BlockTransformation implements KeyEnum<String> {
    //
    CBC("CBC"),
    GCM("GCM"),
    ;

    final String key;

    BlockTransformation(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
