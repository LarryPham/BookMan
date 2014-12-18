package com.techiedb.app.bookman;

import android.util.Log;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved.
 * Mobile UX Promotion Division.
 * This software and its documentation are confidential and proprietary
 * information of Techie Digital Benchwork Inc.  No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Techie Digital Benchwork.
 * Techie Digital Benchwork makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 * History
 *
 * @author Larry Pham
 * @project BookMan
 * @since Dec.18.2014
 *
 * The class represents the algorithms for encrypting and decrypting data using the basic
 * algorithms
 */
public class CryptographyInformation {

    private final static String TAG = Properties.PREFIX + CryptographyInformation.class.getSimpleName();

    public static String encrypt(String seed, String original) throws Exception{
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, original.getBytes());
        Log.d(TAG, String.format("Result of encryption: %s", result.toString()));
        return toHex(result);
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        return cipher.doFinal(clear);
    }

    public static String descrypt(String seed, String encrypted) throws Exception{
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] encrypt = toByte(encrypted);
        byte[] result = decrypt(rawKey, encrypt);
        Log.d(TAG, String.format("Result of descrytion: %s", result.toString()));
        return new String(result);
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception{
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return cipher.doFinal(encrypted);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception{
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(seed);
        keyGen.init(128, secureRandom);
        SecretKey sKey = keyGen.generateKey();
        return sKey.getEncoded();
    }

    public static String toHex(String text){
        return toHex(text.getBytes());
    }

    public static String fromHex(String hexString){
        return new String(toByte(hexString));
    }

    public static byte[] toByte(String hexString){
        int len = hexString.length() /2;
        byte[] result = new byte[len];
        for ( int i = 0; i < len; i++){
            result[i] = Integer.valueOf(hexString.substring(2*i, 2*i + 2), 16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buffer){
        if (buffer == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buffer.length);
        for (int i = 0; i< buffer.length; i++){
            appendHex(result, buffer[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sBuffer, byte b){
        sBuffer.append(HEX.charAt(b>>4)&0x0f).append(HEX.charAt(b&0x0f));
    }
}
