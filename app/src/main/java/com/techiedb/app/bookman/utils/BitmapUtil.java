package com.techiedb.app.bookman.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

import com.techiedb.app.bookman.Properties;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @since 2014.10.03
 */
public class BitmapUtil {

  private static final String TAG = Properties.PREFIX + "BitmapUtil";

  public static Size getBitmapSize(String fileName) {
    try {
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      BitmapFactory.decodeFile(fileName, options);
      return new Size(options.outWidth, options.outHeight);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static Size getBitmapSize(byte[] inBitmap) {
    try {
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      BitmapFactory.decodeByteArray(inBitmap, 0, inBitmap.length, options);
      return new Size(options.outWidth, options.outHeight);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Method calculateSampleSize() used to determine the size of output's image which the optional size was been defined.
   *
   * @param inStream     The InputStream for loading content from server or other stream
   * @param optionalSize The Optional Size.
   * @return Integer Type. The ratios for bitmap with specified width and height.
   */
  public static int calculateSampleSize(InputStream inStream, Size optionalSize) {
    try {
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      BitmapFactory.decodeStream(inStream, null, options);

      if (options.outWidth / 16 > optionalSize.width) {
        return 16;
      } else if (options.outWidth / 8 > optionalSize.width) {
        return 8;
      } else if (options.outWidth / 4 > optionalSize.width) {
        return 4;
      } else if (options.outWidth / 2 > optionalSize.width) {
        return 2;
      } else {
        return 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    }
  }

  /**
   * Method calculateSampleSize() used to determine the size of output's image which the optional size was been defined.
   *
   * @param buffer       The Image's Buffer array
   * @param optionalSize The Optional Size.
   * @return Integer Type. The ratios for bitmap with specified width and height.
   */
  public static int calculateSampleSize(byte[] buffer, Size optionalSize) {
    try {
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      BitmapFactory.decodeByteArray(buffer, 0, buffer.length, options);

      if (options.outWidth / 16 > optionalSize.width) {
        return 16;
      } else if (options.outWidth / 8 > optionalSize.width) {
        return 8;
      } else if (options.outWidth / 4 > optionalSize.width) {
        return 4;
      } else if (options.outWidth / 4 > optionalSize.width) {
        return 4;
      } else if (options.outWidth / 2 > optionalSize.width) {
        return 2;
      } else {
        return 1;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return 1;
    }
  }

  /**
   * Method setBackgroundImage
   */
  public static void setBackgroundImage(Context context, View view, int resId) {
    InputStream inStream = context.getApplicationContext().getResources().openRawResource(resId);
    BitmapDrawable drawable = new BitmapDrawable(inStream);
    view.setBackgroundDrawable(drawable);
  }

  public static class Size {

    private int width;
    private int height;

    public Size(int width, int height) {
      this.width = width;
      this.height = height;
    }

    public int getWidth() {
      return width;
    }

    public void setWidth(int width) {
      this.width = width;
    }

    public int getHeight() {
      return height;
    }

    public void setHeight(int height) {
      this.height = height;
    }
  }

  public static class Pool {

    private static HashMap<String, Pool> mBitmapPoolMap = new HashMap<String, Pool>();
    private int MAX_ITEM_COUNT = 1000;
    private int RECYCLE_ITEM_COUNT = 100;
    private List<BitmapItem> listBitmapItems = new ArrayList<BitmapItem>();

    public Pool() {

    }

    public Pool(int maxItemCount, int recyleItemCount) {
      MAX_ITEM_COUNT = maxItemCount;
      RECYCLE_ITEM_COUNT = recyleItemCount;
    }

    public static Pool resolve(String key) {
      if (mBitmapPoolMap.containsKey(key)) {
        return mBitmapPoolMap.get(key);
      } else {
        Pool pool = new Pool();
        mBitmapPoolMap.put(key, pool);
        return pool;
      }
    }

    public Bitmap getBitmap(String key) {
      BitmapItem item = null;
      for (Iterator<BitmapItem> iter = listBitmapItems.iterator(); iter.hasNext(); ) {
        item = iter.next();
        if (item.key.equals(key)) {
          if (listBitmapItems.remove(item)) {
            listBitmapItems.add(item);
            Log.d(TAG, String.format("Bitmap(ID:%s) move first position, total(%d)",
                                     item.key, listBitmapItems.size()));
          }
          return item.bitmap;
        }
      }
      return null;
    }

    /**
     * Method putBitmap used to put the BitmapItem to the Pool
     *
     * @param key    the Specified Item's key.
     * @param bitmap the Specified Item's content.
     */
    public void putBitmap(String key, Bitmap bitmap) {
      BitmapItem item = null;
      for (Iterator<BitmapItem> iter = listBitmapItems.iterator(); iter.hasNext(); ) {
        item = iter.next();
        if (item.key.equals(key)) {
          return;
        }
      }
      item = new BitmapItem(key, bitmap);
      if (listBitmapItems.size() >= MAX_ITEM_COUNT) {
        for (int i = 0; i < RECYCLE_ITEM_COUNT; i++) {
          BitmapItem oldItem = listBitmapItems.get(0);
          oldItem.bitmap.recycle();
          Log.d(TAG, String.format("Bitmap(ID:%s) recycled", oldItem.key));
        }
      }
    }

    public void clear() {
      for (Iterator<BitmapItem> iter = listBitmapItems.iterator(); iter.hasNext(); ) {
        iter.next().bitmap.recycle();
      }

      listBitmapItems.clear();
      Log.d(TAG, String.format("All Bitmap recycled"));
    }

    private class BitmapItem {

      public String key;
      public Bitmap bitmap;

      public BitmapItem(String key, Bitmap bitmap) {
        this.key = key;
        this.bitmap = bitmap;
      }
    }
  }

}
