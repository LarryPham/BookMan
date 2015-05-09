package com.techiedb.app.bookman.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since May.06.2015
 *
 *
 * Utilities and constants related to app preferences.
 */
public class PreferenceUtils {
  private static final String TAG = LogUtils.makeLogTag("PreferenceUtils");

  /**
   * Boolean preference that indicates whether we installed the bootstrap data or not.
   */
  public static final String PREF_DATA_BOOTSTRAP_DONE = "pref_data_bootstrap_done";
  /**
   * Boolean indicating whether we should attempt to sign in on startup (default)
   */
  public static final String PREF_USER_REFUSED_SIGN_IN = "pref_user_refused_sign_in";
  /**
   * Boolean indicating if we performed the (one-time) welcome flow.
   */
  public static final String PREF_WELCOME_DONE = "pref_welcome_done";
  /**
   * Boolean indicating whether TOS has been accepted
   */
  public static final String PREF_TOS_ACCEPTED = "pref_tos_accepted";

  public static void markDataBootstrapDone(final Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    sp.edit().putBoolean(PREF_DATA_BOOTSTRAP_DONE, true).apply();
  }

  public static boolean isDataBootstrapDone(final Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getBoolean(PREF_DATA_BOOTSTRAP_DONE, false);
  }

  public static void markUserRefusedSignin(final Context context) {
    markUserRefusedSignin(context, true);
  }

  public static void markUserRefusedSignin(final Context context, final boolean refused) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    sp.edit().putBoolean(PREF_USER_REFUSED_SIGN_IN, refused).apply();
  }

  public static boolean hasUserRefusedSignin(final Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getBoolean(PREF_USER_REFUSED_SIGN_IN, false);
  }

  public static boolean isToAccepted(final Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getBoolean(PREF_TOS_ACCEPTED, false);
  }

  public static void markTosAccepted(final Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    sp.edit().putBoolean(PREF_TOS_ACCEPTED, true).apply();
  }

  public static void markWelcomeDone(final Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    sp.edit().putBoolean(PREF_WELCOME_DONE, true).apply();
  }

  public static boolean isWelcomeDone(final Context context) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    return sp.getBoolean(PREF_WELCOME_DONE, false);
  }

  public static void registerOnSharedPreferenceChangeListener(final Context context, SharedPreferences.OnSharedPreferenceChangeListener
      listener) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    sp.registerOnSharedPreferenceChangeListener(listener);
  }

  public static void unregisterOnSharedPreferenceChangeListener(final Context context, SharedPreferences.OnSharedPreferenceChangeListener
      listener) {
    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
    sp.unregisterOnSharedPreferenceChangeListener(listener);
  }
}
