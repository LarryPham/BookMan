package com.techiedb.app.bookman.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.activeandroid.util.Log;
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
public class BookDBHelper extends SQLiteOpenHelper {

  private static final String TAG = Properties.PREFIX + BookDBHelper.class.getSimpleName();
  private Context mContext;
  private static final int DB_VERSION = 3;
  private static final String DB_NAME = "BookDB.db";
  private static final String BOOK_TABLE = "Books";
  private static final String PUBLISHERS_TABLE = "Publishers";
  private static final String PROPERTIES_TABLE ="Properties";
  private static final String TRACK_TABLE = "Track";
  public class BookColumns {
    public static final String Book_ID = "BookID";
    public static final String PUBLISHER_ID = "PublisherID";
    public static final String AUTHOR = "Author";
    public static final String IBSN = "IBSN";
    public static final String TITLE = "Title";
    public static final String SUBTITLE = "SubTitle";
    public static final String PAGE = "Page";
    public static final String YEAR = "Year";
    public static final String DOWNLOAD_URL = "Downloads";
    public static final String IMAGE_URL = "Image";
    public static final String DESCRIPTION = "Description";
  }

  public class PublisherColumns {
    public static final String PUBLISHER_ID = "PublisherID";
    public static final String PUBLISHER_NAME = "PublisherName";
    public static final String PUBLISHER_DESCRIPTION = "PublisherDescription";
  }

  public class PropertiesColumns {
    public static final String PROPERTIES_ID ="_id";
    public static final String PROPERTIES_NAME = "Properties";
    public static final String PROPERTIES_DESCRIPTION = "PropertiesDescription";
  }

  public class TrackColumns {
    public static final String TRACK_ID ="_id";
    public static final String TRACK_NAME = "Name";
    public static final String TRACK_COUNT = "Count";
  }

  private static final String CREATE_BOOK_TABLE = "CREATE TABLE " + BOOK_TABLE + " ( " +
                                                  BookColumns.Book_ID + " INTEGER PRIMARY KEY, " +
                                                  BookColumns.PUBLISHER_ID + " INTEGER, " +
                                                  BookColumns.AUTHOR + " TEXT, " +
                                                  BookColumns.TITLE + " TEXT, " +
                                                  BookColumns.SUBTITLE + " TEXT, " +
                                                  BookColumns.DESCRIPTION + " TEXT, " +
                                                  BookColumns.IMAGE_URL + " TEXT, " +
                                                  BookColumns.YEAR + " INTEGER, " +
                                                  BookColumns.PAGE + " INTEGER, " +
                                                  BookColumns.IBSN + " LONG, " +
                                                  BookColumns.DOWNLOAD_URL + " TEXT, PRIMARY KEY("+
                                                  BookColumns.Book_ID +"));";
  private static final String CREATE_PUBLISHER_TABLE = "CREATE TABLE " + PUBLISHERS_TABLE + " ( " +
                                                  PublisherColumns.PUBLISHER_ID + " INTEGER PRIMARY KEY, " +
                                                  PublisherColumns.PUBLISHER_NAME + " TEXT, " +
                                                  PublisherColumns.PUBLISHER_DESCRIPTION + " TEXT, PRIMARY KEY( " +
                                                  PublisherColumns.PUBLISHER_ID + "));";
  private static final String CREATE_PROPERTIES_TABLE = "CREATE TABLE " + PROPERTIES_TABLE + " ( " +
                                                  PropertiesColumns.PROPERTIES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                  PropertiesColumns.PROPERTIES_NAME + " TEXT, " +
                                                  PropertiesColumns.PROPERTIES_DESCRIPTION + " TEXT));";
  private static final String CREATE_TRACK_TABLE = "CREATE TABLE " + TRACK_TABLE +" ( " +
                                                  TrackColumns.TRACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                  TrackColumns.TRACK_NAME + " TEXT, " +
                                                  TrackColumns.TRACK_COUNT + " INTEGER);";
  private static final String CREATE_BOOK_TITLE_INDEX = "CREATE INDEX IDX_BOOK_TITLE ON " + BOOK_TABLE +
                                                  "( " + BookColumns.TITLE + ");";
  private static final String CREATE_BOOK_PUBLISHER_INDEX = "CREATE INDEX IDX_BOOK_PUBLISHER ON " + BOOK_TABLE +
                                                  "( " + BookColumns.PUBLISHER_ID + "); ";
  private static final String CREATE_PUBLISHER_INDEX = "CREATE INDEX IDX_PUBLISHER ON " + PUBLISHERS_TABLE + " ( " +
                                                  PublisherColumns.PUBLISHER_NAME + ");";

  public BookDBHelper(Context context) {
    super(context, DB_NAME, null, DB_VERSION);
    this.mContext = context;
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    Log.d(TAG, String.format("BookDBHelper onCreate() "));
    db.execSQL(CREATE_BOOK_TABLE);
    db.execSQL(CREATE_PUBLISHER_TABLE);
    db.execSQL(CREATE_PROPERTIES_TABLE);
    db.execSQL(CREATE_TRACK_TABLE);

    db.execSQL(CREATE_BOOK_TITLE_INDEX);
    db.execSQL(CREATE_BOOK_PUBLISHER_INDEX);
    db.execSQL(CREATE_PUBLISHER_INDEX);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.d(TAG, String.format("Upgrading from version: %d to %d ", oldVersion,newVersion));
    switch (oldVersion) {
      case 2:
        db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE );
        db.execSQL("DROP TABLE IF EXISTS " + PUBLISHERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PROPERTIES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TRACK_TABLE);
        onCreate(db);
        break;
      default:
        break;
    }
  }

  public void clearTables() {
    SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE);
    db.execSQL("DROP TABLE IF EXISTS " + PUBLISHERS_TABLE);
    db.execSQL("DROP TABLE IF EXISTS " + PROPERTIES_TABLE);
    db.execSQL("DROP TABLE IF EXISTS " + TRACK_TABLE);
    onCreate(db);
  }

  public Cursor getBookCursor() {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = null;
    /* Get all records of book table */
    cursor = db.query(BOOK_TABLE, null, null, null, null, null, null);
    cursor.moveToFirst();
    return cursor;
  }

  public int getBookCount() {
    SQLiteDatabase db = this.getWritableDatabase();
    int count = 0;
    Cursor cursor = null;
    cursor = db.rawQuery(String.format("SELECT COUNT(*) FROM %s", BOOK_TABLE), null);
    if (cursor.moveToFirst()) {
      count = cursor.getInt(0);
    }
    return count;
  }


}
