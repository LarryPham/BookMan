package com.techiedb.app.bookman.models;

import android.graphics.Bitmap;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion
 * Division. This software and its documentation are confidential and proprietary information of
 * Techie Digital Benchwork Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the
 * prior written consent of Techie Digital Benchwork. Techie Digital Benchwork makes no
 * representations with respect to the contents, and assumes no responsibility for any errors that
 * might appear in the software and documents. This publication and the contents hereof are subject
 * to change without notice. History
 *
 * @author Larry Pham
 * @since 2014.10.03
 */
public class Image {

  private String mURL = null;
  private String mTitle = null;
  private String mLink = null;
  private int mWidth = 0;
  private int mHeight = 0;
  private String mDescription = null;

  private Bitmap mBitmap = null;

  public Image() {

  }

  public Image(Bitmap bitmap) {
    this.mBitmap = bitmap;
  }

  public Image(String url, String title, String link) {
    this.mURL = url;
    this.mTitle = title;
    this.mLink = link;
  }

  public String getUrl() {
    return this.mURL;
  }

  public String getTitle() {
    return this.mTitle;
  }

  public String getLink() {
    return this.mLink;
  }

  public int getWidth() {
    return mWidth;
  }

  public int getHeight() {
    return mHeight;
  }

  public String getDescription() {
    return this.mDescription;
  }

  public void setDescription(String description) {
    this.mDescription = description;
  }

  public void setUrl(String url) {
    this.mURL = url;
  }

  public void setTitle(String title) {
    this.mTitle = title;
  }

  public void setWidth(int width) {
    this.mWidth = width;
  }

  public void setHeight(int height) {
    this.mHeight = height;
  }

  public void setWidth(String szWidth) {
    int width = 0;
    if (szWidth != null && szWidth.length() > 0) {
      width = Integer.parseInt(szWidth) + 1;
    }
    this.mWidth = width;
  }

  public void setHeight(String szHeight) {
    int height = 0;
    if (szHeight != null && szHeight.length() > 0) {
      height = Integer.parseInt(szHeight) + 1;
    }
    this.mHeight = height;
  }

  public Bitmap getBitmap() {
    return mBitmap;
  }

  public void setBitmap(Bitmap bitmap) {
    this.mBitmap = bitmap;
    setHeight(bitmap.getHeight());
    setWidth(bitmap.getWidth());
  }

}
