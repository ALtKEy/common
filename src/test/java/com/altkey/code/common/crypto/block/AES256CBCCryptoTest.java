package com.altkey.code.common.crypto.block;

import com.altkey.code.common.crypto.Crypto;
import com.altkey.code.common.crypto.CryptoFactory;
import com.altkey.code.common.crypto.enums.BlockTransformation;
import com.altkey.code.common.crypto.enums.CryptoAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.security.InvalidKeyException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AES256CBCCryptoTest {
    //
    @Test
    void encrypt() throws InvalidKeyException {
        Crypto crypto = CryptoFactory.getBlockCrypto(CryptoAlgorithm.AES, BlockTransformation.CBC, "01234567890123456".getBytes());
        String encrypt = crypto.encrypt("test");
        System.out.println(encrypt);
        assert null != encrypt;
    }

    @Test
    void decrypt() throws InvalidKeyException {
        Crypto crypto = CryptoFactory.getBlockCrypto(CryptoAlgorithm.AES, BlockTransformation.CBC, "01234567890123456".getBytes());
        String encrypt = crypto.encrypt("test");
        String decrypt = crypto.decrypt(encrypt);
        System.out.println("test");
        assert "test".equals(decrypt);
    }
}