package com.altkey.code.common.enums;

/**
 * Enum interface
 * Enum 사용시 DB저장 Key값이 별도로 있거나 별도 키값으로 사용할때
 * JSON, Mybatis, JPA 등에서 Enum을 사용할때 키값으로 보낼때도 사용
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-30
 */
public interface KeyEnum<K> {
    //
    K getKey();
}