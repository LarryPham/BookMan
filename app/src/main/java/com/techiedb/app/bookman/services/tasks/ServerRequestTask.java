package com.techiedb.app.bookman.services.tasks;

import com.techiedb.app.bookman.Properties;

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
public class ServerRequestTask extends Thread {

  public static final String TAG = Properties.PREFIX + ServerRequestTask.class.getSimpleName();

  static final int PDF = 1;
  static final int UNKNOWN = 2;
  private String mAction = null;
  private RequestType mRequestType = RequestType.NONE;
  private int mRequestOwner = 0;
  private int mRequestMessage = 0;
  private boolean mCancelled = false;
  private WeakHandler mServiceHandler;

  public ServerRequestTask() {

  }

  public static enum RequestType {
    NONE, BOOKS_LIST, BOOK_DETAIL, KEYWORD_LIST
  }
}
