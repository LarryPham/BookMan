package com.techiedb.app.bookman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
 * @project BookMan
 * @since Dec.18.2014
 */
public class UnseperatedListAdapter extends BaseAdapter {

  public static final String TAG = Properties.PREFIX + UnseperatedListAdapter.class.getSimpleName();

  protected Context mContext = null;
  protected LayoutInflater mLayoutInflater = null;
  private List<List<Object>> mContentListVector = null;
  private List<String> mCategoryVector = null;

  private List<String> getCategoryVector() {
    return mCategoryVector;
  }

  public UnseperatedListAdapter(Context context) {
    super();
    this.mContext = context;
    this.mLayoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    mContentListVector = new ArrayList<List<Object>>();
    mCategoryVector = new ArrayList<String>();
  }

  public List<List<Object>> getContentListVector() {
    return this.mContentListVector;
  }

  @Override
  public int getCount() {
    int count = 0;
    if (mContentListVector == null || mContentListVector.size() == 0) {
      return 0;
    }

    for (int i = 0; i < mContentListVector.size(); i++) {
      List<Object> contentlist = (List<Object>) mContentListVector.get(i);
      count += contentlist.size();
    }

    return count;
  }

  @Override
  public boolean isEnabled(int position) {
    return true;
  }

  @Override
  public Object getItem(int position) {
    int count = 0;
    if ((mContentListVector == null) || (mContentListVector.size() == 0)) {
      return null;
    }

    for (int i = 0; i < mContentListVector.size(); i++) {
      List<Object> contentList = (List<Object>) mContentListVector.get(i);
      if (position < count + contentList.size()) {
        return contentList.get(position - count);
      }
      count += contentList.size();
    }
    return null;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    return null;
  }
}
