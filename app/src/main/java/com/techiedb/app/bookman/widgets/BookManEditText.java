package com.techiedb.app.bookman.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.techiedb.app.bookman.utils.LogUtils;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since May.10.2015
 *
 * Displaying the customized fonts for EditText
 */
public class BookManEditText extends EditText {
  public static final String TAG = LogUtils.makeLogTag(BookManEditText.class);

  public BookManEditText(Context context) {
    super(context, null);
    TypefaceCache.setCustomTypeface(context, this, null);
    LogUtils.LOGD(TAG, String.format("Loading the customized for default edit-text"));
  }

  public BookManEditText(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypefaceCache.setCustomTypeface(context, this, attrs);
    LogUtils.LOGD(TAG, String.format("Loading the customized for default edit-text"));

  }

  public BookManEditText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypefaceCache.setCustomTypeface(context, this, attrs);
    LogUtils.LOGD(TAG, String.format("Loading the customized for default edit-text"));
  }
}
