package com.techiedb.app.bookman.services;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.techiedb.app.bookman.Properties;

import java.util.HashMap;


/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @since 2014.10.08
 */
public class SerializedTaskManager {

  static final String TAG = Properties.PREFIX + SerializedTaskManager.class.getSimpleName();
  private final static int STM_SERVER_REQUEST = 100;
  private final static int STM_PARSE_FEED = 101;
  private final static int STM_AUTO_UPDATE_FEED = 102;
  private final static int STM_IMAGE_DOWNLOAD_REQUEST = 103;
  static int INNER_MESSAGE_JOB_DO = 1;
  static HashMap<String, SerializedTaskManager>
      hashMap =
      new HashMap<String, SerializedTaskManager>();
  public JobThread jobThread = null;
  public Thread currentJob = null;
  public Object objCurrentJob = null;
  public int requestMsg = 0;

  private SerializedTaskManager() {
    jobThread = new JobThread();
    int priority = jobThread.getPriority();
    if (priority - 1 >= Thread.MIN_PRIORITY) {
      jobThread.setPriority(priority - 1);
    }
    jobThread.start();
    while (jobThread.getJobHandler() == null) {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        Log.e(TAG, String.format("SerialTaskManager(): Exception: %s", e.toString()));
        e.printStackTrace();
      }
    }
  }

  public static SerializedTaskManager resolve(String key) {
    SerializedTaskManager manager = hashMap.get(key);
    if (manager == null) {
      manager = new SerializedTaskManager();
      hashMap.put(key, manager);
    }
    return manager;
  }

  /**
   * Method finalizing the Job-Thread.
   */
  protected void finalize() {
    Log.d(TAG, "finalize() + ");
    if (jobThread != null) {
      jobThread.stop();
    }
  }

  public void add(Thread newTask) {
    Message msg = Message.obtain();
    this.requestMsg = INNER_MESSAGE_JOB_DO;
    msg.what = INNER_MESSAGE_JOB_DO;
    msg.obj = newTask;
    jobThread.getJobHandler().sendMessage(msg);
    Log.d(TAG, String.format("new job added. %s", newTask.getClass().getSimpleName()));
  }

  public void add(Object newTask, int requestMsg) {
    this.requestMsg = requestMsg;
    Message msg = Message.obtain();
    msg.what = requestMsg;
    msg.obj = newTask;
    jobThread.setRequestMsg(requestMsg);
    jobThread.getJobHandler().sendMessage(msg);
    Log.d(TAG, String.format("new job added. %s", newTask.getClass().getSimpleName()));
  }

  public void cancel() {
    String fn = "cancel(): ";
    if (jobThread == null) {
      Log.d(TAG, fn + String.format("Error: jobThread is null."));
      return;
    }
    jobThread.getJobHandler().removeMessages(this.requestMsg);
    Log.d(TAG, fn + String.format("cancel jobThread %s", jobThread.getClass().getSimpleName()));
    jobThread.interrupt();
    if (objCurrentJob != null) {
      Log.d(TAG, fn + String.format("interrupt Json"));
    }
  }

  class JobThread extends Thread {

    public static final String TAG = Properties.PREFIX + "JobThread";
    public Handler jobHandler = null;
    public int requestMsg = 0;

    public JobThread() {

    }

    public Handler getJobHandler() {
      return jobHandler;
    }

    public void setRequestMsg(int msg) {
      this.requestMsg = msg;
    }

    public void run() {
      /**
       * You have to prepare the looper before creating the handler.
       */
      Looper.prepare();
      Log.d(TAG, String.format("Thread %s start. %d", this.getName(), requestMsg));
      jobHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
          switch (msg.what) {
            case STM_PARSE_FEED:
            case STM_AUTO_UPDATE_FEED: {

            }
          }
        }
      };
    }
  }
}
