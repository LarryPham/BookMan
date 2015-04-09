package com.techiedb.app.bookman;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.techiedb.app.bookman.models.BookDBHelper;

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
 * BookMan         Revision: 20150322
 */
public class BooksProvider extends ContentProvider {

  public static final String TAG = Properties.PREFIX + BooksProvider.class.getSimpleName();
  static final int BOOK_CASE = 1;
  static final int BOOKS_CASE = 2;
  static final String PATH = "BookTable";
  static final UriMatcher mMatcher;
  static {
    mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    mMatcher.addURI("com.techiedb.app.bookman", "BookTable", BOOK_CASE);
    mMatcher.addURI("com.techiedb.app.bookman", "BooksTable", BOOKS_CASE);
  }
  private static final String TABLE_BOOK = "BookTable";
  private static final String TABLE_BOOKS = "BooksTable";
  private static Uri BOOK_APP_URI = Uri.parse("content://com.techiedb.app.bookman/BookTable");
  private SQLiteDatabase mDB;

  @Override
  public boolean onCreate() {
    BookDBHelper dbHelper = new BookDBHelper(getContext());
    mDB = dbHelper.getWritableDatabase();
    return true;
  }

  @Override
  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                      String sortOrder) {
    Log.d(TAG, String.format("Querying the book from table: %s", uri));
    SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
    int code = mMatcher.match(uri);
    switch (code) {
      case BOOK_CASE:
        builder.setTables(TABLE_BOOK);
        Log.v(TAG, String.format("query book"));
        break;
      case BOOKS_CASE:
        builder.setTables(TABLE_BOOKS);
        Log.v(TAG, String.format("query books"));
        break;
      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }

    Cursor cursor = builder.query(mDB, projection, selection, selectionArgs, null, null, null);
    cursor.setNotificationUri(getContext().getContentResolver(), uri);
    return cursor;
  }

  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Override
  public Uri insert(Uri uri, ContentValues values) {
    return null;
  }

  @Override
  public int delete(Uri uri, String selection, String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    return 0;
  }
}
