package com.techiedb.app.bookman.services;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.models.JsonBook;

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
public class BookResult extends BookBaseResult {

  public static final String TAG = Properties.PREFIX + BookResult.class.getSimpleName();
  private JsonBook mBook;

  public BookResult() {

  }

  public BookResult(JsonBook jsonBook) {
    this.mBook = jsonBook;
  }

  public JsonBook getBook() {
    return mBook;
  }

  public void setBook(JsonBook book) {
    mBook = book;
  }
}
