package com.techiedb.app.bookman.utils;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techiedb.app.bookman.activities.BaseActivity;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since May.06.2015
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class LUtils {
  private static final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};
  private static final int[] STATE_UNCHECKED = new int[]{};
  private static Typeface sMediumTypeface;
  protected BaseActivity mActivity;
  private Handler mHandler = new Handler();

  private LUtils(BaseActivity activity) {
    this.mActivity = activity;
  }

  public static LUtils getInstance(BaseActivity activity) {
    return new LUtils(activity);
  }

  private static boolean hasLollipop() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }

  public void startActivityWithTransition(Intent intent, final View clickedView, final String transitionName) {
    ActivityOptions options = null;
    if (hasLollipop() && clickedView != null && !TextUtils.isEmpty(transitionName)) {
      options = ActivityOptions.makeSceneTransitionAnimation(mActivity, clickedView, transitionName);
    }

    mActivity.startActivity(intent, (options != null) ? options.toBundle() : null);
  }

  public void setMediumTypeface(TextView textView) {
    if (hasLollipop()) {
      if (sMediumTypeface == null) {
        sMediumTypeface = Typeface.create("sans-serif-medium", Typeface.NORMAL);
      }
      textView.setTypeface(sMediumTypeface);
    } else {
      textView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
    }
  }

  public int getStatusBarColor() {
    if (!hasLollipop()) {
      return Color.BLACK;
    }
    return mActivity.getWindow().getStatusBarColor();
  }

  public void setStatusBarColor(int color) {
    if (!hasLollipop()) {
      return;
    }
    mActivity.getWindow().setStatusBarColor(color);
  }

  public void setOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck, boolean allowAnimate) {
    if (!hasLollipop()) {

    }
  }

  public void compatSetOrAnimatePlusCheckIcon(final ImageView imageView, boolean isCheck, boolean allowAnimate) {
  }
}
