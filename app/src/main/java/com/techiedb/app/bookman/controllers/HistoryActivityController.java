package com.techiedb.app.bookman.controllers;

import android.os.Message;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.activities.BaseActivity;
import com.techiedb.app.bookman.models.BookDataModel;

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
 * @project BookMan
 * @since Dec.19.2014
 */
public class HistoryActivityController extends BaseController{
    public static final String TAG = Properties.PREFIX + HistoryActivityController.class.getSimpleName();

    public HistoryActivityController(BaseActivity activity, BookDataModel inDataModel) {
        super(activity, inDataModel);
    }

    public HistoryActivityController(BaseActivity activity, BookDataModel inDataModel, boolean blnFirstRunning) {
        super(activity, inDataModel, blnFirstRunning);
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
