package com.techiedb.app.bookman.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.techiedb.app.bookman.R;
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
 */
public class BookManTextView extends TextView {
  public static final String TAG = LogUtils.makeLogTag(BookManTextView.class);
  protected boolean mFixWidowWordEnabled;

  public BookManTextView(Context context) {
    super(context, null);
    LogUtils.LOGD(TAG, "Loading customized fonts for default TextView");
    TypefaceCache.setCustomTypeface(context, this, null);
  }

  public BookManTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    LogUtils.LOGD(TAG, "Loading customized fonts for default TextView");
    TypefaceCache.setCustomTypeface(context, this, attrs);
  }

  public BookManTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LogUtils.LOGD(TAG, "Loading customized fonts for default TextView");
    TypefaceCache.setCustomTypeface(context, this, attrs);
  }

  @Override
  public void setText(CharSequence text, BufferType type) {
    if (!mFixWidowWordEnabled) {
      super.setText(text, type);
      return;
    }
    Spannable out;
    int lastSpace = text.toString().lastIndexOf(' ');
    if (lastSpace != -1 && lastSpace < text.length() - 1) {
      // Replace last space character by a non breaking space.
      CharSequence tmpText = replaceCharacter(text, lastSpace, "\u00A0");
      out = new SpannableString(tmpText);
      // Restore spans if text is an instance of Spanned

    }
  }

  private void readCustomAttrs(Context context, AttributeSet attrs) {
    TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BookManTextView, 0, 0);
    if (array != null) {
      mFixWidowWordEnabled = array.getBoolean(R.styleable.BookManTextView_fixWidowWords, false);
      if (mFixWidowWordEnabled) {
        // Force text update
        setText(getText());
      }
    }
  }

  private CharSequence replaceCharacter(CharSequence source, int charIndex, CharSequence replacement) {
    if (charIndex != -1 && charIndex < source.length() - 1) {
      return TextUtils.concat(source.subSequence(0, charIndex), replacement, source.subSequence(charIndex + 1, source.length()));
    }
    return source;
  }
}
