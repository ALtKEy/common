package com.altkey.code.common.crypto;

import com.altkey.code.common.crypto.block.AES256CBCCrypto;
import com.altkey.code.common.crypto.enums.BlockTransformation;
import com.altkey.code.common.crypto.enums.CryptoAlgorithm;

import java.security.InvalidKeyException;

public class CryptoFactory {
    //
    public static Crypto getBlockCrypto(CryptoAlgorithm algorithm, BlockTransformation transformation, byte[] key) throws InvalidKeyException {
        switch (algorithm) {
            case AES:
                if (transformation == BlockTransformation.CBC) {
                    return new AES256CBCCrypto(key);
                }
            default:
                throw new UnsupportedOperationException("Unsupported algorithm: " + algorithm);
        }
    }
}