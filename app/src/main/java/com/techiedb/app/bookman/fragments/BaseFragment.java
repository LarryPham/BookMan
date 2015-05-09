package com.techiedb.app.bookman.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.activities.BaseActivity;
import com.techiedb.app.bookman.controllers.BaseController;
import com.techiedb.app.bookman.models.BookDataModel;

import java.util.HashMap;

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
public abstract class BaseFragment extends Fragment {

  protected static final String TAG = Properties.PREFIX + BaseFragment.class.getSimpleName();
  protected BaseController mController = null;
  protected BookDataModel mDataModel;
  protected BaseActivity mActivity;
  protected View mLayoutView;
  protected Bundle mBundle = new Bundle();
  protected HashMap<String, Object> mTags;

  protected String mFragmentKey;
  private boolean mReceivedKeyboardState;

  /**
   * Sets the Extras Bundle to an instance of {@link BaseFragment} class, which will be assigned by something variables that instance
   * used to handle something into this screen
   * @param extras The Bundle's object.
   */
  public void setBundle(Bundle extras) {
    this.mBundle = extras;
  }

  /**
   * Gets the Extras Bundle object which as integrated into the current fragment object
   * @return The Bundle object
   */
  public Bundle getBundle() {
    return this.mBundle;
  }

  /**
   * Sets the FragmentKey which has integrated with key that <code>BaseActivity</code> used to call the BaseFragment instance by them
   * @param fragmentKey The FragmentKey String Type.
   */
  public void setFragmentKey(String fragmentKey) {
    mFragmentKey = fragmentKey;
  }

  /**
   * Gets the FragmentKey
   * @return The Fragment's Key value
   */
  public String getFragmentKey() {
    return this.mFragmentKey;
  }

  @Override
  public void onResume() {
    super.onResume();
  }

  /**
   * Method used to display the keyboard onto current screen
   */
  protected void onKeyboardShow() {

  }

  /**
   * Method used to hide the keyboard onto current screen, after checking the existence of keyboard's session onto current fragment screen
   */
  protected void onKeyBoardHide() {

  }

  /**
   * Checking the Keyboard are showing or not.
   * @return Boolean Type.
   */
  public boolean isKeyboardShowing() {
    return false;
  }

  /**
   * Gets the Tag object which has been integrated into the current fragment, sometime, we can pass the object from activity or fragment
   * to it by using <code>Tag</code> object that it's other way for passing object between 2 components like fragment or activity easier
   * than using parcelable object.
   * @param key The Tag's keyword.
   * @return The Tag object which has been integrated into the current fragment.
   */
  public Object getTag(String key) {
    if (mTags != null) {
      return mTags.get(key);
    }
    return null;
  }

  /**
   * Sets the Tag object which will be integrated into this current fragment, if you want to pass the object to current fragment, let's
   * use this method for passing object which will be processed into current fragment
   * @param key The Tag's keyword
   * @param tag The Tag object which will be embedded into current fragment.
   */
  public void setTag(String key, Object tag) {
    if (mTags == null) {
      mTags = new HashMap<>();
    }
    mTags.put(key, tag);
  }

  @Override
  public void onDestroy() {
    if (mController != null) {
      mController.onDestroy();
    }
    super.onDestroy();
  }

  /**
   * When the controller received the data as params that sent back from application's object to them, they will feed the new updates or
   * something new after validating them to the BaseFragment instance by using this method.
   *
   * By declaring the <code>InvalidateParams</code> for assigning new notified updates to current fragment.
   */
  public abstract void invalidate();

  /**
   * Also same meaning with method above, but it will used with couple of objects that used to validate notifications from controller.
   * @param params Objects
   */
  public abstract void invalidate(Object... params);
}
