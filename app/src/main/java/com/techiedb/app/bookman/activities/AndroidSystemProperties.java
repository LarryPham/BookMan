package com.techiedb.app.bookman.activities;

import java.lang.reflect.Method;

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
 * @since 2014.10.08
 * Using the android-system properties for reflecting the methods from android packages.
 */
public class AndroidSystemProperties {
    private static Method mMethod;
    static {
        try{
            Class<?> clazz = AndroidSystemProperties.class.getClassLoader().loadClass("android.os.SystemProperties");
            mMethod = clazz.getDeclaredMethod("get", new Class<?>[]{String.class, String.class});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException ex){
            ex.printStackTrace();
        }
    }
}
