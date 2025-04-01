package com.altkey.code.common.utils;

import com.altkey.code.common.crypto.enums.BlockTransformation;
import com.altkey.code.common.crypto.enums.CryptoAlgorithm;
import org.junit.jupiter.api.*;

/**
 * @author Kim Jung-tae(altkey)
 * @since
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EnumUtilsTest {
    //
    @Test
    void getKeyEnumTest() {
        final String key = "AES";
        CryptoAlgorithm cryptoAlgorithm = EnumUtils.getKeyEnum(CryptoAlgorithm.class, key);
        // 맞는지 확인한다
        assert cryptoAlgorithm == CryptoAlgorithm.AES;
        Assertions.assertThrows(IllegalArgumentException.class, () -> EnumUtils.getKeyEnum(BlockTransformation.class, key));
    }
}