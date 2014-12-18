package com.techiedb.app.bookman.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
public class BookService extends Service {
    private static final String TAG = Properties.PREFIX + BookService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
