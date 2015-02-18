package com.techiedb.app.bookman.activities;

import android.util.Log;

import com.techiedb.app.bookman.Properties;

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
 * @since 2014.10.02 Using the ActivityActionCounter object for determining the actions will be
 * handled into the Views components and requesting the actions from activities to controllers, and
 * other components.
 *
 * For managing the work-flow for current project, we need to determine the amount of actions, which
 * will be handled or be destructed
 */
public class ActivityActionCounter {

  private static final String TAG = Properties.PREFIX + ActivityActionCounter.class.getSimpleName();
  private int mActionCount = 0;

  public synchronized void incrementActionCount() {
    this.mActionCount++;
    Log.d(TAG, String.format("ActionCount was increased: %d", mActionCount));
  }

  public synchronized void decrementActionCount() {
    this.mActionCount--;
    Log.d(TAG, String.format("ActionCount was decreased: %d", mActionCount));
  }

  public synchronized int getCount() {
    return this.mActionCount;
  }

  public synchronized void reset() {
    this.mActionCount = 0;
    Log.d(TAG, String.format("ActionCount was reset"));
  }
}