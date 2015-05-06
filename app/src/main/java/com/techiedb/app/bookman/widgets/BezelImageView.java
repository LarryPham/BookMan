package com.techiedb.app.bookman.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.techiedb.app.bookman.R;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since Apr.17.2015
 */
public class BezelImageView extends ImageView {
  private Paint mBlackPaint;
  private Paint mMaskedPaint;
  private Rect mBounds;
  private RectF mBoundsF;

  private Drawable mBorderDrawable;
  private Drawable mMaskDrawable;

  private ColorMatrixColorFilter mDesaturateColorFilter;
  private boolean mDesaturateOnPress = false;
  private boolean mCacheValid = false;
  private Bitmap mCachedBitmap;
  private int mCachedWidth;
  private int mCachedHeight;

  public BezelImageView(Context context) {
    this (context, null);
  }

  public BezelImageView(Context context, AttributeSet attrs) {
    this (context, attrs, 0);
  }

  public BezelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    // Attributes initialization
    final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BezelImageView, defStyleAttr, 0);
    mMaskDrawable = array.getDrawable(R.styleable.BezelImageView_maskDrawable);
    if (mMaskDrawable != null) {
      mMaskDrawable.setCallback(this);
    }

    mBorderDrawable = array.getDrawable(R.styleable.BezelImageView_borderDrawable);
    if (mBorderDrawable != null) {
      mBorderDrawable.setCallback(this);
    }

    mDesaturateOnPress = array.getBoolean(R.styleable.BezelImageView_desaturateOnPress, mDesaturateOnPress);
    array.recycle();

    // Other initialization
    mBlackPaint = new Paint();
    mBlackPaint.setColor(0xff000000);

    mMaskedPaint = new Paint();
    mMaskedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

    // Always want a cache allocated.
    mCachedBitmap = Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);
    if (mDesaturateOnPress) {
      // Create a desaturate color filter for pressed state.
      ColorMatrix colorMatrix = new ColorMatrix();
      colorMatrix.setSaturation(0);
      mDesaturateColorFilter = new ColorMatrixColorFilter(colorMatrix);
    }

  }

  @Override
  protected boolean setFrame(int left, int top, int right, int bottom) {
    final boolean changed = super.setFrame(left, top, right, bottom);
    mBounds = new Rect(0,0, right - left, bottom - top);
    mBoundsF = new RectF(mBounds);

    if (mBorderDrawable != null) {
      mBorderDrawable.setBounds(mBounds);
    }

    if (mMaskDrawable != null) {
      mMaskDrawable.setBounds(mBounds);
    }

    if (changed) {
      mCacheValid = false;
    }

    return changed;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (mBounds == null) {
      return;
    }

    int width = mBounds.width();
    int height = mBounds.height();

    if (width == 0 || height == 0) {
      return;
    }
    // Need to redraw the cache
    if (!mCacheValid || width != mCachedWidth || height != mCachedHeight) {
      // Have a correct-sized bitmap cache already allocated. Just erase it.
      mCachedBitmap.eraseColor(0);
    } else {
      // Allocate a new bitmap with the correct dimensions.
      mCachedBitmap.recycle();
      //noinspection AndroidLintDrawAllocation
      mCachedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      mCachedWidth = width;
      mCachedHeight = height;
    }

    Canvas cachedCanvas = new Canvas(mCachedBitmap);
    if (mMaskDrawable != null) {
      int saveCanvas = cachedCanvas.save();
      mMaskDrawable.draw(cachedCanvas);
      mMaskedPaint.setColorFilter((mDesaturateOnPress && isPressed()) ? mDesaturateColorFilter : null);
      cachedCanvas.saveLayer(mBoundsF, mMaskedPaint, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
      super.onDraw(cachedCanvas);
      cachedCanvas.restoreToCount(saveCanvas);
    } else if (mDesaturateOnPress && isPressed()) {
      int saveCanvas = cachedCanvas.save();
      cachedCanvas.drawRect(0, 0, mCachedWidth, mCachedHeight, mBlackPaint);
      mMaskedPaint.setColorFilter(mDesaturateColorFilter);
      cachedCanvas.saveLayer(mBoundsF, mMaskedPaint, Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
      super.onDraw(cachedCanvas);
      cachedCanvas.restoreToCount(saveCanvas);
    } else {
      super.onDraw(canvas);
    }
    if (mBorderDrawable != null) {
      mBorderDrawable.draw(cachedCanvas);
    }

    // Draw from cache
    canvas.drawBitmap(mCachedBitmap, mBounds.left, mBounds.top, null);
  }

  @Override
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    if (mBorderDrawable != null && mBorderDrawable.isStateful()) {
      mBorderDrawable.setState(getDrawableState());
    }
    if (mMaskDrawable != null && mMaskDrawable.isStateful()) {
      mMaskDrawable.setState(getDrawableState());
    }

    if (isDuplicateParentStateEnabled()) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  @Override
  public void invalidateDrawable(Drawable dr) {
    if (dr == mBorderDrawable || dr == mMaskDrawable) {
      invalidate();
    } else {
      super.invalidateDrawable(dr);
    }
  }

  @Override
  protected boolean verifyDrawable(Drawable dr) {
    return dr == mBorderDrawable || dr == mMaskDrawable || super.verifyDrawable(dr);
  }
}
