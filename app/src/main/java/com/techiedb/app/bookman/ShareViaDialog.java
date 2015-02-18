package com.techiedb.app.bookman;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
public class ShareViaDialog extends Dialog {

  protected static final String TAG = Properties.PREFIX + ShareViaDialog.class.getSimpleName();
  private List<ResolveInfo> mShareAppList;
  private List<ResolveInfo> mDisplayAppList = null;
  public ResolveInfo mShareInfo = null;
  private AlertDialog mDialog;
  private Context mContext;
  private String mItemUri;

  private static String DLNA_PACKAGE_NAME = "com.techiedb.app.dlna";
  public static int ACTION_SEND = 1;
  public static int ACTION_SEND_MULTIPLE = 2;
  public static int ACTION_UPLOAD_CONTENT = 3;
  public static int ACTION_DOWNLOAD_CONTENT = 4;
  public int mBucketId = 0;
  public static boolean mDialogShowing = false;

  private static float mSystemFontSize;

  private static final float HUGE_FONT_SIZE = 29.0f;
  private static final float LARGE_FONT_SIZE = 22.0f;
  private static final float NORMAL_FONT_SIZE = 20.0f;
  private static final float SMALL_FONT_SIZE = 18.0f;
  private static final float TINY_FONT_SIZE = 16.0f;

  private final OnDismissListener mDismissListener = new OnDismissListener() {
    @Override
    public void onDismiss(DialogInterface dialog) {
      setDialogVisibility(false);
      mDialog = null;
    }
  };

  private final OnClickListener mShareListener = new OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
      ResolveInfo displayedInfo = mDisplayAppList.get(which);
      List<ResolveInfo> shareInfoList = new ArrayList<ResolveInfo>();
      for (ResolveInfo info : mShareAppList) {
        if (info.activityInfo.name.equals(displayedInfo.activityInfo.name)
            && info.activityInfo.packageName.equals(displayedInfo.activityInfo.packageName)) {
          shareInfoList.add(info);
        }
      }
      mShareInfo = shareInfoList.get(0);

      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_SEND);
      intent.setType("text/plain");
      intent.setComponent(new ComponentName(mShareInfo.activityInfo.packageName,
                                            mShareInfo.activityInfo.name));
      intent.putExtra(Intent.EXTRA_TEXT, mItemUri);
      mContext.startActivity(intent);
      setDialogVisibility(false);
    }
  };

  public ShareViaDialog(Context context, String uri) {
    super(context);
    mContext = context;
    mItemUri = uri;
    setContentView(R.layout.send_list_dialog_body);
    showPopup();
  }

  public void showPopup() {
    if (mDialog != null) {
      if (!getDialogVisibility()) {
        setDialogVisibility(true);
      }
      return;
    }

    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
    ResolveInfo resolveInfo;
    PackageManager pm = mContext.getPackageManager();
    ArrayList<SendAppItem> arrayList = new ArrayList<SendAppItem>();
    dialogBuilder.setTitle(R.string.popup_share_item_title);
    for (int i = 0; i < mDisplayAppList.size(); i++) {
      resolveInfo = mDisplayAppList.get(i);
      arrayList.add(new SendAppItem(resolveInfo.loadIcon(pm), (String) resolveInfo.loadLabel(pm)));
    }
    SendAppListAdapter sendAppListAdapter = new SendAppListAdapter(mContext, arrayList);
    dialogBuilder.setAdapter(sendAppListAdapter, mShareListener);
    mDialog = dialogBuilder.create();
    mDialog.setCanceledOnTouchOutside(true);
    mDialog.setOnDismissListener(mDismissListener);
    setDialogVisibility(true);
  }

  public boolean getDialogVisibility() {
    return mDialogShowing;
  }

  public void setDialogVisibility(boolean visibility) {
    try {
      if (visibility) {
        mDialogShowing = true;
        if (mDialog != null) {
          mDialog.show();
        }
      } else {
        mDialogShowing = false;
        if (mDialog != null) {
          mDialog.hide();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      dismiss();
    }
  }

  private void setSendIntentList() {
    String action = Intent.ACTION_SEND;
    addList(getResolveInfo(action, "text/plain"));
    setDisplayList();
  }

  private void addList(List<ResolveInfo> listResolveInfo) {
    if (mShareAppList == null) {
      mShareAppList = listResolveInfo;
      for (int i = 0; i < mShareAppList.size(); i++) {
        Log.d(TAG, String.format("addList(): activityName: %s "
                                 + listResolveInfo.get(i).activityInfo.name));
        Log.d(TAG, String.format("addList(): packageName: %s"
                                 + listResolveInfo.get(i).activityInfo.packageName));
      }
      return;
    }
  }

  private List<ResolveInfo> getResolveInfo(String action, String dataType) {
    PackageManager packageManager = mContext.getPackageManager();
    Intent intent = new Intent(action);
    intent.setType(dataType);
    intent.addCategory(Intent.CATEGORY_DEFAULT);
    return (packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY |
                                                         PackageManager.GET_RESOLVED_FILTER
                                                         | PackageManager.GET_INTENT_FILTERS));
  }

  private void setDisplayList() {
    mDisplayAppList = new ArrayList<ResolveInfo>();
    for (int i = 0; i < mShareAppList.size(); i++) {
      if (!isExistedApp(mDisplayAppList, mShareAppList.get(i))) {
        Log.d(TAG, String.format(
            "setDisplayList(): packageName: %s" + mShareAppList.get(i).activityInfo.packageName));
        Log.d(TAG, String.format(
            "setDisplayList(): activieyInfo: %s " + mShareAppList.get(i).activityInfo.name));

        if (!mShareAppList.get(i).activityInfo.packageName.equals(DLNA_PACKAGE_NAME)) {
          mDisplayAppList.add(mShareAppList.get(i));
        }
      }
    }
    Collections
        .sort(mDisplayAppList, new ResolveInfo.DisplayNameComparator(mContext.getPackageManager()));
  }

  private boolean isExistedApp(List<ResolveInfo> list, ResolveInfo info) {
    for (ResolveInfo resolveInfo : list) {
      if (resolveInfo.activityInfo.name.equals(info.activityInfo.name)) {
        return true;
      }
    }
    return false;
  }

  private class SendAppListAdapter extends ArrayAdapter<SendAppItem> {

    public SendAppListAdapter(Context context, ArrayList<SendAppItem> appItems) {
      super(context, 0, appItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View view = convertView;
      if (view == null) {
        LayoutInflater inflater = (LayoutInflater) mContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.send_list_dialog_row_item, null);
      }
      SendAppItem item = getItem(position);
      if (item != null) {
        ImageView appIcon = (ImageView) view.findViewById(R.id.appicon);
        TextView appName = (TextView) view.findViewById(R.id.apptext);

        if (appIcon != null) {
          appIcon.setImageDrawable(item.getAppIcon());
        }
        if (appName != null) {
          appName.setText(item.getAppName());
          appName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mSystemFontSize);
        }
      }
      return view;
    }
  }

  class SendAppItem {

    private Drawable mAppIcon;
    private String mAppName;

    public SendAppItem(Drawable appIcon, String appName) {
      this.mAppIcon = appIcon;
      this.mAppName = appName;
    }

    public Drawable getAppIcon() {
      return this.mAppIcon;
    }

    public String getAppName() {
      return this.mAppName;
    }
  }

  public void dismiss() {
    if (mDialog != null) {
      mDialog.dismiss();
    }
    super.dismiss();
  }

  public void hide() {
    if (mDialog != null && mDialog.isShowing()) {
      mDialog.hide();
    }
  }
}
