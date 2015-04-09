package com.techiedb.app.bookman;

import android.content.Context;
import android.os.FileObserver;
import android.util.Log;

import com.techiedb.app.bookman.models.Book;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Copyright (C) 2015 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @since 2015.Feb.22
 *
 * BookMan         Revision:
 */
public class DownloadFileObserver extends FileObserver {

  public static final String TAG = Properties.PREFIX + DownloadFileObserver.class.getSimpleName();
  public static int CHANGES_ONLY = CREATE | DELETE | CLOSE_WRITE | MOVE_SELF
                                   | MOVED_FROM | MOVED_TO;

  private List<SingleFileObserver> mObservers;
  private String mPath;
  private int mMask;
  private BookApp mApp = null;
  private Book mBook = null;
  private Context mContext = null;
  private boolean mChangeCancelPath = false;

  public DownloadFileObserver(Context context, String path) {
    this(context, path, ALL_EVENTS);
  }

  public DownloadFileObserver(Context context, String path, int mask) {
    super(path, mask);
    this.mPath = path;
    this.mMask = mask;
    this.mContext = context;
    mApp = (BookApp) context.getApplicationContext();
  }

  @Override
  public void startWatching() {
    if (mObservers != null) {
      return;
    }
    mObservers = new ArrayList<SingleFileObserver>();
    Stack<String> stack = new Stack<String>();
    stack.push(mPath);
    while (!stack.isEmpty()) {
      String parent = String.valueOf(stack.pop());
      mObservers.add(new SingleFileObserver(parent, mMask));
      File path = new File(parent);
      File[] files = path.listFiles();

      if (null == files) {
        continue;
      }

      for (File file : files) {
        if (file.isDirectory() && !file.getName().equals(".") && !file.getName().equals(".")) {
          stack.push(file.getPath());
        }
      }

      for (SingleFileObserver sfo : mObservers) {
        sfo.startWatching();
      }
    }
  }

  @Override
  public void stopWatching() {
    if (mObservers == null) {
      return;
    }
    mObservers.clear();
    mObservers = null;
  }

  public void close() {
    super.finalize();
  }

  @Override
  public void onEvent(int event, String path) {
    int index = path.lastIndexOf(".");
    String ext = path.substring(index + 1, path.length());
    switch (event) {
      case FileObserver.ACCESS:
        Log.i(TAG, "ACCESS: " + path);
        break;
      case FileObserver.ATTRIB:
        Log.i(TAG, "CLOSE_NOWRITE: " + path);
        break;
      case FileObserver.CLOSE_WRITE:
        Log.i(TAG, "CLOSE_WRITE: " + path);
        break;
      case FileObserver.CREATE:
        Log.i(TAG, "CREATE: " + path);
        break;
      case FileObserver.DELETE:
        Log.i(TAG, "DELETE: " + path);
        if (!ext.toLowerCase().equals("new") && !ext.toLowerCase().equals("old")) {

        }
    }
  }

  class SingleFileObserver extends FileObserver {

    String mPath;

    public SingleFileObserver(String path) {
      this(path, ALL_EVENTS);
      mPath = path;
    }

    public SingleFileObserver(String path, int mask) {
      super(path, mask);
      mPath = path;
    }

    @Override
    public void onEvent(int event, String path) {
      String newPath = mPath + "/" + path;
      DownloadFileObserver.this.onEvent(event, newPath);
    }
  }
}
