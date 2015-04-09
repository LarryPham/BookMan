package com.techiedb.app.bookman;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @project BookMan
 * @since Dec.18.2014
 */
public class TextProgressBar extends ProgressBar {

  public static final String TAG = Properties.PREFIX + TextProgressBar.class.getSimpleName();
  private String mContent = "";
  private TextPaint mTextPaint;

  public TextProgressBar(Context context) {
    super(context);
    initTextPaint();
  }

  public TextProgressBar(Context context, AttributeSet attrs) {
    super(context, attrs);
    initTextPaint();
  }

  public TextProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initTextPaint();
  }

  private void initTextPaint() {
    mTextPaint = new TextPaint();
    mTextPaint.setColor(Color.BLACK);
    mTextPaint.setTextSize(14);
    mTextPaint.setAntiAlias(true);
  }

  @Override
  protected synchronized void onDraw(Canvas canvas) {
    /// First draw the regular progress bar, then custom draw out the text content.
    super.onDraw(canvas);
    Rect bounds = new Rect();
    mTextPaint.getTextBounds(mContent, 0, mContent.length(), bounds);
    int dx = getWidth() / 2 - bounds.centerX();
    int dy = getHeight() / 2 - bounds.centerY();
    canvas.drawText(mContent, dx, dy, mTextPaint);
  }

  public synchronized void setText(String text) {
    this.mContent = text;
    drawableStateChanged();
  }

  public void setTextColor(int color) {
    mTextPaint.setColor(color);
    drawableStateChanged();
  }
}
