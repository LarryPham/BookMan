package com.techiedb.app.bookman.models;

import com.techiedb.app.bookman.Properties;

import java.io.Serializable;

/**
 * Copyright (C) 2015.Feb Techie Digital Benchwork Inc., Ltd. All rights reserved.
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
 * History 2015.Feb.19     Larry Pham         The 1st Sprint Version
 */
public class JSONBook implements Serializable {
  public static final String TAG = Properties.PREFIX + Book.class.getSimpleName();

  private long mId = 1l;
  private String mTitle = null;
  private String mSubTitle = null;
  private String mDescription = null;
  private String mImageURL = null;
  private long mISBN = 1l;

  public JSONBook() {

  }

  public long getId() {
    return mId;
  }

  public void setId(long id) {
    mId = id;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public String getSubTitle() {
    return mSubTitle;
  }

  public void setSubTitle(String subTitle) {
    mSubTitle = subTitle;
  }

  public String getDescription() {
    return mDescription;
  }

  public void setDescription(String description) {
    mDescription = description;
  }

  public String getImageURL() {
    return mImageURL;
  }

  public void setImageURL(String imageURL) {
    mImageURL = imageURL;
  }

  public long getISBN() {
    return mISBN;
  }

  public void setISBN(long ISBN) {
    mISBN = ISBN;
  }
}
