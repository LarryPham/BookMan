package com.techiedb.app.bookman.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since May.09.2015
 */
public class JsonUtils {
  public static String QUERY_SEPARATOR = ".";
  public static String QUERY_ARRAY_INDEX_START = "[";
  public static String QUERY_ARRAY_INDEX_END = "]";
  public static String QUERY_ARRAY_FIRST = "first";
  public static String QUERY_ARRAY_LAST = "last";

  public static final String JSON_NULL_STR = "null";
  private static final String TAG = LogUtils.makeLogTag(JsonUtils.class);

  public static <T> T queryJson(JSONObject source, String query, T defaultObject) {
    int nextSeparator = query.indexOf(QUERY_SEPARATOR);
    int nextIndexStart = query.indexOf(QUERY_ARRAY_INDEX_START);
    if (nextSeparator == -1 && nextIndexStart == -1){
      try {
        if (!source.has(query)) {
          return defaultObject;
        }

        Object result = source.get(query);
        if (result.getClass().isAssignableFrom(defaultObject.getClass())) {
          return (T) result;
        } else {
          return defaultObject;
        }
      } catch (ClassCastException | JSONException ex) {
        LogUtils.LOGE(TAG, "Unable to cast the object to " + defaultObject.getClass().getName(), ex);
        return defaultObject;
      }
    }
    int endQuery;
    if (nextSeparator == -1 || nextIndexStart == -1) {
      endQuery = Math.max(nextSeparator, nextIndexStart);
    } else {
      endQuery = Math.min(nextSeparator, nextIndexStart);
    }
    String nextQuery = query.substring(endQuery);
    String key = query.substring(0, endQuery);

    try {
      if (source == null) {
        return defaultObject;
      }
      if (nextQuery.indexOf(QUERY_SEPARATOR) == 0) {
        return queryJson(source.getJSONObject(key), nextQuery.substring(1), defaultObject);
      } else if (nextQuery.indexOf(QUERY_ARRAY_INDEX_START) == 0) {
        return queryJson(source.getJSONArray(key), nextQuery, defaultObject);
      } else if (!nextQuery.equals("")) {
        return defaultObject;
      }

      Object result = source.get(key);
      if (result.getClass().isAssignableFrom(defaultObject.getClass())) {
        return (T) result;
      } else {
        LogUtils.LOGE(TAG, String.format("The returned object type %s is not assignable to the type %s. Using defualt!", result.getClass
            (), defaultObject.getClass()));
        return defaultObject;
      }
    } catch (java.lang.ClassCastException ex) {
      LogUtils.LOGE(TAG, "Unable to cast the object to" + defaultObject.getClass().getName(), ex);
      return defaultObject;
    } catch (JSONException ex) {
      return defaultObject;
    }
  }

  public static<T> T queryJson(JSONArray source, String query, T defaultObject) {
    // query must start with [ have an index and then have ]
    int indexStart = query.indexOf(QUERY_ARRAY_INDEX_START);
    int indexEnd = query.indexOf(QUERY_ARRAY_INDEX_END);
    if (indexStart == -1 || indexEnd == -1 || indexStart > indexEnd) {
      return defaultObject;
    }

    String indexStr = query.substring(indexStart + 1, indexEnd);
    int index;
    if (indexStr.equals(QUERY_ARRAY_FIRST)) {
      index = 0;
    } else if (indexStr.equals(QUERY_ARRAY_LAST)) {
      index = -1;
    } else {
      index = Integer.parseInt(indexStr);
    }

    if (index < 0) {
      index = source.length() + index;
    }

    String remainingQuery = query.substring(indexEnd + 1);
    try {
      if (remainingQuery.indexOf(QUERY_ARRAY_INDEX_START) == 0) {
        return queryJson(source.getJSONArray(index), remainingQuery, defaultObject);
      } else if (remainingQuery.indexOf(QUERY_SEPARATOR) == 0) {
        return queryJson(source.getJSONObject(index), remainingQuery.substring(1), defaultObject);
      } else if (!remainingQuery.equals("")){
        LogUtils.LOGW(TAG, String.format("Incorrect query for next object %s", remainingQuery));
        return defaultObject;
      }

      Object result = source.get(index);
      if (result.getClass().isAssignableFrom(defaultObject.getClass())) {
        return (T)result;
      } else {
        LogUtils.LOGW(TAG, String.format("The returned object type %s is not assignable to the type %s. Using default!",
                                         result.getClass(), defaultObject.getClass()));
        return defaultObject;
      }
    } catch (java.lang.ClassCastException ex) {
      LogUtils.LOGE(TAG, "Unable to cast the object to " + defaultObject.getClass().getName(), ex);
      return defaultObject;
    } catch (JSONException e) {
      e.printStackTrace();
      return defaultObject;
    }
  }

  public static ArrayList<String> fromJsonArrayToStringList(JSONArray jsonArray) {
    ArrayList<String> stringList = new ArrayList<>();
    for (int index = 0; index < jsonArray.length(); index++) {
      try {
        stringList.add(jsonArray.getString(index));
      } catch (JSONException ex) {
        LogUtils.LOGE(TAG, ex.toString());
      }
    }
    return stringList;
  }

  public static JSONArray fromStringListToJSONArray(ArrayList<String> stringList) {
    JSONArray jsonArray = new JSONArray();
    if (stringList != null) {
      for (int index = 0; index < stringList.size(); index++) {
        jsonArray.put(stringList.get(index));
      }
    }
    return jsonArray;
  }

  public static String getString(JSONObject json, String name) {
    String value = json.optString(name);
    if (JSON_NULL_STR.equals(value)) {
      return "";
    }
    return value;
  }

  /**
   * Replacement for JSONObject.optBoolean() - optBoolean() only checks for "true" and "false"
   */
  public static boolean getBool(JSONObject json, String name) {
    String value= getString(json, name);
    if (TextUtils.isEmpty(value)) {
      return false;
    }

    if (value.equals("0")) {
      return false;
    }

    if (value.equalsIgnoreCase("false")) {
      return false;
    }
    return true;
  }
  /**
   * Returns the JSONObject chidl of the passed parent that matches the passed query this is basically
   * an "optJSONObject" that supports nested queries, for example:
   *
   * getJSONChild("meta/data/site")
   * would find this:
   * "meta":{
   *   "data":{
   *     "site":{
   *       "ID":23424,
   *       "name":"http.fancise.com"
   *     }
   *   }
   * }
   */
  public static JSONObject getJSONChild(final JSONObject jsonParent, final String query) {
    if (jsonParent == null || TextUtils.isEmpty(query)) {
      return null;
    }
    String[] names = query.split("/");
    JSONObject jsonChild = null;
    for (int index = 0; index < names.length; index++) {
      if (jsonChild == null) {
        jsonChild = jsonParent.optJSONObject(names[index]);
      } else {
        jsonChild = jsonChild.optJSONObject(names[index]);
      }
      if (jsonChild == null) {
        return null;
      }
    }
    return jsonChild;
  }

}
