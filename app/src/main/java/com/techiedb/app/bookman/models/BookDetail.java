package com.techiedb.app.bookman.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.techiedb.app.bookman.Properties;

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
public class BookDetail implements Parcelable {
  public static final String TAG = Properties.PREFIX + BookDetail.class.getSimpleName();

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
  private Image mImage = null;
  private Image mOriginalImage = null;

  public BookDetail() {

  }

  public BookDetail(Parcel source) {
    this.mID = source.readLong();
    this.mTitle = source.readString();
    this.mSubTitle = source.readString();
    this.mDescription = source.readString();
    this.mAuthor = source.readString();
    this.mISBN = source.readLong();
    this.mYear = source.readInt();
    this.mPage = source.readInt();
    this.mPublisher = source.readString();
    this.mImageURL = source.readString();
    this.mContentLink = source.readString();
    this.mImage = (Image) source.readValue(Image.class.getClassLoader());
    this.mOriginalImage = (Image) source.readValue(Image.class.getClassLoader());
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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(this.mID);
    dest.writeString(this.mTitle);
    dest.writeString(this.mSubTitle);
    dest.writeString(this.mDescription);
    dest.writeString(this.mAuthor);
    dest.writeLong(this.mISBN);
    dest.writeInt(this.mYear);
    dest.writeInt(this.mPage);
    dest.writeString(this.mPublisher);
    dest.writeString(this.mImageURL);
    dest.writeString(this.mContentLink);
    dest.writeValue(this.mImage);
    dest.writeValue(this.mOriginalImage);
  }

  public static final Creator<BookDetail> CREATOR = new Creator<BookDetail>() {
    @Override
    public BookDetail createFromParcel(Parcel source) {
      return new BookDetail(source);
    }

    @Override
    public BookDetail[] newArray(int size) {
      return new BookDetail[size];
    }
  };
}
