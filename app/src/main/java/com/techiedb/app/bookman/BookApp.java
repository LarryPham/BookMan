package com.techiedb.app.bookman;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.techiedb.app.bookman.activities.ActivityHandler;
import com.techiedb.app.bookman.controllers.ControllerMessage;
import com.techiedb.app.bookman.models.BookDBHelper;
import com.techiedb.app.bookman.models.BookDataModel;
import com.techiedb.app.bookman.services.BookService;
import com.techiedb.app.bookman.utils.BitmapUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CacheRequest;
import java.net.CacheResponse;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class BookApp extends Application {

  private static final String TAG = Properties.PREFIX + BookApp.class.getSimpleName();
  private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      Log.d(TAG, String.format("OnReceive: Action[%s]", action));
      if (action.equals(Intent.ACTION_CONFIGURATION_CHANGED)) {
        Configuration config = getResources().getConfiguration();
        if (config != null) {
          Log.d(TAG, String.format("Changed Language - [%s][%s]", config.locale.getLanguage(),
                                   config.locale.getCountry()));
        }
      }
    }
  };
  private BookDataModel mDataModel;
  private BookDBHelper mDBHelper = new BookDBHelper(this);
  private BookService mService;
  private SharedPreferences mPrefs;
  private Context mContext;
  private List<ActivityHandler> mHandlerList = new ArrayList<ActivityHandler>();
  private AppMainHandler mainHandler = new AppMainHandler();

  @Override
  public void onCreate() {
    super.onCreate();
    initCache();
    mContext = getApplicationContext();
    this.registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_LOW));
    this.registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));
  }

  public void addHandlerIfNotExist(ActivityHandler handler) {
    boolean blnExist = false;
    try {
      int size = mHandlerList.size();
      for (int i = 0; i < size; i++) {
        if (mHandlerList.get(i).hashCode() == handler.hashCode()) {
          blnExist = true;
          break;
        }
      }
      if (!blnExist) {
        Log.e(TAG, String.format("addHandlerIfNotExist"));
        addHandler(handler);
      }
    } catch (Exception e) {
      Log.e(TAG, String.format("addHandlerIfNotExist()" + "Exception: " + e.getMessage()));
      e.printStackTrace();
    }
  }

  public void addHandler(ActivityHandler handler) {
    Log.d(TAG, String.format("addHandler: %s", handler.toString()));
    mHandlerList.add(handler);
  }

  public void removeHandler(ActivityHandler handler) {
    Log.d(TAG, String.format("removing the handler(%s)", handler.toString()));
    mHandlerList.remove(handler);
    Log.d(TAG, String.format("count of remained handlers: %d", mHandlerList.size()));
  }

  public void removeAllHandler() {
    Log.d(TAG, String.format("remove all of handlers(%d)", mHandlerList.size()));
    mHandlerList.clear();
  }

  public Handler getAppMainHandler() {
    return this.mainHandler;
  }

  public BookDBHelper getDBHelper() {
    return this.mDBHelper;
  }

  private void initCache() {
    final File cacheDir = this.getCacheDir();
    java.net.ResponseCache.setDefault(new ResponseCache() {

      private String escape(String url) {
        return url.replace("/", "-").replace(".", "-");
      }

      @Override
      public CacheResponse get(URI uri, String s, Map<String, List<String>> stringListMap)
          throws IOException {
        if (uri == null) {
          Log.d(TAG, String.format("get(): Error: uri is null."));
          return null;
        }
        final File file = new File(cacheDir, escape(uri.getPath()));
        if (file.exists()) {
          return new CacheResponse() {
            @Override
            public InputStream getBody() throws IOException {
              return new FileInputStream(file);
            }

            @Override
            public Map<String, List<String>> getHeaders() throws IOException {
              return null;
            }
          };
        } else {
          return null;
        }
      }

      @Override
      public CacheRequest put(URI uri, URLConnection urlConnection) throws IOException {
        final File file = new File(cacheDir, escape(urlConnection.getURL().getPath()));
        return new CacheRequest() {

          @Override
          public void abort() {
            file.delete();
          }

          @Override
          public OutputStream getBody() throws IOException {
            return new FileOutputStream(file);
          }
        };
      }
    });
  }

  @Override
  public void onTrimMemory(int level) {
    super.onTrimMemory(level);
    boolean evicBitmaps = false;
    switch (level) {
      case TRIM_MEMORY_COMPLETE:
      case TRIM_MEMORY_MODERATE:
      case TRIM_MEMORY_RUNNING_LOW:
      case TRIM_MEMORY_RUNNING_CRITICAL:
        evicBitmaps = true;
        break;
      default:
        break;
    }
    if (evicBitmaps && BitmapUtil.Pool.resolve("AppImageCache") != null) {
      BitmapUtil.Pool.resolve("AppImageCache").clear();
    }
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    Log.d(TAG, String
        .format("Running the application into the low-memory mode: %s", BookApp.this.toString()));
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    Log.d(TAG, String.format("Terminating the application: %s", BookApp.this.toString()));
    this.unregisterReceiver(mReceiver);
  }


  class AppMainHandler extends Handler {

    public void handleMessage(Message msg) {
      String fn = "handleMessage(): ";
      Log.d(TAG, fn + "[AppMainHandler]" + msg.toString());
      int index = 0;
      int size = mHandlerList.size();
      Log.d(TAG, fn + "[AppMainHandler] handlerList size: " + size);
      switch (msg.what) {
        case ControllerMessage.REQUEST_EBOOK_IMAGE_FROM_SERVER_COMPLETED: {
          Log.i(TAG, "REQUEST_EBOOK_IMAGE_FROM_SERVER_COMPLETED");
          if (msg.arg1 == Properties.HASH_CODE_FOR_IMAGE) {
            Log.i(TAG, "REQUEST_EBOOK_IMAGE_FROM_SERVER_COMPLETED");

          }
        }
        // Adding blocks for handle the feedback completed messages.
      }

      for (int i = 0; i < size; i++) {
        Log.d(TAG, fn + "Message Id: " + msg.what + " handler of: " + mHandlerList.get(i).getClass()
            .getSimpleName());
        ActivityHandler activityHandler = mHandlerList.get(i);
        Log.d(TAG, fn + " handlerList size: " + mHandlerList.size());
        activityHandler.callback(msg);
      }
    }
  }
}
