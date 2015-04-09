package com.techiedb.app.bookman.services.tasks;

import java.lang.ref.WeakReference;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @since Apr.02.2015
 *
 * Using the WeakReferenceRunnable for restricting the reference for each of the list of <code>Runnable</code> when we used it to implement
 * the customized threads.
 */
public abstract class WeakReferenceRunnable<T> implements Runnable {

  private final WeakReference<T> mObjectRef;

  public WeakReferenceRunnable(T object) {
    mObjectRef = new WeakReference<T>(object);
  }

  @Override
  public final void run() {
    T object = mObjectRef.get();
    if (object != null) {
      run(object);
    }
  }

  public abstract void run(T object);
}
