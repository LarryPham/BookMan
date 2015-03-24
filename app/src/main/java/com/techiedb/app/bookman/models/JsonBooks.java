package com.techiedb.app.bookman.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2015 Techie Digital Benchwork Inc., Ltd. All rights reserved.
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
 * History 2015.Mar.24      Larry Pham         The 1st Sprint Version
 */
public class JsonBooks {
  private String mTotal;
  private int mPage;
  private List<JsonBookItem> mJsonBookList=  new ArrayList<JsonBookItem>();

  public JsonBooks() {

  }

  public String getTotal() {
    return mTotal;
  }

  public void setTotal(String total) {
    mTotal = total;
  }

  public int getPage() {
    return mPage;
  }

  public void setPage(int page) {
    mPage = page;
  }

  public List<JsonBookItem> getJsonBookList() {
    return mJsonBookList;
  }

  public void setJsonBookList(List<JsonBookItem> jsonBookList) {
    mJsonBookList = jsonBookList;
  }
}
