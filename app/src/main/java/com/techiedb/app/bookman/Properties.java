package com.techiedb.app.bookman;

import java.io.File;

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
public class Properties {

    public static final String PREFIX = "[BookMan]";
    public static final String BASE_SERVER_URL = "http://it-ebooks-api.info/v1/";
    public static final int HASH_CODE_FOR_IMAGE = 111;
    public static final String URL = "http://it-ebooks-api.info/v1/";
    public static final String PACKAGE_NAME = "com.techiedb.app.bookman";
    public static final String PREFS_PATH = "/data/com.techiedb.app.bookman/shared_prefs";
    public static final String IMAGE_CACHE_FOLDER = "cache";
    public static final int MAX_SIZE_INIT_VALUE = -1;
    public static final int PAGE_SIZE_INIT_VALUE = -1;
    public static final int PAGE_INDEX_INIT_VALUE = -1;
    public static final int ID_INIT_VALUE = -1;
    public static final int RUN_MODE_TEST = 1;
    public static final int RUN_MODE_RETAIL = 2;
    public static final int RUN_MODE = RUN_MODE_RETAIL;
    public static final int MIN_SEARCH_KEYWORD_LENGTH = 3;
    public static final int CONN_DISCONNECTED = 0;
    public static final int CONN_WIFI = 1;
    public static final int CONN_3G = 2;
    public static final int CONNECTION_TIMEOUT = 3000;
    public static final int CONNECTION_READ_TIMEOUT = 5000;
    public static String INTERNAL_MEMORY_PATH = "/mnt/sdcard";
    public static String DEFAULT_CONTENT_PATH = INTERNAL_MEMORY_PATH + "/bookman";
    public static String INTERNAL_MEMORY_INUSE = DEFAULT_CONTENT_PATH + "/" + ".inuse";
    public static String MARKET_CACHE_IMAGE_FOLDER = DEFAULT_CONTENT_PATH + File.separator + IMAGE_CACHE_FOLDER + File.separator;
    public static String EXTERNAL_MEMORY_PATH = "/mnt/sdcard/external_sd";
    public static String EXTERNAL_CONTENT_PATH = EXTERNAL_MEMORY_PATH + "/bookman";
    public static String EXTERNAL_MEMORY_INUSE = EXTERNAL_CONTENT_PATH + "/" + ".inuse";

    public static void setInternalPath(String path) {
        INTERNAL_MEMORY_PATH = path;
        DEFAULT_CONTENT_PATH = INTERNAL_MEMORY_PATH + "/bookman";
        MARKET_CACHE_IMAGE_FOLDER = DEFAULT_CONTENT_PATH + File.separator + IMAGE_CACHE_FOLDER + File.separator;
        INTERNAL_MEMORY_INUSE = DEFAULT_CONTENT_PATH + "/" + ".inuse";
    }

    public static void setExternalPath(String path) {
        EXTERNAL_MEMORY_PATH = path;
        EXTERNAL_CONTENT_PATH = EXTERNAL_MEMORY_PATH + "/bookman";
        EXTERNAL_MEMORY_INUSE = EXTERNAL_CONTENT_PATH + "/" + ".inuse";
    }

    public static enum StorageValueState {
        NONE,
        ONLY_EXTERNAL_SUPPORT,
        INTERNAL,
        EXTERNAL
    }

    public static enum DialogMsg {
        CANCEL_DOWNLOAD,
        DOWNLOAD_FAIL,
        NOT_FOUND_FILE,
        NOT_CONNECT_WIFI,
        NOT_CONNECT_3G,
        ONLY_CHARGING_DOWN,
        NOT_AVAILABLE_SD_CARD,
        NO_NETWORK_CONNECTION,
        ONLY_WIFI,
        UNSUBSCRIBE_WITH_BOOK
    }

    public static enum EnclosureType {
        NONE,
        PDF
    }


}
