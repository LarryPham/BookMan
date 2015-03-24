package com.techiedb.app.bookman.models;

import com.techiedb.app.bookman.Properties;

import java.io.Serializable;

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
public class JsonBook implements Serializable {
  public static final String TAG = Properties.PREFIX + JsonBook.class.getSimpleName();
  private long mId = 1231243445;
  private String mTitle;
  private String mSubTitle;
  private String mDescription;
  private String mAuthor;
  private String mISBN;
  private String mYear;
  private String mPage;
  private String mPublisher;
  private Image mCoverImage;
  private String mDownloadImageURL;
  private String mDownloadURL;

  public JsonBook() {

  }

  public JsonBook(Book book) {
    this.mId = book.getID();
    this.mTitle = book.getTitle();
    this.mSubTitle = book.getSubTitle();
    this.mDescription = book.getDescription();
    this.mAuthor = book.getAuthor();
    this.mISBN = book.getISBN();
    this.mYear = String.valueOf(book.getYear());
    this.mPage = String.valueOf(book.getPage());
    this.mPublisher = book.getPublisher();
    this.mCoverImage = new Image();
    this.setDownloadImageURL(book.getImageURL());
    this.mDownloadURL = book.getContentLink();
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

  public String getAuthor() {
    return mAuthor;
  }

  public void setAuthor(String author) {
    mAuthor = author;
  }

  public String getISBN() {
    return mISBN;
  }

  public void setISBN(String ISBN) {
    mISBN = ISBN;
  }

  public String getYear() {
    return mYear;
  }

  public void setYear(String year) {
    mYear = year;
  }

  public String getPage() {
    return mPage;
  }

  public void setPage(String page) {
    mPage = page;
  }

  public String getPublisher() {
    return mPublisher;
  }

  public void setPublisher(String publisher) {
    mPublisher = publisher;
  }

  public Image getCoverImage() {
    return mCoverImage;
  }

  public void setCoverImage(Image coverImage) {
    mCoverImage = coverImage;
  }

  public String getDownloadImageURL() {
    return mDownloadImageURL;
  }

  public void setDownloadImageURL(String downloadImageURL) {
    mDownloadImageURL = downloadImageURL;
  }

  public String getDownloadURL() {
    return mDownloadURL;
  }

  public void setDownloadURL(String downloadURL) {
    mDownloadURL = downloadURL;
  }
}
