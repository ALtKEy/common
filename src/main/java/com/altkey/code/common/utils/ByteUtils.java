package com.altkey.code.common.utils;

/**
 * ByteUtils
 * ByteArray 에 패딩, 연결을 짓기 위해 사용
 *
 * @author Kim Jung-tae(altkey)
 * @since 2025-03-26
 */
public class ByteUtils {
    //
    private ByteUtils() {}

    public static byte[] rightPadding(final byte[] value, final byte pad, final int length) {
        //
        if(value.length >= length) return value;

        byte[] rtn = new byte[length];

        System.arraycopy(value, 0, rtn, 0, value.length);
        for (int i = value.length; i < length; i++) {
            rtn[i] = pad;
        }

        return rtn;
    }

    public static byte[] leftPadding(final byte[] value, final byte pad, final int length) {
        //
        if(value.length >= length) return value;

        byte[] rtn = new byte[length];

        for (int i = 0; i < (length - value.length); i++) {
            rtn[i] = pad;
        }

        if (length - (length - value.length) >= 0) {
            System.arraycopy(value, 0, rtn, (length - value.length), length - (length - value.length));
        }

        return rtn;
    }

    public static byte[] concat(final byte[] left, final byte[] right) {
        //
        byte[] rtn = new byte[left.length + right.length];

        System.arraycopy(left, 0, rtn, 0, left.length);
        System.arraycopy(right, 0, rtn, left.length, right.length);

        return rtn;
    }
}
