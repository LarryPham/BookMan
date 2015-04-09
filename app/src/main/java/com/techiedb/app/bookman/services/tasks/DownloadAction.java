package com.techiedb.app.bookman.services.tasks;

import android.os.Parcel;

import com.techiedb.app.bookman.Properties;

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
 * @since Apr.05.2015
 */
public class DownloadAction extends Action {
  public static final String TAG = Properties.PREFIX + DownloadAction.class.getSimpleName();

  public DownloadAction(Integer id, Params params) {
    super(Action.DOWNLOAD_CONTENT, id, (Object) params);
  }

  public void setParams(Params params) {
    this.mObject = params;
  }

  @Override
  public int describeContents() {
    return super.describeContents();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.mId);
    dest.writeString(((Params) mObject).mFrom);
    dest.writeString(((Params) mObject).mTo);
  }

  public static final Creator<DownloadAction> CREATOR = new Creator<DownloadAction>() {
    @Override
    public DownloadAction createFromParcel(Parcel source) {
      DownloadAction action = new DownloadAction(source.readInt(), null);
      DownloadAction.Params params = new Params(source.readString(), source.readString(), source.readLong());
      action.setParams(params);
      return action;
    }

    @Override
    public DownloadAction[] newArray(int size) {
      return new DownloadAction[size];
    }
  };
  static public class Params {
    public String mFrom;
    public String mTo;
    public long mReDownloadSize;

    public Params(String fromURL, String toURl, long reDownloadSize) {
      this.mFrom = fromURL;
      this.mTo = toURl;
      this.mReDownloadSize = reDownloadSize;
    }
  }
}
