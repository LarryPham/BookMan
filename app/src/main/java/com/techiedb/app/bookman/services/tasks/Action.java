package com.techiedb.app.bookman.services.tasks;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @since 2014.10.02
 */
public class Action implements Parcelable {

  public static final String REQUEST_OWNER = "com.techiedb.app.bookman.actions.ACTION_REQUEST_OWNER";
  public static final String REQUEST_MSG = "com.techiedb.app.bookman.actions.ACTION_REQUEST_MSG";

  public static final String LOAD_GALLERY_PAGE = "com.techiedb.app.bookman.actions.ACTION_LOAD_GALLERY_PAGE";
  public static final String LOAD_CONTENT_PARTIAL = "com.techiedb.app.bookman.actions.ACTION_LOAD_CONTENT_PARTIAL";
  public static final String LOAD_CONTENT_ALL = "com.techiedb.app.bookman.actions.ACTION_LOAD_CONTENT_ALL";
  public static final String LOADING_IMAGE = "com.techiedb.app.bookman.actions.ACTION_LOADING_IMAGE";
  public static final String LOADING_SIZE = "com.techiedb.app.bookman.actions.ACTION_LOADING_SIZE";

  public static final String CANCEL_DOWNLOAD_CONTENT = "com.techiedb.app.bookman.actions.ACTION_CANCEL_DOWNLOAD_CONTENT";
  public static final String CANCEL_ALL_DOWNLOAD_CONTENT = "com.techiedb.app.bookman.actions.ACTION_CANCEL_ALL_DOWNLOAD_CONTENT";

  public static final String ACTION_EXIT = "com.techiedb.app.bookman.actions.ACTION_EXIT";
  public static final String RESUME_LOADING = "com.techiedb.app.bookman.actions.RESUME_LOADING";

  public static final String FILE = "com.techiedb.app.bookman.actions.ACTION_REQUEST_FILE";
  public static final String URL = "com.techiedb.app.bookman.actions.ACTION_REQUEST_URL";

  public static final String FROM_URL = "com.techiedb.app.bookman.actions.ACTION_REQUEST_FROM_URL";
  public static final String TO_URL = "com.techiedb.app.bookman.actions.ACTION_REQUEST_TO_URL";

  public static final String BOOK_ID = "com.techiedb.app.bookman.actions.ACTION_BINDING_BOOK_ID";
  public static final String PUBLISHER_ID = "com.techiedb.app.bookman.actions.ACTION_BINDING_PUBLISHER_ID";
  public static final String AUTHOR_ID = "com.techiedb.app.bookman.actions.ACTION_BINDING_AUTHOR_ID";
  public static final String IS_NOTICE = "com.techiedb.app.bookman.actions.ACTION_REQUEST_NOTICE_FROM_SERVER";

  public static final String DISMISS_DIALOG = "com.techiedb.app.bookman.actions.ACTION_DISMISS_DIALOG";
  public static final String SHOW_DIALOG = "com.techiedb.app.bookman.actions.ACTION_SHOW_DIALOG";
  public static final String SHOW_PROGRESS_DURATION = "com.techiedb.app.bookman.actions.ACTION_SHOW_PROGRESS_DURATION";
  public static final String PULL_REFRESH_TO_REQUEST = "com.techiedb.app.bookman.actions.ACTION_PULL_TO_REFRESH_REQUEST";
  public static final String REQUEST_TO_LOADING_MORE = "com.techiedb.app.bookman.actions.ACTION_REQUEST_TO_LOADING_MORE";
  public static final String CLIENT_DEVICES = "com.techiedb.app.bookman.actions.REQUEST_CLIENT_DEVICES";
  public static final String CLIENT_OS = "com.techiedb.app.bookman.actions.REQUEST_CLIENT_OS";
  public static final String CLIENT_GUID = "com.techiedb.app.bookman.actions.REQUEST_CLIENT_GUID";
  public static final String GUID = "com.techiedb.app.bookman.actions.GENERATE_GUID";

  public static final String REQUEST_TO_SERVER_EBOOKS_LIST = "com.techiedb.app.bookman.actions.REQUEST_TO_SERVER_EBOOKS_LIST";
  public static final String REQUEST_TO_SERVER_EBOOK_INFO = "com.techiedb.app.bookman.actions.REQUEST_TO_SERVER_EBOOK_INFO";

  public static final String REQUEST_TO_SERVER_EBOOKS_AUTHOR_KEYWORD ="com.techiedb.app.bookman.actions.REQUEST_TO_SERVER_EBOOKS_AUTHOR_KEYWORD";
  public static final String REQUEST_TO_SERVER_EBOOKS_PUBLISHER_KEYWORD = "com.techiedb.app.bookman.actions.REQUEST_TO_SERVER_EBOOKS_PUBLISHER_KEYWORD";
  public static final String REQUEST_TO_SERVER_EBOOKS_KEYWORD = "com.techiedb.app.bookman.actions.REQUEST_TO_SERVER_EBOOKS_KEYWORD";

  public static final String TOTAL = "com.techiedb.app.bookman.actions.REQUEST_TO_SERVER_TOTAL";
  public static final String OFFSET = "com.techiedb.app.bookman.actions.REQUEST_TO_SERVER_OFFSET";
  public static final String LIMIT = "com.techiedb.app.bookman.actions.REQUEST_TO_SERVER_LIMIT";
  public static final String SHOW_ERROR_DIALOG = "com.techiedb.app.bookman.actions.SHOW_ERROR_DIALOG";
  public static final String DISMISS_ERROR_DIALOG = "com.techiedb.app.bookman.actions.DISMISS_ERROR_DIALOG";
  public static final String SHOW_RESPONE_TIME = "com.techiedb.app.bookman.actions.SHOW_RESPONSE_TIME";
  public static final String ENTRY_ID = "com.techiedb.app.bookman.actions.ENTRY_ID";
  public static final String DOWNLOAD_CONTENT = "com.techiedb.app.book.actions.DOWNLOAD_CONTENT";
  protected int mId;
  protected String mCode;
  protected Object mObject;

  public Action(String code, int id, Object inTarget) {
    this.mCode = code;
    this.mId = id;
    this.mObject = inTarget;
  }

  public Action(Parcel source) {
    readFromParcel(source);
  }

  public int ID() {
    return this.mId;
  }

  public String Code() {
    return mCode;
  }

  public Object Obj() {
    return mObject;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(REQUEST_OWNER);
    parcel.writeString(REQUEST_MSG);
    parcel.writeString(LOAD_GALLERY_PAGE);
    parcel.writeString(LOAD_CONTENT_ALL);
    parcel.writeString(LOAD_CONTENT_PARTIAL);
    parcel.writeString(LOADING_IMAGE);
    parcel.writeString(LOADING_SIZE);

    parcel.writeString(CANCEL_ALL_DOWNLOAD_CONTENT);
    parcel.writeString(CANCEL_DOWNLOAD_CONTENT);

    parcel.writeString(ACTION_EXIT);
    parcel.writeString(RESUME_LOADING);

    parcel.writeString(FILE);
    parcel.writeString(URL);
    parcel.writeString(FROM_URL);
    parcel.writeString(TO_URL);

    parcel.writeString(BOOK_ID);
    parcel.writeString(AUTHOR_ID);
    parcel.writeString(PUBLISHER_ID);
    parcel.writeString(IS_NOTICE);

    parcel.writeString(DISMISS_DIALOG);
    parcel.writeString(SHOW_DIALOG);
    parcel.writeString(SHOW_PROGRESS_DURATION);
    parcel.writeString(PULL_REFRESH_TO_REQUEST);
    parcel.writeString(REQUEST_TO_LOADING_MORE);

    parcel.writeString(CLIENT_DEVICES);
    parcel.writeString(CLIENT_OS);
    parcel.writeString(CLIENT_GUID);
    parcel.writeString(GUID);

    parcel.writeString(REQUEST_TO_SERVER_EBOOKS_LIST);
    parcel.writeString(REQUEST_TO_SERVER_EBOOK_INFO);
    parcel.writeString(REQUEST_TO_SERVER_EBOOKS_KEYWORD);
    parcel.writeString(REQUEST_TO_SERVER_EBOOKS_AUTHOR_KEYWORD);
    parcel.writeString(REQUEST_TO_SERVER_EBOOKS_PUBLISHER_KEYWORD);

    parcel.writeString(TOTAL);
    parcel.writeString(OFFSET);
    parcel.writeString(LIMIT);
    parcel.writeString(SHOW_ERROR_DIALOG);
    parcel.writeString(DISMISS_ERROR_DIALOG);
    parcel.writeString(SHOW_RESPONE_TIME);
    parcel.writeString(ENTRY_ID);
  }

  public void readFromParcel(Parcel source) {
    this.mId = source.readInt();
    this.mCode = source.readString();
    this.mObject = source.readSerializable();
  }
}
