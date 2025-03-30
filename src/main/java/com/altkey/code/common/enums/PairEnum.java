package com.altkey.code.common.enums;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Enum interface
 * Enum 사용시 DB저장 Key값이 별도로 있고 또 value 값이 같이 묶여서 사용될 경우
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public interface PairEnum<K, V> {
    //
    Pair<K, V> getPair();

    default K getKey() {
        return getPair().getLeft();
    }

    V getValue();
}