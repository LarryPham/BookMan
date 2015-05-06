package com.techiedb.app.bookman.services.tasks;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.controllers.ControllerMessage;
import com.techiedb.app.bookman.models.Error;
import com.techiedb.app.bookman.services.BookResult;
import com.techiedb.app.bookman.services.BooksResult;

import org.json.JSONException;

/**
 * Copyright (C) 2014 Techie Digital Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Techie Digital Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Techie Digital
 * Inc. Techie Digital Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since Apr.05.2015
 */
public class ServerRequestTask extends Thread {

  public static final String TAG = Properties.PREFIX + ServerRequestTask.class.getSimpleName();

  static final int PDF = 1;
  static final int UNKNOWN = 2;
  private String mAction = null;
  // Params for requesting the list of books or book's detail
  private String mKeyword = null;
  private long mBookId = 0;
  private RequestType mRequestType = RequestType.NONE;
  private int mRequestOwner = 0;
  private int mRequestMessage = 0;
  private boolean mCancelled = false;
  private Handler mServiceHandler;

  private int mMaxSize = Properties.MAX_SIZE_INIT_VALUE;
  private int mPageSize = Properties.PAGE_SIZE_INIT_VALUE;
  private int mPageIndex = Properties.PAGE_INDEX_INIT_VALUE;

  private JsonParser mJsonParser;
  private String mUrl;

  public ServerRequestTask() {

  }

  public static enum RequestType {
    NONE, BOOKS_LIST, BOOK_DETAIL, BOOKS_LIST_PAGING
  }

  public ServerRequestTask(String action, Handler handler) {
    this.mAction = action;
    this.mServiceHandler = handler;
  }

  public ServerRequestTask(String action, long bookId, Handler handler) {
    this.mServiceHandler = handler;
    this.mAction = action;
    this.mBookId = bookId;
  }

  public void run() {
    String fn = "run(): ";
    switch (mRequestType) {
      case BOOKS_LIST:{
        getBooksCollection();
        break;
      }
      case BOOK_DETAIL: {
        getRequestedBookDetail();
        break;
      }
      case BOOKS_LIST_PAGING: {
        getRequestBooksCollectionByPaging();
        break;
      }
      default: {
        break;
      }
    }
  }

  public String getAction() {
    return mAction;
  }

  public void setAction(String action) {
    mAction = action;
  }

  public String getKeyword() {
    return mKeyword;
  }

  public void setKeyword(String keyword) {
    mKeyword = keyword;
  }

  public long getBookId() {
    return mBookId;
  }

  public void setBookId(long bookId) {
    mBookId = bookId;
  }

  public RequestType getRequestType() {
    return mRequestType;
  }

  public void setRequestType(RequestType requestType) {
    mRequestType = requestType;
  }

  public int getRequestOwner() {
    return mRequestOwner;
  }

  public void setRequestOwner(int requestOwner) {
    mRequestOwner = requestOwner;
  }

  public int getRequestMessage() {
    return mRequestMessage;
  }

  public void setRequestMessage(int requestMessage) {
    mRequestMessage = requestMessage;
  }

  public boolean isCancelled() {
    return mCancelled;
  }

  public void setCancelled(boolean cancelled) {
    mCancelled = cancelled;
  }

  public Handler getServiceHandler() {
    return mServiceHandler;
  }

  public void setServiceHandler(Handler serviceHandler) {
    mServiceHandler = serviceHandler;
  }

  public int getMaxSize() {
    return mMaxSize;
  }

  public void setMaxSize(int maxSize) {
    mMaxSize = maxSize;
  }

  public int getPageSize() {
    return mPageSize;
  }

  public void setPageSize(int pageSize) {
    mPageSize = pageSize;
  }

  public int getPageIndex() {
    return mPageIndex;
  }

  public void setPageIndex(int pageIndex) {
    mPageIndex = pageIndex;
  }

  /**
   * Getting the collections of requested books by searching list of books with specified keyword.
   *
   */
  public void getBooksCollection() {
    String fn = "getBooksCollection(): ";
    BooksResult result;
    JsonParser parser = this.mJsonParser;
    this.mUrl = makeCollectionBooksURL();
    parser.setURL(this.mUrl);
    parser.setKeyword(mKeyword);

    try {
      result = parser.readBooksResult();
      if (result == null) {
        if (isCancelled()) {
          Log.d(TAG, fn + String.format("Task Cancelled"));
        } else {
          Log.e(TAG, fn + String.format("Error: result is null && not cancelled!!"));
        }
      } else {
        if (result.getResultCode() > 0) {
          final Error error = new Error(ControllerMessage.REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL);
          sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
        } else {
          sendMessage(ControllerMessage.REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD_COMPLETED, mRequestOwner, mRequestMessage, result);
        }
      }
    } catch (Exception e) {
      Error error;
      if (e instanceof JSONException) {
        error = new Error(ControllerMessage.REQUEST_ERROR_REASON_JSON_PARSE_EXCEPTION);
      } else{
        error = new Error(ControllerMessage.REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL);
      }
      sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
      e.printStackTrace();
    }
  }

  public void getRequestedBookDetail() {
    String fn = "getRequestedBookDetail(): ";
    BookResult result;
    this.mUrl = makeRequestedBookDetailURL();
    JsonParser parser = this.mJsonParser;
    parser.setURL(this.mUrl);
    parser.setBookId(mBookId);
    try {
      result = parser.readBookDetail();
      if (result == null) {
        if (isCancelled()) {
          Log.d(TAG, fn + String.format("Task Cancelled."));
        } else {
          Log.d(TAG, fn + String.format("Error: Result is null && not cancelled."));
        }
      } else {
        if (result.getResultCode() > 0) {
          final Error error = new Error(ControllerMessage.REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL);
          Log.d(TAG, fn + String.format("SendingBack error: %s", error.toString()));
          sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
        } else {
          Log.d(TAG, fn + String.format("Request to get the book's detail successfully: %s", result.toString()));
          sendMessage(ControllerMessage.REQUEST_TO_SERVER_EBOOK_DETAIL_COMPLETED, mRequestOwner, mRequestMessage, result);
        }
      }
    } catch (Exception e) {
      Log.e(TAG, fn + String.format("Exception: %s", e.getMessage()));
      Error error;
      if (e instanceof JSONException) {
        error = new Error(ControllerMessage.REQUEST_ERROR_REASON_JSON_PARSE_EXCEPTION);
      } else{
        error = new Error(ControllerMessage.REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL);
      }
      sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
      e.printStackTrace();
    }
  }

  public void getRequestBooksCollectionByPaging() {
    String fn = "getRequestBooksCollectionByPaging(): ";
    BooksResult result = new BooksResult();
    this.mUrl = makeCollectionBooksPagingURL();
    JsonParser parser = this.mJsonParser;
    parser.setURL(this.mUrl);
    try {
      result = parser.readBooksResult();
      if (result == null) {
        if (isCancelled()) {
          Log.d(TAG, fn + String.format("Task Cancelled"));
        } else {
          Log.d(TAG, fn + String.format("Error: result is null && not cancelled"));
        }
      } else {
        if (result.getResultCode() > 0) {
          final Error error = new Error(ControllerMessage.REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL);
          Log.d(TAG, fn + String.format("SendingBack error: %s", error.toString()));
          sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
        } else {
          Log.d(TAG, fn + String.format("Request to get the book's detail successfully: %s", result.toString()));
          sendMessage(ControllerMessage.REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD_PAGING_COMPLETED, mRequestOwner, mRequestMessage, result);
        }
      }
    } catch (Exception e) {
      Log.e(TAG, fn + String.format("Exception: %s", e.getMessage()));
      Error error;
      if (e instanceof JSONException) {
        error = new Error(ControllerMessage.REQUEST_ERROR_REASON_JSON_PARSE_EXCEPTION);
      } else{
        error = new Error(ControllerMessage.REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL);
      }
      sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
      e.printStackTrace();
    }
  }

  // http://it-ebooks.info/api/v1/search/mKeyword
  private String makeCollectionBooksURL() {
    String fn = "makeCollectionsBookURL(): ";
    String url = null;
    url = Properties.URL + "search/" + mKeyword;
    Log.d(TAG, String.format("Requesting the collection of books: %s", url));
    return url;
  }
  // http://it-ebooks.info/api/v1/search/requestBookString/page/pageIndex
  private String makeCollectionBooksPagingURL() {
    String fn = "makeCollectionBooksPaginURL(): ";
    String url = null;
    url = Properties.URL + "search/" + mKeyword + "/page/";
    if (mPageSize != -1) {
      url += mPageSize;
    }
    Log.d(TAG, String.format("Requesting the collection of books: %s - page: %d", url, mPageSize));
    return url;
  }

  // http://it-ebooks.info/api/v1/bookId
  private String makeRequestedBookDetailURL() {
    String fn = "makeRequestedBookDetailURL(): ";
    String url = null;
    url = Properties.URL + "book/";
    if (mBookId != 0) {
      url += mBookId;
    }
    Log.d(TAG, String.format("RequestBook's URL: %s", url));
    return url;
  }

  private void sendMessage(int code, int requestOwner, int requestMessage, Object obj) {
    String fn = "sendMessage(): ";
    final Message msg = new Message();
    Log.d(TAG, fn + "code     = " + ControllerMessage.toString(code) + "(" + code + ")");
    Log.d(TAG, fn + "requestOwner = " + requestOwner);
    Log.d(TAG, fn + "requestMsg   = " + ControllerMessage.toString(requestMessage) + "(" + requestMessage + ")");

    if (obj instanceof Error) {
      Error error = (Error) obj;
      int errorCode = error.getErrorCode();
      Log.d(TAG, fn + "errorCode    = " + ControllerMessage.toString(errorCode) + "(" + errorCode + ")");
    }
    msg.what = code;
    msg.arg1 = requestOwner;
    msg.arg2 = requestMessage;
    msg.obj = obj;

    if (this.isCancelled()) {
      Log.d(TAG, fn + "Task Cancelled. Do not sendMessage(): ");
    } else {
      mServiceHandler.sendMessage(msg);
    }
  }
}
