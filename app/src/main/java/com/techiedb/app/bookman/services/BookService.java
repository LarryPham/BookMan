package com.techiedb.app.bookman.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.techiedb.app.bookman.BookApp;
import com.techiedb.app.bookman.Properties;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion
 * Division. This software and its documentation are confidential and proprietary information of
 * Techie Digital Benchwork Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the
 * prior written consent of Techie Digital Benchwork. Techie Digital Benchwork makes no
 * representations with respect to the contents, and assumes no responsibility for any errors that
 * might appear in the software and documents. This publication and the contents hereof are subject
 * to change without notice. History
 *
 * @author Larry Pham
 * @since 2014.10.02
 */
public class BookService extends Service {

  private static final String TAG = Properties.PREFIX + BookService.class.getSimpleName();
  private final static int STM_SERVER_REQUEST = 100;
  private final static int STM_PARSE_FEED = 101;
  private final static int STM_AUTO_UPDATE_FEED = 102;

  private final static int STM_IMAGE_DOWNLOAD_REQUEST = 103;
  private final static int STM_PARSE_SYNC_XML = 104;
  private Handler mMainHandler;
  private BookApp mApp;
  private static final int ONE_HOUR_UPDATE_INTERVAL = 1000 * 60 * 60;
  private static final int SIX_HOURS_UPDATE_INTERVAL = 1000 * 60 * 60 * 6;
  private static final int ONE_DAY_UPDATE_INTERVAL = 1000 * 60 * 60 * 24;
  private static final int THREE_DAYS_UPDATE_INTERVAL = 1000 * 60 * 60 * 24 * 3;


  @Override
  public IBinder onBind(Intent intent) {
    Log.d(TAG, "onBind");
    return mBinder;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    return super.onUnbind(intent);
  }

  private final IBinder mBinder = new LocalBinder();
  ;

  public class LocalBinder extends Binder {

    public BookService getService() {
      return BookService.this;
    }
  }


}
