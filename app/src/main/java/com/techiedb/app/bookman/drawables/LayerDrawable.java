package com.techiedb.app.bookman.drawables;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since Apr.05.2015
 */
public class LayerDrawable extends Drawable implements Drawable.Callback {

  private final Rect mTempRect = new Rect();
  LayerState mLayerState;
  private int[] mPaddingLeft;
  private int[] mPaddingRight;
  private int[] mPaddingTop;
  private int[] mPaddingBottom;
  private Drawable mParent;
  private boolean mBlockSetBounds;

  public LayerDrawable(Drawable... layers) {
    this(null, layers);
  }

  public LayerDrawable(LayerState state, Drawable... layers) {
    this(state);
    int length =
  }

  public LayerDrawable(LayerState state) {
    LayerState as = createConstantState(state);
    mLayerState = as;
    if (as.mNum > 0) {
      ensurePadding();
    }
  }

  LayerState createConstantState(LayerState state) {
    return new LayerState(state, this);
  }

  private boolean reapplyPadding(int i, Rec rec) {
    final Rect rect = mTempRect;
    rec.mDrawable.getPadding(rect);
    if (rect.left != mPaddingLeft[i] || rect.top != mPaddingTop[i] || rect.right != mPaddingRight[i]
        || rect.bottom != mPaddingBottom[i]) {
      mPaddingLeft[i] = rect.left;
      mPaddingRight[i] = rect.right;
      mPaddingTop[i] = rect.top;
      mPaddingBottom[i] = rect.bottom;
      return true;
    }
    return false;
  }

  private void ensurePadding() {
    final int N = mLayerState.mNum;
    if (mPaddingLeft != null && mPaddingLeft.length >= N) {
      return;
    }
    mPaddingLeft = new int[N];
    mPaddingTop = new int[N];
    mPaddingRight = new int[N];
    mPaddingBottom = new int[N];
  }

  @Override
  public ConstantState getConstantState() {
    if (mLayerState.canConstantState()) {
      mLayerState.mChangingConfiguration = super.getChangingConfigurations();
      return mLayerState;
    }
    return null;
  }

  @Override
  public void invalidateDrawable(Drawable who) {

  }

  @Override
  public void scheduleDrawable(Drawable who, Runnable what, long when) {

  }

  @Override
  public void unscheduleDrawable(Drawable who, Runnable what) {

  }

  @Override
  public void draw(Canvas canvas) {

  }

  @Override
  public void setAlpha(int alpha) {

  }

  @Override
  public void setColorFilter(ColorFilter cf) {

  }

  @Override
  public int getOpacity() {
    return 0;
  }


  static class Rec {

    public Drawable mDrawable;
    public int mInsetLeft, mInsetTop, mInsetRight, mInsetBottom;
    public int mId;
  }

  static class LayerState extends ConstantState {

    int mNum;
    Rec[] mArray;
    int mChangingConfiguration;
    int mChildrenChangingConfiguration;

    private boolean mHaveOpacity = false;
    private int mOpacity;

    private boolean mHaveStateful = false;
    private boolean mStateful;

    private boolean mCheckedConstantState;
    private boolean mCanConstantState;

    LayerState(LayerState origin, LayerDrawable owner) {
      if (origin != null) {
        final Rec[] originRec = origin.mArray;
        final int N = origin.mNum;

        mNum = N;
        mArray = new Rec[N];

        mChangingConfiguration = origin.mChangingConfiguration;
        mChildrenChangingConfiguration = origin.mChildrenChangingConfiguration;

        for (int i = 0; i < N; i++) {
          final Rec rec = mArray[i] = new Rec();
          final Rec orgRec = originRec[i];

          rec.mDrawable = orgRec.mDrawable.getConstantState().newDrawable();
          rec.mDrawable.setCallback(owner);
          rec.mInsetLeft = orgRec.mInsetLeft;
          rec.mInsetTop = orgRec.mInsetTop;
          rec.mInsetRight = orgRec.mInsetRight;
          rec.mInsetBottom = orgRec.mInsetBottom;
          rec.mId = orgRec.mId;
        }

        mHaveOpacity = origin.mHaveOpacity;
        mOpacity = origin.mOpacity;
        mHaveStateful = origin.mHaveStateful;
        mStateful = origin.mStateful;
        mCheckedConstantState = mCanConstantState = true;
      } else {
        mNum = 0;
        mArray = null;
      }
    }

    @Override
    public Drawable newDrawable() {
      return new LayerDrawable(this);
    }

    @Override
    public int getChangingConfigurations() {
      return mChangingConfiguration;
    }

    public final int getOpacity() {
      if (mHaveOpacity) {
        return mOpacity;
      }
      final int N = mNum;
      final Rec[] array = mArray;
      int op = N > 0 ? array[0].mDrawable.getOpacity() : PixelFormat.TRANSPARENT;
      for (int i = 1; i < N; i++) {
        op = Drawable.resolveOpacity(op, array[i].mDrawable.getOpacity());
      }
      mOpacity = op;
      mHaveOpacity = true;
      return op;
    }

    public final boolean isStateful() {
      if (mHaveStateful) {
        return mStateful;
      }

      boolean stateful = false;
      final int N = mNum;
      final Rec[] array = mArray;
      for (int i = 0; i < N; i++) {
        if (array[i].mDrawable.isStateful()) {
          stateful = true;
          break;
        }
      }
      mStateful = stateful;
      mHaveStateful = true;
      return stateful;
    }

    public synchronized boolean canConstantState() {
      final Rec[] array = mArray;
      if (!mCheckedConstantState && array != null) {
        mCanConstantState = true;
        final int N = mNum;
        for (int i = 0; i < N; i++) {
          if (array[i].mDrawable.getConstantState() == null) {
            mCanConstantState = false;
            break;
          }
        }
        mCheckedConstantState = true;
      }
      return mCanConstantState;
    }
  }
}
