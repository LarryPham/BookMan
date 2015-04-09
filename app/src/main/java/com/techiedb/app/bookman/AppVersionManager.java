package com.techiedb.app.bookman;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @project BookMan
 * @since Dec.18.2014
 */
public class AppVersionManager {

  public static final String TAG = Properties.PREFIX + AppVersionManager.class.getSimpleName();
  public static final String mAppId = "com.techiedb.app.bookman";
  public static final int SOCKET_TIMEOUT = 30000;

  /**
   * Method used to check the existed app's version and then show it into the playstore.
   *
   * @param context The Application Context.
   * @return Boolean Type.
   */
  public final static boolean invokeBookManApps(Context context) {
    Log.d(TAG, String.format("invokeBookManApps"));
    if (isExistBookManApps(context)) {
      Intent intent = new Intent(Intent.ACTION_MAIN);
      intent
          .setClassName("com.techiedb.app.bookman", "com.techiedb.app.bookman.MainBookManActivity");
      intent.putExtra("directcall", true);
      intent.putExtra("GUID", mAppId);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      context.startActivity(intent);
      return true;
    }
    return false;
  }

  /**
   * Method used to check the existence of current app or not
   *
   * @param context The Application Context.
   * @return Boolean Type.
   */
  public static final boolean isExistBookManApps(Context context) {
    PackageManager pm = context.getPackageManager();
    try {
      pm.getPackageInfo("com.techiedb.app.bookman", PackageManager.GET_ACTIVITIES);
      Log.d(TAG, String.format("isExistedBookManApps"));
    } catch (PackageManager.NameNotFoundException ex) {
      return false;
    }
    return true;
  }

  /**
   * Method used to check the current app's version name.
   *
   * @param context The Application Context.
   * @return String Type.
   */
  public final static String getBookManVersion(Context context) {
    String versionName = null;
    try {
      PackageInfo
          packageInfo =
          context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      versionName = packageInfo.versionName;
      Log.d(TAG, String.format("getBookManVersion: %s", versionName));
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return versionName;
  }

  /**
   * Method used to check the current app's version code.
   *
   * @param context The Application Context.
   * @return Integer type.
   */
  public final static int getBookManVersionCode(Context context) {
    int versionCode = 0;
    try {
      PackageInfo
          packageInfo =
          context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
      versionCode = packageInfo.versionCode;
      Log.d(TAG, String.format("getBookManVersionCode(): %d ", versionCode));
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return versionCode;
  }
}
