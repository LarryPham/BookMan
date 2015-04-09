package com.techiedb.app.bookman;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Copyright (C) 2015 Techie Digital Benchwork Inc., Ltd. All rights reserved.
 *
 * Mobility Development Division, Digital Media & Communications Business, Techie Digital Benchwork., Ltd.
 *
 * This software and its documentation are confidential and proprietary information of Techie Digital Benchwork., Ltd.  No part of the
 * software and documents may be copied, reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form
 * without the prior written consent of Techie Digital Benchwork Inc.
 *
 * Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice.
 *
 * History 2015.Mar.25      Larry Pham         The 1st Sprint Version
 */
public class ShadowLayout extends FrameLayout {

  private static final String TAG = Properties.PREFIX + ShadowLayout.class.getSimpleName();

  private int mShadowColor;
  private float mShadowRadius;
  private float mCornerRadius;
  private float mDx;
  private float mDy;

  public ShadowLayout(Context context) {
    super(context);
    initialize(context, null);
  }

  public ShadowLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
    initialize(context, attrs);
  }

  public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initialize(context, attrs);
  }

  private void initialize(Context context, AttributeSet attrs) {
    TypedArray styles = context.obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
    if (styles == null) {
      return;
    }

    try {
      mCornerRadius = styles.getDimension(R.styleable.ShadowLayout_CornerRadius,
                                          getResources().getDimension(R.dimen.default_book_item_corner_radius));
      mShadowRadius = styles.getDimension(R.styleable.ShadowLayout_ShadowRadius,
                                          getResources().getDimension(R.dimen.default_book_item_shadow_radius));
      mDx = styles.getDimension(R.styleable.ShadowLayout_Dx, 0);
      mDy = styles.getDimension(R.styleable.ShadowLayout_Dy, 0);
      mShadowColor = styles.getColor(R.styleable.ShadowLayout_ShadowColor,
                                     getResources().getColor(R.color.book_item_shadow_color));
    } finally {
      styles.recycle();
    }

    int xPadding = (int) (mShadowRadius + Math.abs(mDx));
    int yPadding = (int) (mShadowRadius + Math.abs(mDy));
    setPadding(xPadding, yPadding, xPadding, yPadding);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (w > 0 && h > 0) {
      setBackgroundCompat(w, h);
    }
  }

  private void setBackgroundCompat(int w, int h) {
    Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadowRadius, mDx, mDy, mShadowColor,
                                       Color.TRANSPARENT);
    BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
      setBackgroundDrawable(drawable);
    } else {
      setBackground(drawable);
    }
  }

  private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                    float dx, float dy, int shadowColor, int fillColor) {
    Bitmap outBitmap = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(outBitmap);

    RectF shadowBoundary = new RectF(shadowRadius, shadowRadius, shadowWidth - shadowRadius, shadowHeight - shadowRadius);
    if (dy > 0) {
      shadowBoundary.top += dy;
      shadowBoundary.bottom -= dy;
    } else if (dy < 0) {
      shadowBoundary.top += Math.abs(dy);
      shadowBoundary.bottom -= Math.abs(dy);
    }

    if (dx > 0) {
      shadowBoundary.left += dx;
      shadowBoundary.right -= dx;
    } else if (dx < 0) {
      shadowBoundary.left += Math.abs(dx);
      shadowBoundary.right -= Math.abs(dx);
    }

    Paint shadowPaint = new Paint();
    shadowPaint.setAntiAlias(true);
    shadowPaint.setColor(fillColor);
    shadowPaint.setStyle(Paint.Style.FILL);

    if (!isInEditMode()) {
      shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
    }

    canvas.drawRoundRect(shadowBoundary, cornerRadius, cornerRadius, shadowPaint);
    return outBitmap;
  }
}
