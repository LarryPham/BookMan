package com.techiedb.app.bookman.utils;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableNotifiedException;
import com.google.android.gms.common.Scopes;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.techiedb.app.bookman.Properties;

import java.io.IOException;
import java.util.UUID;

/**
 * Copyright (C) 2014 Sugar Ventures Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation are
 * confidential and proprietary information of Sugar Ventures Inc.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of Sugar Ventures
 * Inc. Sugar Ventures Inc makes no representations with respect to the contents, and assumes no responsibility for any errors that might
 * appear in the software and documents. This publication and the contents hereof are subject to change without notice. History
 *
 * @author Larry Pham
 * @since Apr.19.2015
 */
public class AccountUtils {
  private static final String TAG = Properties.PREFIX + AccountUtils.class.getSimpleName();

  private static final String PREF_ACTIVE_ACCOUNT = "chosen_account";
  private static final String PREFIX_PREF_AUTH_TOKEN = "auth_token_";
  private static final String PREFIX_PREF_PLUS_PROFILE_ID = "plus_profile_id_";
  private static final String PREFIX_PREF_PLUS_NAME = "plus_name_";
  private static final String PREFIX_PREF_PLUS_IMAGE_URL = "plus_image_url_";
  private static final String PREFIX_PREF_PLUG_COVER_URL = "plus_cover_url";
  private static final String PREFIX_PREF_GCM_KEY = "gcm_key_";

  public static final String AUTH_SCOPES[] = {
      Scopes.PLUS_LOGIN, Scopes.DRIVE_APPFOLDER,
      "https://www.googleapis.com/auth/userinfo.email"
  };

  static final String AUTH_TOKEN_TYPE;

  static {
    StringBuilder sb = new StringBuilder();
    sb.append("oath2:");
    for (String scope: AUTH_SCOPES) {
      sb.append(scope);
      sb.append(" ");
    }
    AUTH_TOKEN_TYPE = sb.toString();
  }

  private static SharedPreferences getSharedPreferences(final Context context) {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }

  public static boolean hasActiveAccount(final Context context) {
    return !TextUtils.isEmpty(getActiveAccountName(context));
  }

  public static String getActiveAccountName(final Context context) {
    SharedPreferences sp = getSharedPreferences(context);
    return sp.getString(PREF_ACTIVE_ACCOUNT, null);
  }

  public static Account getActiveAccount(final Context context) {
    String account = getActiveAccountName(context);
    if (account != null) {
      return new Account(account, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
    } else {
      return null;
    }
  }

  public static boolean setActiveAccount(final Context context, final String accountName) {
    Log.d(TAG, String.format("Set active account to: %s", accountName));
    SharedPreferences sp = getSharedPreferences(context);
    sp.edit().putString(PREF_ACTIVE_ACCOUNT, accountName).apply();
    return true;
  }

  public static String makeAccountSpecificPrefKey(Context context, String prefix) {
    return hasActiveAccount(context) ? makeAccountSpecificPrefKey(getActiveAccountName(context), prefix): null;
  }

  public static String makeAccountSpecificPrefKey(String accountName, String prefix) {
    return prefix + accountName;
  }

  public static String getAuthToken(final Context context) {
    SharedPreferences sp = getSharedPreferences(context);
    return hasActiveAccount(context) ? sp.getString(makeAccountSpecificPrefKey(context, PREFIX_PREF_AUTH_TOKEN), null) : null;
  }

  public static void setAuthToken(final Context context, final String accountName, final String token) {
    Log.d(TAG, String.format("Auth token of length %d for %s", (TextUtils.isEmpty(token) ? 0 : token.length()), accountName));
    SharedPreferences sp = getSharedPreferences(context);
    sp.edit().putString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_AUTH_TOKEN), token).apply();
    Log.v(TAG, String.format("Putting AuthToken: %s", token));
  }

  public static void setAuthToken(final Context context, final String authToken) {
    if (hasActiveAccount(context)) {
      setAuthToken(context, getActiveAccountName(context), authToken);
    } else {
      Log.e(TAG, String.format("Can't set auth token because there is no chosen account"));
    }
  }

  static void invalidateAuthToken(final Context context) {
    GoogleAuthUtil.invalidateToken(context, getAuthToken(context));
    setAuthToken(context, null);
  }

  public static void setPlusProfileId(final Context context, final String accountName, final String profileId) {
    SharedPreferences sp = getSharedPreferences(context);
    sp.edit().putString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_PLUS_PROFILE_ID), profileId).apply();
  }

  public static String getPlusProfileId(final Context context) {
    SharedPreferences sp = getSharedPreferences(context);
    return hasActiveAccount(context) ? sp.getString(makeAccountSpecificPrefKey(context, PREFIX_PREF_PLUS_PROFILE_ID), null) : null;
  }

  public static boolean hasToken(final Context context, final String accountName) {
    SharedPreferences sp = getSharedPreferences(context);
    return !TextUtils.isEmpty(sp.getString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_AUTH_TOKEN), null));
  }

  public static void setPlusName(final Context context, final String accountName, final String name) {
    SharedPreferences sp = getSharedPreferences(context);
    sp.edit().putString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_PLUS_NAME), name).apply();
  }

  public static String getPlusName(final Context context) {
    SharedPreferences sp = getSharedPreferences(context);
    return hasActiveAccount(context) ? sp.getString(makeAccountSpecificPrefKey(context, PREFIX_PREF_PLUS_NAME), null) : null;
  }

  public static void setPlusNameImageURL(final Context context, final String accountName, final String imageUrl) {
    SharedPreferences sp = getSharedPreferences(context);
    sp.edit().putString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_PLUS_IMAGE_URL), imageUrl).apply();
  }

  public static String getPlusNameImageURL(Context context) {
    SharedPreferences sp = getSharedPreferences(context);
    return hasActiveAccount(context) ? sp.getString(makeAccountSpecificPrefKey(context, PREFIX_PREF_PLUS_IMAGE_URL), null) : null;
  }

  public static String getPlusNameImageURL(final Context context, final String accountName) {
    SharedPreferences sp = getSharedPreferences(context);
    return hasActiveAccount(context) ? sp.getString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_PLUS_IMAGE_URL), null) : null;
  }

  public static void refreshAuthToken(Context context) {
    invalidateAuthToken(context);
    tryAuthenticateWithErrorNotification(context, "");
  }

  public static void setPlusCoverURL(Context context, final String accountName, String coverPhotoUrl) {
    SharedPreferences sp = getSharedPreferences(context);
    sp.edit().putString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_PLUG_COVER_URL), coverPhotoUrl).apply();
  }

  public static String getPlusCoverUrl(final Context context) {
    SharedPreferences sp = getSharedPreferences(context);
    return hasActiveAccount(context) ? sp.getString(makeAccountSpecificPrefKey(context, PREFIX_PREF_PLUG_COVER_URL), null) : null;
  }

  static void tryAuthenticateWithErrorNotification(Context context, String syncAuthority) {
    try {
      String accountName = getActiveAccountName(context);
      if (accountName != null) {
        Log.i(TAG, String.format("Requesting new auth token (with notification"));
        final String token = GoogleAuthUtil.getTokenWithNotification(context, accountName, AUTH_TOKEN_TYPE, null, syncAuthority, null);
        setAuthToken(context, token);
      } else {
        Log.e(TAG, String.format("Can't try authentication because no account is chosen"));
      }
    } catch (UserRecoverableNotifiedException e) {
      e.printStackTrace();
      Log.w(TAG, String.format("User recoverable exception. Check notification. %s",e));
    } catch (GoogleAuthException e) {
      e.printStackTrace();
      Log.e(TAG, String.format("Unrecoverable authentication exception. %s", e.getMessage()));
    } catch (IOException e) {
      Log.e(TAG, String.format("Transient error encountered: %s", e.getMessage()));
      e.printStackTrace();
    }
  }

  public static void setGcmKey(final Context context, final String accountName, final String gcmKey) {
    SharedPreferences sp = getSharedPreferences(context);
    sp.edit().putString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_GCM_KEY), gcmKey).apply();
    Log.d(TAG, String.format("GCM Key of account %s set to: %s", accountName, sanitizeGcmKey(gcmKey)));
  }

  public static String getGcmKey(final Context context, final String accountName) {
    SharedPreferences sp = getSharedPreferences(context);
    String gcmKey = sp.getString(makeAccountSpecificPrefKey(accountName, PREFIX_PREF_GCM_KEY), null);

    if (TextUtils.isEmpty(gcmKey)) {
      gcmKey = UUID.randomUUID().toString();
      Log.d(TAG, String.format("No GCM key on account %s . Generating randome one: %s", accountName, sanitizeGcmKey(gcmKey)));
      setGcmKey(context, accountName, gcmKey);
    }
    return gcmKey;
  }

  public static String sanitizeGcmKey(String key) {
    if (key == null) {
      return "(null)";
    } else if (key.length() > 0) {
      return key.substring(0, 4) + "........." + key.substring(key.length() - 4);
    } else {
      return "..........";
    }
  }
}
