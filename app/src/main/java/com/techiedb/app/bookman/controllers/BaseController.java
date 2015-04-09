package com.techiedb.app.bookman.controllers;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.techiedb.app.bookman.BookApp;
import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.activities.ActivityActionCounter;
import com.techiedb.app.bookman.activities.ActivityHandler;
import com.techiedb.app.bookman.activities.BaseActivity;
import com.techiedb.app.bookman.fragments.BaseFragment;
import com.techiedb.app.bookman.models.BookDataModel;
import com.techiedb.app.bookman.services.BookService;
import com.techiedb.app.bookman.services.tasks.Action;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @since 2014.10.02
 */
public abstract class BaseController {

  public static final String TAG = Properties.PREFIX + BaseController.class.getSimpleName();
  protected BaseActivity mActivity;
  protected BaseFragment mFragment;
  protected BookApp mApp;
  protected BookDataModel mDataModel;

  protected ActivityBaseHandler mHandler = new ActivityBaseHandler();
  protected ActivityActionCounter mCounter = new ActivityActionCounter();

  public BaseController(BaseActivity activity, BookDataModel inDataModel) {
    this.mActivity = activity;
    this.mDataModel = inDataModel;
    this.mApp = (BookApp) mActivity.getApplicationContext();
    onCreate();
  }

  public BaseController(BaseActivity activity, BookDataModel inDataModel, boolean blnFirstRunning) {
    this.mActivity = activity;
    this.mDataModel = inDataModel;
    this.mApp = (BookApp) mActivity.getApplicationContext();
    if (blnFirstRunning) {
      mApp.removeAllHandler();
    }
    onCreate();
  }

  public BaseController(BaseFragment fragment, BookDataModel inDataModel) {
    this.mFragment = fragment;
    this.mActivity = (BaseActivity) mFragment.getActivity();
    this.mApp = (BookApp) mActivity.getApplicationContext();
    onCreate();
  }

  public BaseController(BaseFragment fragment, BookDataModel inDataModel, boolean blnFirstRunning) {
    this.mFragment = fragment;
    this.mActivity = (BaseActivity) mFragment.getActivity();
    this.mDataModel = inDataModel;
    if (blnFirstRunning) {
      mApp.removeAllHandler();
    }
    onCreate();
  }

  public void onCreate() {
    mApp.addHandler(mHandler);
  }

  public void onDestroy() {
    String fn = "onDestroy(): ";
    Log.d(TAG, fn + " removeHandler ");
    mApp.removeHandler(mHandler);
  }

  public abstract void handleMessage(Message msg);

  /**
   * Method sendMessage(). It used to send the message which have the specified content and targeted object.
   *
   * @param what The message's content.
   * @param obj  The target.
   */
  public void sendMessage(int what, Object obj) {
    Message msg = new Message();
    msg.what = what;
    msg.obj = obj;
    handleMessage(msg);
  }

  protected Intent obtainIntent(int msg) {
    Intent intent = new Intent(mActivity, BookService.class);
    intent.putExtra(Action.REQUEST_OWNER, this.hashCode());
    intent.putExtra(Action.REQUEST_MSG, msg);
    return intent;
  }

  protected boolean checkOwner(Message msg) {
    if (msg.arg1 == this.hashCode()) {
      return true;
    } else {
      Log.d(TAG, String.format("[PROC] CheckOwner false %d:%d", msg.arg1, this.hashCode()));
      return false;
    }
  }

  public ActivityActionCounter getActionCounter() {
    return mCounter;
  }

  public void setActionCounter(ActivityActionCounter counter) {
    this.mCounter = counter;
  }

  public String getTopActivity() {
    ActivityManager am = (ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE);
    ComponentName topActivity = am.getRunningTasks(1).get(0).topActivity;
    return topActivity.getClassName();
  }

  public class ActivityBaseHandler extends ActivityHandler {

    @Override
    public void callback(Message msg) {
      handleMessage(msg);
    }
  }
}
