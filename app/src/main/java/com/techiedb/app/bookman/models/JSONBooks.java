package com.techiedb.app.bookman.models;

import com.techiedb.app.bookman.Properties;

import java.io.Serializable;
import java.util.ArrayList;

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
public class JSONBooks implements Serializable {
  private static final String TAG = Properties.PREFIX + JSONBooks.class.getSimpleName();
  private int mErrorCode = 0;
  private float mTime = 0.1f;
  private int mTotal = 0;
  private int mPage = 0;

  public JSONBooks() {

  }

  public int getErrorCode() {
    return mErrorCode;
  }

  public void setErrorCode(int errorCode) {
    mErrorCode = errorCode;
  }

  public float getTime() {
    return mTime;
  }

  public void setTime(float time) {
    mTime = time;
  }

  public int getTotal() {
    return mTotal;
  }

  public void setTotal(int total) {
    mTotal = total;
  }

  public int getPage() {
    return mPage;
  }

  public void setPage(int page) {
    mPage = page;
  }

}
