package com.techiedb.app.bookman.activities;

import com.techiedb.app.bookman.Properties;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @project BookMan
 * @since Dec.19.2014
 */
public class SettingsActivity extends BaseActivity implements PreferenceChangeListener {

  protected static final String TAG = Properties.PREFIX + SettingsActivity.class.getSimpleName();

  @Override
  protected int getSelfNavDrawerItem() {
    return NAVDRAWER_ITEM_BOOK_SETTINGS;
  }

  @Override
  public void invalidate() {

  }

  @Override
  public void invalidate(Object param) {

  }

  @Override
  public void preferenceChange(PreferenceChangeEvent pce) {

  }
}
