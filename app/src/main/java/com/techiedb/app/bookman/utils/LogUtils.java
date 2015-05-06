package com.techiedb.app.bookman.utils;

import android.util.Log;

import com.techiedb.app.bookman.BuildConfig;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since Apr.26.2015
 */
public class LogUtils {
  public static final String LOG_PREFIX = "[BookMan].";
  private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
  private static final int MAX_LOG_TAG_LENGTH = 23;

  public static String makeLogTag(String tagContent) {
    if (tagContent.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
      return LOG_PREFIX + tagContent.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH -1);
    }
    return LOG_PREFIX + tagContent;
  }

  /**
   * Don't use this when obfuscating class name!
   */
  public static String makeLogTag(Class clazz) {
    return makeLogTag(clazz.getSimpleName());
  }

  public static void LOGD(final String tag, String message) {
    // noinspection PointlessBooleanExpression, ConstantConditions
    if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
      Log.d(tag, message);
    }
  }

  public static void LOGD(final String tag, String message, Throwable cause) {
    // noinspection PointlessBooleanExpression, ConstantConditions
    if (BuildConfig.DEBUG || Log.isLoggable(tag, Log.DEBUG)) {
      Log.d(tag, message, cause);
    }
  }

  public static void LOGV(final String tag, String message) {
    // noinspection PointlessBooleanExpression, ConstantConditions
    if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
      Log.v(tag, message);
    }
  }

  public static void LOGV(final String tag, String message, Throwable cause) {
    if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
      Log.v(tag, message, cause);
    }
  }

  public static void LOGI(final String tag, String message) {
    Log.i(tag, message);
  }

  public static void LOGI(final String tag, String message, Throwable cause) {
    Log.i(tag, message, cause);
  }

  public static void LOGW(final String tag, String message, Throwable cause) {
    Log.w(tag, message, cause);
  }

  public static void LOGW(final String tag, String message) {
    Log.w(tag, message);
  }

  public static void LOGE(final String tag, String message, Throwable cause) {
    Log.e(tag, message, cause);
  }

  public static void LOGE(final String tag, String message) {
    Log.e(tag, message);
  }

  private LogUtils() {

  }

}
