package com.techiedb.app.bookman;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Security;

/**
 * Copyright (C) 2015 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion
 * Division. This software and its documentation are confidential and proprietary information of
 * Techie Digital Benchwork Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the
 * prior written consent of Techie Digital Benchwork. Techie Digital Benchwork makes no
 * representations with respect to the contents, and assumes no responsibility for any errors that
 * might appear in the software and documents. This publication and the contents hereof are subject
 * to change without notice. History
 *
 * @author Larry Pham
 * @since 2015.Feb.22
 *
 * BookMan         Revision:
 */
public class AndroidSystemProperties {
  private static Method mMethod;
  static {
    try {
      Class<?> clazz = AndroidSystemProperties.class.getClassLoader().loadClass("android.os.SystemProperties");
      mMethod = clazz.getDeclaredMethod("get", String.class, String.class);
    } catch (ClassNotFoundException | SecurityException | NoSuchMethodException e) {
      e.printStackTrace();
    }
  }

  public static int getNativeProperty(String name, int defValue) {
    return Integer.parseInt(getNativeProperty(name, Integer.toString(defValue)));
  }

  public static String getNativeProperty(String name, String defValue) {
    try {
      if (mMethod == null) {
        return defValue;
      }

      String value = (String) mMethod.invoke(null, name, defValue);
      if (value == null || value.length() == 0) {
        return defValue;
      }
      return value;
    } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException ex) {
      throw new AssertionError(ex);
    }
  }
}
