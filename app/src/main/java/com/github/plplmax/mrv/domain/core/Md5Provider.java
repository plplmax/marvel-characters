package com.github.plplmax.mrv.domain.core;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface Md5Provider {
    String getMd5(String data);

    class Base implements Md5Provider {
        private static final String ALGORITHM = "MD5";
        private static final byte HEX_LENGTH = 32;
        private static final short BYTE_BITMASK = 0xFF;

        @Override
        public String getMd5(String data) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

                byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
                messageDigest.update(dataBytes);
                byte[] hash = messageDigest.digest();

                StringBuilder builder = new StringBuilder(HEX_LENGTH);

                for (byte b : hash) {
                    int unsignedByte = (b & BYTE_BITMASK);
                    String hexByte = Integer.toHexString(unsignedByte);
                    
                    if (hexByte.length() < 2) builder.append('0');
                    builder.append(hexByte);
                }

                return builder.toString();
            } catch (NoSuchAlgorithmException e) {
                return "";
            }
        }
    }
}
