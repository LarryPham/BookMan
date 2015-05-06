package com.techiedb.app.bookman.services;

import com.techiedb.app.bookman.Properties;

/**
 * Copyright (C) 2014 Techie Digital Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Techie Digital Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Techie Digital
 * Inc. Techie Digital Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since Apr.09.2015
 */
public class BookBaseResult {

  public static final String TAG = Properties.PREFIX + BookBaseResult.class.getSimpleName();
  protected int mResultCode = 0;
  protected int mResultTotal = 0;
  protected int mResultSize = 0;
  protected String mResultMessage = null;

  protected int mErrorCode = 0;

  public BookBaseResult() {

  }

  public int getResultCode() {
    return mResultCode;
  }

  public void setResultCode(int resultCode) {
    mResultCode = resultCode;
  }

  public int getResultTotal() {
    return mResultTotal;
  }

  public void setResultTotal(int resultTotal) {
    mResultTotal = resultTotal;
  }

  public int getResultSize() {
    return mResultSize;
  }

  public void setResultSize(int resultSize) {
    mResultSize = resultSize;
  }

  public String getResultMessage() {
    return mResultMessage;
  }

  public void setResultMessage(String resultMessage) {
    mResultMessage = resultMessage;
  }

  public int getErrorCode() {
    return mErrorCode;
  }

  public void setErrorCode(int errorCode) {
    mErrorCode = errorCode;
  }
}
