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
public class JSONBookDetail implements Serializable {
  public static final String TAG = Properties.PREFIX + JSONBookDetail.class.getSimpleName();

  private long mID = 1l;
  private String mTitle = null;
  private String mSubTitle = null;
  private String mDescription = null;
  private String mAuthor = null;
  private long mISBN = 1l;
  private int mYear = 0;
  private int mPage = 0;
  private String mPublisher = null;
  private String mImageURL = null;
  private String mContentLink = null;

  public JSONBookDetail() {

  }

  public long getID() {
    return mID;
  }

  public void setID(long ID) {
    mID = ID;
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

  public String getAuthor() {
    return mAuthor;
  }

  public void setAuthor(String author) {
    mAuthor = author;
  }

  public long getISBN() {
    return mISBN;
  }

  public void setISBN(long ISBN) {
    mISBN = ISBN;
  }

  public int getYear() {
    return mYear;
  }

  public void setYear(int year) {
    mYear = year;
  }

  public int getPage() {
    return mPage;
  }

  public void setPage(int page) {
    mPage = page;
  }

  public String getPublisher() {
    return mPublisher;
  }

  public void setPublisher(String publisher) {
    mPublisher = publisher;
  }

  public String getImageURL() {
    return mImageURL;
  }

  public void setImageURL(String imageURL) {
    mImageURL = imageURL;
  }

  public String getContentLink() {
    return mContentLink;
  }

  public void setContentLink(String contentLink) {
    mContentLink = contentLink;
  }
}
