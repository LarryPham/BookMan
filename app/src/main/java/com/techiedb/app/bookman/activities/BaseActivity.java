package com.techiedb.app.bookman.activities;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.controllers.BaseController;
import com.techiedb.app.bookman.models.BookDBHelper;
import com.techiedb.app.bookman.models.Image;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion
 * Division. This software and its documentation are confidential and proprietary information of
 * Techie Digital Benchwork Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the
 * prior written consent of Techie Digital Benchwork. Techie Digital Benchwork makes no
 * representations with respect to the contents, and assumes no responsibility for any errors that
 * might appear in the software and documents. This publication and the contents hereof are subject
 * to change without notice. History
 *
 * @author Larry Pham
 * @since 2014.10.02
 */
public abstract class BaseActivity extends FragmentActivity {

  private static final String TAG = Properties.PREFIX + BaseActivity.class.getSimpleName();

  @Override
  protected void onResume() {
    super.onResume();
  }

  protected BaseController controller = null;

  public abstract void invalidate();

  public abstract void invalidate(Object param);

  @Override
  protected void onDestroy() {
    if (controller != null) {
      controller.onDestroy();
    }
    super.onDestroy();
  }

  protected String getTopActivity() {
    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    ComponentName topActivity = am.getRunningTasks(1).get(0).topActivity;
    Log.d(TAG, String.format("TopActivity: %s, ClassName: %s", topActivity.getPackageName(),
                             topActivity.getClassName()));
    return topActivity.getClassName();
  }

  /**
   * Method getDefaultBookImage, used to choose the default book's image cover for books item into
   * Gallery or List of books
   */

  public Image getBookCoverDefaultImage(String key) {
    return null;
  }
}
