package com.techiedb.app.bookman;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;

/**
 * Copyright (C) 2015 Techie Digital Benchwork Inc., Ltd. All rights reserved.
 *
 * Mobility Development Division, Digital Media & Communications Business, Techie Digital
 * Benchwork., Ltd.
 *
 * This software and its documentation are confidential and proprietary information of Techie
 * Digital Benchwork., Ltd.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the
 * prior written consent of Techie Digital Benchwork Inc.
 *
 * Techie Digital Benchwork makes no representations with respect to the contents, and assumes no
 * responsibility for any errors that might appear in the software and documents. This publication
 * and the contents hereof are subject to change without notice.
 *
 * History 2015.Mar.25      Larry Pham         The 1st Sprint Version
 *
 *
 * The Drawable which used to describe the animation when fading start-bitmap to end-bitmap into App's layout
 */
public class CrossFadeDrawable extends Drawable {

  public static final String TAG = Properties.PREFIX + CrossFadeDrawable.class.getSimpleName();

  private static final int TRANSITION_STARTING = 0;
  private static final int TRANSITION_RUNNING = 1;
  private static final int TRANSITION_NONE = 2;

  private int mTransitionState = TRANSITION_NONE;
  private boolean mCrossFade;
  private boolean mReverse;
  private long mStartTimeMillis;
  private int mFrom;
  private int mTo;
  private int mDuration;
  private int mOriginalDuration;
  private int mAlpha;

  private Bitmap mStart;
  private Bitmap mEnd;

  private final Paint mStartPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
  private final Paint mEndPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

  private float mStartX;
  private float mStartY;

  private float mEndX;
  private float mEndY;

  private final Handler mHandler;
  private final Runnable mInvalidater;

  public CrossFadeDrawable(Bitmap startBitmap, Bitmap endBitmap) {
    this.mStart = startBitmap;
    this.mEnd = endBitmap;
    this.mHandler = new Handler();
    this.mInvalidater = new Runnable() {
      @Override
      public void run() {
        invalidateSelf();
      }
    };
  }

  public void startTransition(int durationMillis) {
    this.mFrom = 0;
    this.mTo = 255;
    this.mAlpha = 0;
    this.mOriginalDuration = mDuration = durationMillis;
    this.mReverse = false;
    this.mTransitionState = mStart != mEnd ? TRANSITION_STARTING : TRANSITION_NONE;
    invalidateSelf();
  }

  public void resetTransition() {
    this.mAlpha = 0;
    this.mTransitionState = TRANSITION_NONE;
    invalidateSelf();
  }

  public void reverseTransition(int duration) {
    final long time = SystemClock.uptimeMillis();
    if (time - mStartTimeMillis > mOriginalDuration) {
      if (mAlpha == 0) {
        mFrom = 0;
        mTo = 255;
        mAlpha = 0;
        mReverse = false;
      } else {
        mFrom = 255;
        mTo = 0;
        mAlpha = 255;
        mReverse = true;
      }

      mDuration = mOriginalDuration = duration;
      mTransitionState = TRANSITION_STARTING;
      mHandler.post(mInvalidater);
      return;
    }
    mReverse = !mReverse;
    mFrom = mAlpha;
    mTo = mReverse ? 0 : 255;
    mDuration =
        (int) (mReverse ? time - mStartTimeMillis : mOriginalDuration - (time - mStartTimeMillis));
    mTransitionState = TRANSITION_STARTING;
  }

  @Override
  public void draw(Canvas canvas) {
    boolean isDone = true;
    switch (mTransitionState) {
      case TRANSITION_STARTING:
        mStartTimeMillis = SystemClock.uptimeMillis();
        isDone = false;
        mTransitionState = TRANSITION_RUNNING;
        break;
      case TRANSITION_RUNNING:
        if (mStartTimeMillis >= 0) {
          float normalized = (float) (SystemClock.uptimeMillis() - mStartTimeMillis) / mDuration;
          isDone = normalized >= 1.0f;
          mAlpha = (int) (mFrom + (mTo - mFrom) * Math.min(normalized, 1.0f));
          if (isDone) {
            mTransitionState = TRANSITION_NONE;
            mHandler.post(mInvalidater);
          }
        }
        break;
    }
    final int alpha = mAlpha;
    final boolean crossFade = mCrossFade;
    Bitmap bitmap = mStart;
    Paint paint = mStartPaint;
    if (!crossFade || 255 - alpha > 0) {
      if (crossFade) {
        paint.setAlpha(255 - alpha);
      }
      canvas.drawBitmap(bitmap, mStartX, mStartY, paint);
      if (crossFade) {
        paint.setAlpha(0xFF);
      }
    }
    if (alpha > 0) {
      bitmap = mEnd;
      paint = mEndPaint;
      paint.setAlpha(alpha);
      canvas.drawBitmap(bitmap, mEndX, mEndY, paint);
      paint.setAlpha(0xFF);
    }

    if (!isDone) {
      mHandler.post(mInvalidater);
    }
  }

  public Bitmap getStart() {
    return this.mStart;
  }

  public void setStart(Bitmap startBitmap) {
    mStart = startBitmap;
    invalidateSelf();
  }

  public Bitmap getEnd() {
    return this.mEnd;
  }

  public void setEnd(Bitmap endBitmap) {
    this.mEnd = endBitmap;
    invalidateSelf();
  }

  @Override
  public void setBounds(int left, int top, int right, int bottom) {
    super.setBounds(left, top, right, bottom);
    final int width = right - left;
    final int height = bottom - top;

    mStartX = (width - mStart.getWidth()) / 2.0f;
    mStartY = (height - mStart.getHeight());

    mEndX = (width - mEnd.getWidth()) / 2.0f;
    mEndY = (height - mEnd.getHeight());
  }

  @Override
  public int getIntrinsicWidth() {
    return Math.max(mStart.getWidth(), mEnd.getWidth());
  }

  @Override
  public int getIntrinsicHeight() {
    return Math.max(mStart.getHeight(), mEnd.getHeight());
  }

  @Override
  public int getMinimumWidth() {
    return Math.max(mStart.getWidth(), mEnd.getWidth());
  }

  @Override
  public int getMinimumHeight() {
    return Math.max(mStart.getHeight(), mEnd.getHeight());
  }

  @Override
  public void setDither(boolean dither) {
    mStartPaint.setDither(true);
    mEndPaint.setDither(true);
  }

  @Override
  public void setFilterBitmap(boolean filter) {
    mStartPaint.setFilterBitmap(true);
    mEndPaint.setFilterBitmap(true);
  }

  @Override
  public void setAlpha(int alpha) {

  }

  @Override
  public void setColorFilter(ColorFilter cf) {
    mStartPaint.setColorFilter(cf);
    mEndPaint.setColorFilter(cf);
  }

  @Override
  public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }

  public void setCrossFadeEnabled(boolean enabled) {
    mCrossFade = enabled;
  }

  public boolean isCrossFadeEnabled() {
    return mCrossFade;
  }
}
