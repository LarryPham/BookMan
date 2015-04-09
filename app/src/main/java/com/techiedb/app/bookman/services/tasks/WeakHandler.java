package com.techiedb.app.bookman.services.tasks;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
 */
public class WeakHandler {

  private final Handler.Callback mCallback;
  private final ExecHandler mExecHandler;
  private final ChainedRef mRunnableRef = new ChainedRef(null);

  public WeakHandler() {
    mCallback = null;
    mExecHandler = new ExecHandler();
  }

  public WeakHandler(@Nullable Handler.Callback callback) {
    mCallback = callback;
    mExecHandler = new ExecHandler(new WeakReference<Handler.Callback>(callback));
  }

  public WeakHandler(@NonNull Looper looper) {
    mCallback = null;
    mExecHandler = new ExecHandler(looper);
  }

  public WeakHandler(@NonNull Looper looper, @NonNull Handler.Callback callback) {
    mCallback = callback;
    mExecHandler = new ExecHandler(looper, new WeakReference<Handler.Callback>(callback));
  }

  public final boolean post(@NonNull Runnable runnable) {
    return mExecHandler.post(wrapRunnable(runnable));
  }

  public final boolean postAtTime(@NonNull Runnable runnable, long uptimeMillis) {
    return mExecHandler.postAtTime(wrapRunnable(runnable), uptimeMillis);
  }

  public final boolean postAtTime(Runnable runnable, Object token, long uptimeMillis) {
    return mExecHandler.postAtTime(wrapRunnable(runnable), token, uptimeMillis);
  }

  public final boolean postDelayed(Runnable runnable, long delayMillis) {
    return mExecHandler.postDelayed(runnable, delayMillis);
  }

  public final boolean postAtFrontOfQueue(Runnable runnable) {
    return mExecHandler.postAtFrontOfQueue(wrapRunnable(runnable));
  }

  public final void removeCallbacks(Runnable runnable) {
    final ChainedRef runnableRef = mRunnableRef.findForward(runnable);
    if (runnableRef != null) {
      mExecHandler.removeCallbacks(runnableRef.mWrapper);
    }
  }

  public final void removeCallbacks(Runnable runnable, Object token) {
    final ChainedRef runnableRef = mRunnableRef.findForward(runnable);
    if (runnableRef != null) {
      mExecHandler.removeCallbacks(runnableRef.mWrapper, token);
    }
  }

  public final boolean sendMessage(Message msg) {
    return mExecHandler.sendMessage(msg);
  }

  public final boolean sendEmptyMessage(int what) {
    return mExecHandler.sendEmptyMessage(what);
  }

  public final boolean sendEmptyMessageDelayed(int what, long delayMillis) {
    return mExecHandler.sendEmptyMessageDelayed(what, delayMillis);
  }

  public final boolean sendEmptyMessageAtTime(int what, long uptimeMillis) {
    return mExecHandler.sendEmptyMessageAtTime(what, uptimeMillis);
  }

  public final boolean sendMessageDelayed(Message msg, long delayedMillis) {
    return mExecHandler.sendMessageDelayed(msg, delayedMillis);

  }

  public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
    return mExecHandler.sendMessageAtTime(msg, uptimeMillis);
  }

  public final boolean sendMessageAtFrontOfQueue(Message msg) {
    return mExecHandler.sendMessageAtFrontOfQueue(msg);
  }

  public final void removeMessages(int what) {
    mExecHandler.removeMessages(what);
  }

  public final void removeMessages(int what, Object obj) {
    mExecHandler.removeMessages(what, obj);
  }

  public final void removeCallbacksAtMessages(Object token) {
    mExecHandler.removeCallbacksAndMessages(token);
  }

  public final boolean hasMessages(int what) {
    return mExecHandler.hasMessages(what);
  }

  public final boolean hasMessages(int what, Object obj) {
    return mExecHandler.hasMessages(what, obj);
  }

  public final Looper getLooper() {
    return mExecHandler.getLooper();
  }

  private WeakRunnable wrapRunnable(Runnable runnable) {
    final ChainedRef hardRef = ChainedRef.obtain(runnable);
    mRunnableRef.insertAbove(hardRef);
    return hardRef.mWrapper = new WeakRunnable(new WeakReference<Runnable>(runnable),
                                               new WeakReference<ChainedRef>(hardRef));
  }

  public void handleMessage(Message msg) {
    mExecHandler.handleMessage(msg);
  }

  private static class ExecHandler extends Handler {

    private final WeakReference<Callback> mCallbackRef;

    ExecHandler() {
      mCallbackRef = null;
    }

    ExecHandler(WeakReference<Callback> callback) {
      mCallbackRef = callback;
    }

    ExecHandler(Looper looper) {
      super(looper);
      mCallbackRef = null;
    }

    ExecHandler(Looper looper, WeakReference<Callback> callbackRef) {
      super(looper);
      mCallbackRef = callbackRef;
    }

    @Override
    public void handleMessage(Message msg) {
      if (mCallbackRef == null) {
        return;
      }

      final Callback callback = mCallbackRef.get();
      if (callback == null) {
        return;
      }

      callback.handleMessage(msg);
    }


  }

  static class WeakRunnable implements Runnable {

    private final WeakReference<Runnable> mDelegate;
    private final WeakReference<ChainedRef> mReference;

    WeakRunnable(WeakReference<Runnable> delegate, WeakReference<ChainedRef> reference) {
      mDelegate = delegate;
      mReference = reference;
    }

    @Override
    public void run() {
      final Runnable delegate = mDelegate.get();
      final ChainedRef reference = mReference.get();
      if (reference != null) {
        reference.remove();
      }

      if (delegate != null) {
        delegate.run();
      }
    }
  }

  static class ChainedRef {

    static final int MAX_POOL_SIZE = 15;
    @Nullable
    static ChainedRef sPool;
    static int sPoolSize;
    @Nullable
    ChainedRef mNext;
    @Nullable
    ChainedRef mPrev;
    @Nullable
    Runnable mRunnable;
    @Nullable
    WeakRunnable mWrapper;

    public ChainedRef(@Nullable Runnable runnable) {
      this.mRunnable = runnable;
    }

    public static ChainedRef obtain(Runnable runnable) {
      ChainedRef result = null;
      synchronized (ChainedRef.class) {
        if (sPool != null) {
          result = sPool;
          sPool = sPool.mNext;
          sPoolSize--;
        }
      }
      if (result != null) {
        result.mRunnable = runnable;
        return result;
      }

      return new ChainedRef(runnable);
    }

    public void remove() {
      if (mPrev != null) {
        mPrev.mNext = mNext;
      }

      if (mNext != null) {
        mNext.mPrev = mPrev;
      }

      this.mPrev = null;
      this.mRunnable = null;
      this.mWrapper = null;
      synchronized (ChainedRef.class) {
        if (sPoolSize > MAX_POOL_SIZE) {
          return;
        }

        this.mNext = sPool;
        sPool = this;
        sPoolSize++;
      }
    }

    public void insertAbove(@NonNull ChainedRef candidate) {
      if (mNext != null) {
        mNext.mPrev = candidate;
      }

      candidate.mNext = mNext;
      mNext = candidate;
      candidate.mPrev = this;
    }

    @Nullable
    public ChainedRef findForward(Runnable runnable) {
      ChainedRef current = this;
      while (current != null) {
        if (current.mRunnable != null) {
          if (current.mRunnable.equals(runnable)) {
            return current;
          }
        } else if (runnable == null) {
          return current;
        }
        current = current.mNext;
      }

      return null;
    }
  }
}
