package com.techiedb.app.bookman.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.techiedb.app.bookman.R;

import java.util.Hashtable;

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
 * The cache storage for storing the fonts which other components that was displayed into the components.
 */
public class TypefaceCache {
  private static final int VARIATION_NORMAL = 0;
  private static final int VARIATION_LIGHT = 1;

  private static final Hashtable<String, Typeface> mTypefaceCache = new Hashtable<>();

  public static Typeface getTypeface(Context context) {
    return getTypeface(context, Typeface.NORMAL, VARIATION_NORMAL);
  }

  private static Typeface getTypeface(Context context, int fontStyle, int variation) {
    if (context == null) {
      return null;
    }

    final String typefaceName;
    if (variation == VARIATION_LIGHT) {
      switch (fontStyle) {
        case Typeface.BOLD:
          typefaceName = "OpenSans-LightBold.ttf";
          break;
        case Typeface.ITALIC:
          typefaceName = "OpenSans-LightItalic.ttf";
          break;
        case Typeface.BOLD_ITALIC:
          typefaceName = "OpenSans-LightBoldItalic.ttf";
          break;
        default:
          typefaceName = "OpenSans-Light.ttf";
          break;
      }
    } else {
      switch (fontStyle) {
        case Typeface.BOLD:
          typefaceName = "OpenSans-Bold.ttf";
          break;
        case Typeface.ITALIC:
          typefaceName = "OpenSans-Italic.ttf";
          break;
        case Typeface.BOLD_ITALIC:
          typefaceName = "OpenSans-BoldItalic.ttf";
          break;
        default:
          typefaceName = "OpenSans-Regular.ttf";
          break;
      }
    }
    return getTypefaceForTypefaceName(context, typefaceName);
  }

  protected static Typeface getTypefaceForTypefaceName(Context context, String typefaceName) {
    if (!mTypefaceCache.containsKey(typefaceName)) {
      Typeface typeface = Typeface.createFromAsset(context.getApplicationContext().getAssets(), "fonts/" + typefaceName);
      if (typeface != null) {
        mTypefaceCache.put(typefaceName, typeface);
      }
    }
    return mTypefaceCache.get(typefaceName);
  }

  /**
   * Sets the typeface for a TextView (or TextView descendant such as EditText or Button) based on the passed attributes,
   * defaults to normal typeface.
   */
  protected static void setCustomTypeface(Context context, TextView textView, AttributeSet attrs) {
    if (context == null || textView == null) {
      return;
    }
    // skip at design-time
    if (textView.isInEditMode()) {
      return;
    }

    int variation = TypefaceCache.VARIATION_NORMAL;
    if (attrs != null) {
      TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BookManTextView, 0, 0);
      if (array != null) {
        try {
          variation = array.getInteger(R.styleable.BookManTextView_fontVariation, TypefaceCache.VARIATION_NORMAL);
        } finally {
          array.recycle();
        }
      }
    }
    //determine the font style from the existing typeface
    final int fontStyle;
    if (textView.getTypeface() != null) {
      boolean isBold = textView.getTypeface().isBold();
      boolean isItalic = textView.getTypeface().isItalic();

      if (isBold && isItalic) {
        fontStyle = Typeface.BOLD_ITALIC;
      } else if (isBold) {
        fontStyle = Typeface.BOLD;
      } else if (isItalic) {
        fontStyle = Typeface.ITALIC;
      } else {
        fontStyle = Typeface.NORMAL;
      }
    } else {
      fontStyle = Typeface.NORMAL;
    }

    Typeface typeface = getTypeface(context, fontStyle, variation);
    if (typeface != null) {
      textView.setTypeface(typeface);
    }
  }
}
