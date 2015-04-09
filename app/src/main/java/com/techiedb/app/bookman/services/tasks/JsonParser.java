package com.techiedb.app.bookman.services.tasks;

import android.util.Log;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.controllers.ControllerMessage;
import com.techiedb.app.bookman.models.JsonBook;
import com.techiedb.app.bookman.models.JsonBookItem;
import com.techiedb.app.bookman.models.JsonBooks;
import com.techiedb.app.bookman.services.BookResult;
import com.techiedb.app.bookman.services.BooksResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since Apr.09.2015
 */
public class JsonParser {
  public static final String TAG = Properties.PREFIX + JsonParser.class.getSimpleName();
  private static final boolean DEBUG = false;

  public static final String RESULT_ERROR_CODE = "Error";
  public static final String RESULT_TIME = "Time";
  public static final String RESULT_TOTAL ="Total";
  public static final String RESULT_PAGE = "Page";

  public static final String BOOKS = "Books";
  public static final String ID = "Id";
  public static final String TITLE = "Title";
  public static final String TIME = "Time";
  public static final String SUB_TITLE = "SubTitle";
  public static final String DESCRIPTION = "Description";
  public static final String IMAGE = "Image";
  public static final String ISBN = "isbn";
  public static final String AUTHOR = "Author";
  public static final String YEAR = "Year";
  public static final String PAGE ="Page";
  public static final String PUBLISHER = "Publisher";
  public static final String DOWNLOAD_URL = "Download";
  public static final String BOOK = "Book";

  private String mURL = null;
  private String mKeyword = null;
  private int mBookId = 0;
  private int mPageNumber = 0;

  private int mMaxSize = Properties.MAX_SIZE_INIT_VALUE;
  private int mPageSize = Properties.PAGE_SIZE_INIT_VALUE;
  private int mPageIndex = Properties.PAGE_INDEX_INIT_VALUE;

  private String mResultCode = null;
  private String mResultMessage = null;
  private String mFeedURL = null;

  private int mErrorCode = 0;
  private boolean mCancelled = false;

  public JsonParser() {

  }

  public JsonParser(String url) {
    this.mURL = url;
  }


  public String getURL() {
    return mURL;
  }

  public void setURL(String URL) {
    mURL = URL;
  }

  public String getKeyword() {
    return mKeyword;
  }

  public void setKeyword(String keyword) {
    mKeyword = keyword;
  }

  public int getBookId() {
    return mBookId;
  }

  public void setBookId(int bookId) {
    mBookId = bookId;
  }

  public int getPageNumber() {
    return mPageNumber;
  }

  public void setPageNumber(int pageNumber) {
    mPageNumber = pageNumber;
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

  public String getResultCode() {
    return mResultCode;
  }

  public void setResultCode(String resultCode) {
    mResultCode = resultCode;
  }

  public String getResultMessage() {
    return mResultMessage;
  }

  public void setResultMessage(String resultMessage) {
    mResultMessage = resultMessage;
  }

  public String getFeedURL() {
    return mFeedURL;
  }

  public void setFeedURL(String feedURL) {
    mFeedURL = feedURL;
  }

  public int getErrorCode() {
    return mErrorCode;
  }

  public void setErrorCode(int errorCode) {
    mErrorCode = errorCode;
  }

  public boolean isCancelled() {
    return mCancelled;
  }

  public void setCancelled(boolean cancelled) {
    mCancelled = cancelled;
  }

  public BookResult readBookDetail() throws Exception {
    String fn = "readBookDetail(): ";

    BookResult result = new BookResult();
    InputStream inStream = null;
    try {
      long time = System.currentTimeMillis();
      if ((inStream = getInputStream()) == null) {
        Log.e(TAG,fn + String.format("getInputStream(): "));
        throw new IOException();
      }
      String fullStr = readStream(inStream);
      if (fullStr == null || fullStr.length() == 0) {
        Log.e(TAG, fn + "readStream() is empty!");
        inStream.close();
        mErrorCode = ControllerMessage.REQUEST_ERROR_REASON_INTERNAL_SERVER_ERROR;
        throw new IOException();
      }
      JSONObject jsonObject = new JSONObject("Book" + fullStr);
      printFormattedJsonStr(jsonObject, fullStr);
      JSONObject jsonObj = (JSONObject) jsonObject.get("Book");
      String resultErrorCode = jsonObject.getString(RESULT_ERROR_CODE);
      if (Integer.parseInt(resultErrorCode) > 0) {
        Log.e(TAG, fn + String.format("ERROR: resultCode = %s", resultErrorCode));
        inStream.close();
        return result;
      }
      JsonBook jsonBook = new JsonBook();

      jsonBook.setId(jsonObj.optInt("ID"));
      jsonBook.setTitle(jsonObj.optString("Title"));
      jsonBook.setSubTitle(jsonObj.optString("SubTitle"));
      jsonBook.setDescription(jsonObj.optString("Description"));
      jsonBook.setAuthor(jsonObj.optString("Author"));
      jsonBook.setISBN(jsonObj.optString("ISBN"));
      jsonBook.setYear(jsonObj.optString("Year"));
      jsonBook.setPage(jsonObj.optString("Page"));
      jsonBook.setPublisher(jsonObj.optString("Publisher"));
      jsonBook.setDownloadImageURL(jsonObj.optString("Image"));
      jsonBook.setDownloadURL(jsonObj.optString("Download"));
      long secondTime = System.currentTimeMillis();
      Log.d(TAG, fn + String.format("Parsing Time= %dms", (secondTime - time)));

      if (isCancelled()) {
        result = null;
        Log.e(TAG, fn + String.format("Parsing cancelled!! break"));
      } else {
        result.setBook(jsonBook);
      }
    } catch (JSONException e) {
      throw new JSONException("JSONException");
    } catch (Exception e) {
      Log.e(TAG, fn + "Exception: " + e.toString());
      throw e;
    }
    return result;
  }

  public BooksResult readBooksResult() throws Exception {
    String fn = "readBooksResult(): ";
    BooksResult result = new BooksResult();
    InputStream inStream = null;

    try {
      long startTime = System.currentTimeMillis();
      if ((inStream = getInputStream()) == null) {
        Log.e(TAG, fn + String.format("getInputStream() failed"));
        throw new IOException();
      }
      String fullStr = readStream(inStream);
      if (fullStr == null || fullStr.length() == 0) {
        Log.e(TAG, fn + "readStream(0 is Empty!!");
        inStream.close();
        throw new IOException();
      }

      JSONObject jsonObj = new JSONObject(fullStr);
      printFormattedJsonStr(jsonObj, fullStr);

      String resultCode = jsonObj.getString(RESULT_ERROR_CODE);
      result.setResultCode(Integer.parseInt(resultCode));
      if (Integer.parseInt(resultCode) > 0) {
        Log.e(TAG, fn + String.format("ERROR: resultCode = %s", resultCode));
        inStream.close();
        return result;
      }

      JSONArray array = new JSONArray(jsonObj.getString(BOOKS));
      JsonBooks jsonBooks = new JsonBooks();
      jsonBooks.setError(jsonObj.getString(RESULT_ERROR_CODE));
      jsonBooks.setPage(jsonObj.getInt(RESULT_PAGE));
      jsonBooks.setTime(jsonObj.getDouble(RESULT_TIME));
      jsonBooks.setTotal(jsonObj.getString(RESULT_TOTAL));

      int length = array.length();
      for (int index = 0; index < length; index++) {
        if (isCancelled()) {
          Log.e(TAG, fn + String.format("Parsing cancelled!!! break."));
          break;
        }

        JsonBookItem jsonBookItem = new JsonBookItem();
        JSONObject jsonObject = (JSONObject) array.getJSONObject(index);
        jsonBookItem.setId(jsonObject.getLong(ID));
        jsonBookItem.setISBN(jsonObject.getString(ISBN));
        jsonBookItem.setTitle(jsonObject.getString(TITLE));
        jsonBookItem.setSubTitle(jsonObject.getString(SUB_TITLE));
        jsonBookItem.setDescription(jsonObject.getString(DESCRIPTION));
        jsonBookItem.setCoverBookImageURL(jsonObject.getString(IMAGE));
        jsonBooks.getJsonBookList().add(jsonBookItem);
      }
      long endTime = System.currentTimeMillis();
      Log.d(TAG, fn + String.format("Parsing time: %dms", (endTime - startTime)));
      if (isCancelled()) {
        result = null;
        Log.e(TAG, fn + String.format("Parsing cancelled!!!"));
      } else {
        result.setJsonBooks(jsonBooks);
      }
      inStream.close();
    } catch (JSONException e) {
      Log.e(TAG, fn + "Exception: JSON>>>>");
      throw new JSONException("JSONException");
    } catch (Exception e) {
      Log.e(TAG, fn + "Exception: " + e.toString());
    }
    return result;
  }

  public InputStream getInputStream() throws SocketTimeoutException {
    String fn = "getInputStream(): ";
    InputStream inputStream = null;
    File file = null;
    String domain = this.mURL.substring(0, 4);

    if (domain.compareToIgnoreCase("http") == 0) {
      inputStream = openHttpConnection();
      if (inputStream == null) {
        Log.e(TAG, fn + "InputStream is null");
      }
    } else {
      file = new File(this.mURL);
      try {
        inputStream = new FileInputStream(file);
      } catch (FileNotFoundException e) {
        Log.e (TAG, fn + "Exception: " + e.toString());
        e.printStackTrace();
      }
    }
    return inputStream;
  }

  public String readStream(InputStream inputStream) {
    String fn = "readStream(): ";
    final int BUFFER_LENGTH = 4096;
    String str = null;
    byte[] bytes = new byte[BUFFER_LENGTH];
    int n = 0;
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();

    try {
      while ((n = inputStream.read(bytes)) != -1) {
        outStream.write(bytes, 0, n);
      }
      outStream.flush();
      str = outStream.toString();
      outStream.close();
    } catch (IOException | NullPointerException e) {
      Log.e(TAG, fn + "Exception: " + e.toString());
      e.printStackTrace();
    }
    return str;
  }

  public InputStream openHttpConnection() throws SocketTimeoutException {
    String fn = "openHttpConnection(): ";
    HttpURLConnection conn = null;
    InputStream inputStream = null;

    try {
      URL url = new URL(this.mURL);
      conn = (HttpURLConnection) url.openConnection();
      conn.setUseCaches(false);
      conn.setDefaultUseCaches(false);
      conn.setDoInput(true);
      conn.setAllowUserInteraction(true);
      conn.setConnectTimeout(Properties.CONNECTION_TIMEOUT);
      conn.setReadTimeout(Properties.CONNECTION_READ_TIMEOUT);
      try {
        conn.connect();
      } catch (SocketTimeoutException ex) {
        mErrorCode = ControllerMessage.REQUEST_ERROR_REASON_SERVER_CONNECT_TIMEOUT;
        throw ex;
      }
      try {
        inputStream = conn.getInputStream();
      } catch (SocketTimeoutException ex) {
        mErrorCode = ControllerMessage.REQUEST_ERROR_REASON_SERVER_READ_TIMEOUT;
      }
    } catch (IOException e) {
      mErrorCode = ControllerMessage.REQUEST_ERROR_REASON_HTTP_CONNECT_FAIL;
      if (e instanceof  SocketTimeoutException) {
        mErrorCode = ControllerMessage.REQUEST_ERROR_REASON_SERVER_TIME_OUT;
      }
      Log.e(TAG, fn + "Exception: " + e.toString());
      e.printStackTrace();
      if (conn != null) {
        conn.disconnect();
      }
    }
    return inputStream;
  }

  public void printFormattedJsonStr(JSONObject obj, String str) {
    if (DEBUG) {
      String fn = "printFormattedJsonStr(): ";
      final int BUFFER_LENGTH = 4096;
      final int INDENT = 4;
      try {
        String jsonStr = obj.toString(INDENT);
        String subStr = null;
        Log.d(TAG, fn + "=========================== Start of JsonStr ===============================");
        int length = jsonStr.length();
        int page = length / BUFFER_LENGTH;
        int start = 0;
        int end = BUFFER_LENGTH;
        for (int i = 0; i < page; i++) {
          subStr = jsonStr.substring(start,end);
          start += BUFFER_LENGTH;
          end += BUFFER_LENGTH;
        }
        subStr = jsonStr.substring(start);
        Log.d(TAG, subStr);
        Log.d(TAG, fn + "=========================== End of JsonStr ===================================");
      } catch (Exception ex) {
        Log.e(TAG, fn + "Exception: " + ex.toString());
        ex.printStackTrace();
      }
    }
  }
}
