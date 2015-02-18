package com.techiedb.app.bookman.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

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
public class RecycleUtil {

  private RecycleUtil() {

  }

  /**
   * Method recursiveRecycle() used to delete the view's child or removing the background
   * attributes, if it was currently instance of AdapterView class, method will remove its child
   * components.
   *
   * @param root The RootView
   */
  public static void recursiveRecycle(View root) {
    if (root == null) {
      return;
    }

    root.setBackgroundDrawable(null);
    if (root instanceof ViewGroup) {
      ViewGroup group = (ViewGroup) root;
      int count = group.getChildCount();
      for (int i = 0; i < count; i++) {
        recursiveRecycle(group.getChildAt(i));
      }

      if (!(root instanceof AdapterView)) {
        group.removeAllViews();
      }
    }
    if (root instanceof ImageView) {
      ((ImageView) root).setImageDrawable(null);
    }

    root = null;
    return;
  }
}
