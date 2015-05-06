package com.techiedb.app.bookman.models;

import com.techiedb.app.bookman.Properties;

/**
 * Copyright (C) 2015.Feb Techie Digital Benchwork Inc., Ltd. All rights reserved.
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
 * History 2015.Feb.19     Larry Pham         The 1st Sprint Version
 */
public class Error {

  public static final String TAG = Properties.PREFIX + Error.class.getSimpleName();

  private int mErrorCode = 0;
  private String mErrorMsg = null;
  private int mBookId = 0;
  private String mFeedURL = null;

  public Error() {

  }

  public Error(int errorCode) {
    setErrorCode(errorCode);
  }

  public int getErrorCode() {
    return mErrorCode;
  }

  public void setErrorCode(int errorCode) {
    mErrorCode = errorCode;
  }

  public String getErrorMsg() {
    return mErrorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    mErrorMsg = errorMsg;
  }

  public int getBookId() {
    return mBookId;
  }

  public void setBookId(int bookId) {
    mBookId = bookId;
  }

  public String getFeedURL() {
    return mFeedURL;
  }

  public void setFeedURL(String feedURL) {
    mFeedURL = feedURL;
  }

  public String toString() {
    return String.format("Error[code: %d, message: %s, BookId: %d, FeedURL: %s]",
                         this.mErrorCode, this.mErrorMsg, this.mBookId, this.mFeedURL);
  }
}
