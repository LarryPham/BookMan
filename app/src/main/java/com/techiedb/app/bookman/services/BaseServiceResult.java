package com.techiedb.app.bookman.services;

import com.techiedb.app.bookman.Properties;

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
 * @since Dec.20.2014
 */
public class BaseServiceResult {

  public static final String TAG = Properties.PREFIX + BaseServiceResult.class.getSimpleName();
  protected int mResultCode = 0;
  protected int mResultTotal = 0;
  protected int mResultIndex = 0;
  protected int mResultSize = 0;
  protected String mResultMessage = null;

  protected int mErrorCode = 0;

  public BaseServiceResult() {

  }

  public int getResultCode() {
    return mResultCode;
  }

  public void setResultCode(int mResultCode) {
    this.mResultCode = mResultCode;
  }

  public int getResultTotal() {
    return mResultTotal;
  }

  public void setResultTotal(int mResultTotal) {
    this.mResultTotal = mResultTotal;
  }

  public int getResultIndex() {
    return mResultIndex;
  }

  public void setResultIndex(int mResultIndex) {
    this.mResultIndex = mResultIndex;
  }

  public int getResultSize() {
    return mResultSize;
  }

  public void setResultSize(int mResultSize) {
    this.mResultSize = mResultSize;
  }

  public String getResultMessage() {
    return mResultMessage;
  }

  public void setResultMessage(String mResultMessage) {
    this.mResultMessage = mResultMessage;
  }

  public int getErrorCode() {
    return mErrorCode;
  }

  public void setErrorCode(int mErrorCode) {
    this.mErrorCode = mErrorCode;
  }
}
