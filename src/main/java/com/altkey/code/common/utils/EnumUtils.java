package com.altkey.code.common.utils;

import com.altkey.code.common.enums.KeyEnum;

import java.util.Arrays;

/**
 * EnumUtils
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-04-01
 */
public class EnumUtils {
    //
    private EnumUtils() {}

    /**
     * <pre>
     * Key값으로 Enum을 찾는다.
     * ex) EnumUtils.getKeyEnum(CryptoAlgorithm.class, "AES");
     * </pre>
     * @param enumClass 찾으려는 Enum Class
     * @param value 찾으려는 Key값
     * @return KeyEnum
     * @param <E> enum type
     */
    public static <E extends Enum<E> & KeyEnum<?>> E getKeyEnum(Class<E> enumClass, Object value) {
        try {
            return Arrays.stream(enumClass.getEnumConstants())
                    .filter(type -> type.getKey().equals(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown Enum " + value));
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown Enum " + value);
        }
    }
}