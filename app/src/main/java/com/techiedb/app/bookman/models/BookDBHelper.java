package com.techiedb.app.bookman.models;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.techiedb.app.bookman.Properties;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved.
 * Mobile UX Promotion Division.
 * This software and its documentation are confidential and proprietary
 * information of Techie Digital Benchwork Inc.  No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Techie Digital Benchwork.
 * Techie Digital Benchwork makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 * History
 *
 * @author Larry Pham
 * @since 2014.10.02
 */
public class BookDBHelper extends SQLiteOpenHelper {

    private static final String TAG = Properties.PREFIX + BookDBHelper.class.getSimpleName();
    private static final String DB_VERSION = "Version";
    private static final String DB_NAME = "BookDB.db";
    private static final String BOOK_TABLE = "Books";
    private static final String CATEGORY_TABLE = "Categories";
    private static final String PUBLISHERS_TABLE = "Publishers";
    private static final String AUTHORIES_TABLE = "Authories";

    public class BookColumns{
        public static final String Book_ID = "BookID";
        public static final String PUBLISHER_ID = "PublisherID";
        public static final String AUTHORITIES_ID="AuthoritiesID";
        public static final String IBSN = "IBSN";
        public static final String TITLE = "Title";
        public static final String SUBTITLE = "SubTitle";
        public static final String PAGE = "Page";
        public static final String YEAR = "Year";
        public static final String DOWNLOAD_URL = "Downloads";
        public static final String IMAGE_URL = "Image";
        public static final String DESCRIPTION = "Description";
    }

    public class AuthorColumns{
        public static final String AUTHOR_ID ="AuthorID";
        public static final String AUTHOR_NAME ="AuthorName";
        public static final String AUTHOR_DESCRIPTION = "AuthorDescription";
    }

    public class PublisherColumns{
        public static final String PUBLISHER_ID = "PublisherID";
        public static final String PUBLISHER_NAME = "PublisherName";
        public static final String PUBLISHER_DESCRIPTION = "PublisherDescription";
    }

    public class PropertiesColumns{
        public static final String PROPERTIES_NAME = "Properties";
        public static final String PROPERTIES_DESCRIPTION ="PropertiesDescription";
    }

    public class TrackColumns{
        public static final String TRACK_NAME="Name";
        public static final String TRACK_COUNT ="Count";
    }

    public BookDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BookDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
