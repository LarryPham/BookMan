package com.techiedb.app.bookman;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
public class MainBookManActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_book_man, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
