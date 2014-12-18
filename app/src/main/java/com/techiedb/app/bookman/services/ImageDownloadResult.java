package com.techiedb.app.bookman.services;

import android.graphics.Bitmap;

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
 * @since 2014.10.08
 *
 * The ImageDownloadResult class which will be adaptable for DownloadImage from server to BookApp
 */
public class ImageDownloadResult {
    private int mEntryId;
    private Bitmap mBitmap;
    
    public ImageDownloadResult(int entryId, Bitmap bitmap){
        this.mEntryId = entryId;
        this.mBitmap = bitmap;
    }

    public int getEntryId() {
        return mEntryId;
    }

    public void setEntryId(int mEntryId) {
        this.mEntryId = mEntryId;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }
}
