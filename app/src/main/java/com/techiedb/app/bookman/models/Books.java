package com.techiedb.app.bookman.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.techiedb.app.bookman.Properties;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
public class Books implements Parcelable {
  public static final String TAG = Properties.PREFIX + Books.class.getSimpleName();
  private int mTotal;
  private int mPage;
  private ArrayList<Book> mBookList = new ArrayList<Book>();


  public Books(Parcel source) {
    mBookList = new ArrayList<Book>();
    readFromParcel(source);
  }

  public int getTotal() {
    return mTotal;
  }

  public void setTotal(int total) {
    mTotal = total;
  }

  public int getPage() {
    return mPage;
  }

  public void setPage(int page) {
    mPage = page;
  }

  public ArrayList<Book> getBookList() {
    return mBookList;
  }

  public void setBookList(ArrayList<Book> bookList) {
    mBookList = bookList;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(mTotal);
    dest.writeInt(mPage);
    dest.writeTypedList(mBookList);
  }

  public void readFromParcel(Parcel inSource) {
    mTotal = inSource.readInt();
    mPage = inSource.readInt();
    inSource.readTypedList(mBookList, Book.CREATOR);
  }

  public static final Creator<Books> CREATOR = new Creator<Books>() {
    @Override
    public Books createFromParcel(Parcel source) {
      return new Books(source);
    }

    @Override
    public Books[] newArray(int size) {
      return new Books[size];
    }
  };
}
