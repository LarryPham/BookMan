package com.techiedb.app.bookman.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.Properties.EnclosureType;
import com.techiedb.app.bookman.Properties.StorageValueState;

import java.util.Date;

/**
 * Copyright (C) 2015.Feb Techie Digital Benchwork Inc., Ltd. All rights reserved.
 *
 * Mobility Development Division, Digital Media & Communications Business, Techie Digital
 * Benchwork., Ltd.
 *
 * This software and its documentation are confidential and proprietary information of Techie
 * Digital Benchwork., Ltd.  No part of the software and documents may be copied, reproduced,
 * transmitted, translated, or reduced to any electronic medium or machine-readable form without the
 * prior written consent of Techie Digital Benchwork Inc.
 *
 * Techie Digital Benchwork makes no representations with respect to the contents, and assumes no
 * responsibility for any errors that might appear in the software and documents. This publication
 * and the contents hereof are subject to change without notice.
 *
 * History 2015.Feb.19     Larry Pham         The 1st Sprint Version
 */
public class Book implements Parcelable {
  public static final String TAG = Properties.PREFIX + Book.class.getSimpleName();

  private long mID = 1l;
  private String mTitle = null;
  private String mSubTitle = null;
  private String mDescription = null;
  private String mAuthor = null;
  private String mISBN;
  private int mYear = 0;
  private int mPage = 0;
  private String mPublisher = null;
  private String mImageURL = null;
  private String mContentLink = null;
  private Image mImage = null;
  private Image mOriginalImage = null;

  private Enclosure mEnclosure = null;
  private Date mDownloadDate = null;
  private StorageValueState mState = StorageValueState.NONE;
  private int mHidden = 0; // delete flag
  private int mHistory = 0;
  private String mCancelDownloadPath = null;

  public Book() {
    this.mImage = new Image();
    this.mOriginalImage = new Image();
    this.mEnclosure = new Enclosure();
  }

  public Book(long id) {
    this.mID = id;
  }

  public Book(String title, String url) {
    this.mTitle = title;
    this.mContentLink = url;
  }

  public Book(long id, String url) {
    this.mID = id;
    this.mContentLink = url;
    this.mImage = new Image();
    this.mOriginalImage = new Image();
  }

  public Book(JsonBook jsonBook) {
    this.mID = jsonBook.getId();
    this.mTitle = jsonBook.getTitle();
    this.mSubTitle = jsonBook.getAuthor();
    this.mContentLink = jsonBook.getDownloadURL();
    this.setImageURL(jsonBook.getDownloadImageURL());
    this.mOriginalImage = new Image();
    this.mYear = Integer.valueOf(jsonBook.getYear());
    this.mPage = Integer.valueOf(jsonBook.getPage());
    this.mAuthor = jsonBook.getAuthor();
    this.mISBN = jsonBook.getISBN();
    this.mDescription = jsonBook.getDescription();
    this.mPublisher = jsonBook.getPublisher();
  }

  public Book(Parcel source) {
    readFromSource(source);
  }

  public long getID() {
    return mID;
  }

  public void setID(long ID) {
    mID = ID;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    if (title != null && (title.trim()).length() != 0) {
      mTitle = title.trim();
    }
  }

  public String getSubTitle() {
    return mSubTitle;
  }

  public void setSubTitle(String subTitle) {
    if (subTitle != null && (subTitle.trim()).length() != 0) {
      mSubTitle = subTitle;
    }
  }

  public String getDescription() {
    return mDescription;
  }

  public void setDescription(String description) {
    mDescription = description;
  }

  public String getAuthor() {
    return mAuthor;
  }

  public void setAuthor(String author) {
    mAuthor = author;
  }

  public String getISBN() {
    return mISBN;
  }

  public void setISBN(String ISBN) {
    mISBN = ISBN;
  }

  public int getYear() {
    return mYear;
  }

  public void setYear(int year) {
    mYear = year;
  }

  public int getPage() {
    return mPage;
  }

  public void setPage(int page) {
    mPage = page;
  }

  public String getPublisher() {
    return mPublisher;
  }

  public void setPublisher(String publisher) {
    mPublisher = publisher;
  }

  public String getImageURL() {
    return mImageURL;
  }

  public void setImageURL(String imageURL) {
    mImageURL = imageURL;
  }

  public String getContentLink() {
    return mContentLink;
  }

  public void setContentLink(String contentLink) {
    if (contentLink != null && (contentLink.trim()).length() != 0) {
      mContentLink = contentLink;
    }
  }

  public void setCancelDownloadPath(String cancelDownloadPath) {
    this.mCancelDownloadPath = cancelDownloadPath;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(this.mID);
    dest.writeString(this.mTitle);
    dest.writeString(this.mSubTitle);
    dest.writeString(this.mDescription);
    dest.writeString(this.mAuthor);
    dest.writeString(this.mISBN);
    dest.writeInt(this.mYear);
    dest.writeInt(this.mPage);
    dest.writeString(this.mPublisher);
    dest.writeString(this.mImageURL);
    dest.writeString(this.mContentLink);
    dest.writeValue(this.mImage);
    dest.writeValue(this.mOriginalImage);
  }

  public static final Creator<Book> CREATOR = new Creator<Book>() {
    @Override
    public Book createFromParcel(Parcel source) {
      return new Book(source);
    }

    @Override
    public Book[] newArray(int size) {
      return new Book[size];
    }
  };

  public static class Enclosure implements Parcelable {
    private int mDBId = 0;
    private String mLocalPath = null;
    private String mTitle = null;
    private String mUrl = null;
    private long mLength = 0;
    private EnclosureType mType = EnclosureType.NONE;

    public Enclosure() {
      this.mDBId = 0;
      this.mUrl = null;
      this.mType = EnclosureType.NONE;
      mLength = 0;
    }

    public Enclosure(Parcel source) {
      readFromParcel(source);
    }

    public Enclosure(EnclosureType type, String url) {
      this.mUrl = url;
      if (mType == null) {
        this.mType = EnclosureType.NONE;
      } else {
        this.mType = type;
      }
    }

    public Enclosure(int dbID, String title, EnclosureType type, String url, int length) {
      this.mDBId = dbID;
      this.mUrl = url;

      if (mType == null) {
        this.mType = EnclosureType.NONE;
      } else {
        this.mType = type;
      }
      this.mTitle = title;
      this.mLength = length;
    }

    public int getDBId() {
      return this.mDBId;
    }

    public void setDBId(int dbId) {
      this.mDBId = dbId;
    }

    public String getUrl() {
      return this.mUrl;
    }

    public void setUrl(String url) {
      this.mUrl = url;
    }

    public EnclosureType getType() {
      return this.mType;
    }

    public void setType(EnclosureType type) {
      this.mType = type;
    }

    public void setType(MediaType type) {
      if (type == MediaType.NONE) {
        this.mType = EnclosureType.NONE;
      } else if (type == MediaType.PDF){
        this.mType = EnclosureType.PDF;
      }
    }

    public void setType(String type) {
      if (type == null || type.length() == 0) {
        return;
      }

      type = type.trim();
      this.mType = EnclosureType.NONE;
      if (type.length() >= 3) {
        type = type.substring(0, 3);
        if (type.equalsIgnoreCase("pdf")) {
          this.mType = EnclosureType.PDF;
        }
      }
    }

    public long getLength() {
      return this.mLength;
    }

    public void setLength(long length) {
      this.mLength = length;
    }

    public void setLength(String length) {
      String fn = "setLength(): ";
      if (length != null && length.length() > 0) {
        try {
          this.mLength = Long.parseLong(length.trim());
        } catch (NumberFormatException ex) {
          Log.e(TAG, fn + String.format("Exception: %s", ex.toString()));
          this.mLength = 0;
        }
      }
    }

    public String getTitle() {
      return mTitle;
    }

    public void setTitle(String title) {
      mTitle = title;
    }

    public String getLocalPath() {
      return mLocalPath;
    }

    public void setLocalPath(String localPath) {
      mLocalPath = localPath;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.mDBId);
      dest.writeString(this.mLocalPath);
      dest.writeString(this.mTitle);
      dest.writeString(this.mUrl);
      dest.writeLong(this.mLength);
      dest.writeString(mType.toString());
    }

    public void readFromParcel(Parcel source) {
      this.mDBId = source.readInt();
      this.mLocalPath = source.readString();
      this.mTitle = source.readString();
      this.mUrl = source.readString();
      this.mLength = source.readLong();
      this.mType = Enum.valueOf(EnclosureType.class, source.readString());
    }

    public static final Creator<Enclosure> CREATOR = new Creator<Enclosure>() {
      @Override
      public Enclosure createFromParcel(Parcel source) {
        return new Enclosure(source);
      }

      @Override
      public Enclosure[] newArray(int size) {
        return new Enclosure[size];
      }
    };
  }

  public static enum MediaType{
    PDF, NONE
  }

  public static enum EnclosureDownloadButtonType {
    NO_DOWNLOAD,
    DOWNLOADING_CONTENT,
    WAITING_DOWNLOAD,
    COMPLETED_DOWNLOAD
  }

  public void readFromSource(Parcel inSource) {
    this.mID = inSource.readLong();
    this.mTitle = inSource.readString();
    this.mSubTitle = inSource.readString();
    this.mDescription = inSource.readString();
    this.mAuthor = inSource.readString();
    this.mYear = inSource.readInt();
    this.mPage = inSource.readInt();
    this.mPublisher = inSource.readString();
    this.mImageURL = inSource.readString();
    this.mContentLink = inSource.readString();

    this.mImage = (Image)inSource.readValue(Image.class.getClassLoader());
    this.mOriginalImage = (Image) inSource.readValue(Image.class.getClassLoader());
    this.mEnclosure = (Enclosure) inSource.readSerializable();
  }
}
