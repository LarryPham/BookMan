package com.techiedb.app.bookman.services;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.models.JsonBooks;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since Apr.09.2015
 */
public class BooksResult extends BookBaseResult {

  public static final String TAG = Properties.PREFIX + BooksResult.class.getSimpleName();

  public JsonBooks mJsonBooks = new JsonBooks();

  public BooksResult() {

  }

  public BooksResult(JsonBooks jsonBooks) {
    this.mJsonBooks = jsonBooks;
  }

  public JsonBooks getJsonBooks() {
    return mJsonBooks;
  }

  public void setJsonBooks(JsonBooks jsonBooks) {
    mJsonBooks = jsonBooks;
  }
}
