package com.techiedb.app.bookman.activities;

import com.techiedb.app.bookman.utils.LogUtils;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since May.06.2015
 */
public class HistoryActivity extends BaseActivity {
  public static final String TAG = LogUtils.makeLogTag(HistoryActivity.class);

  @Override
  protected int getSelfNavDrawerItem() {
    return NAVDRAWER_ITEM_BOOK_RECENTS;
  }

  @Override
  public void invalidate() {

  }

  @Override
  public void invalidate(Object param) {

  }
}
