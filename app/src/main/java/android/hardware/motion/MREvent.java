package android.hardware.motion;

import android.os.Parcel;
import android.os.Parcelable;

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
public class MREvent implements Parcelable {

  public static final int NONE = 0;

  public static final int TWO_TAPPING = 1;
  public static final int SNAP_X_POSITIVE = 2;    // snap
  public static final int SNAP_X_NEGATIVE = 3;
  public static final int SNAP_Y_POSITIVE = 4;
  public static final int SNAP_Y_NEGATIVE = 5;
  public static final int SNAP_Z_POSITIVE = 6;
  public static final int SNAP_Z_NEGATIVE = 7;
  public static final int SPEAKER_PHONE_ON = 8;    // motion sequence
  public static final int SPEAKER_PHONE_OFF = 9;
  public static final int FLIP_TOP_TO_BOTTOM = 10;
  public static final int TILT_PORTRAIT_FRONT = 11;    // tilt
  public static final int TILT_PORTRAIT_BACK = 12;
  public static final int TILT_PORTRAIT_FRONT_BACK_STOP = 13;
  public static final int TILT_PORTRAIT_LEFT = 14;
  public static final int TILT_PORTRAIT_RIGHT = 15;
  public static final int TILT_PORTRAIT_LEFT_RIGHT_STOP = 16;
  public static final int TILT_LANDSCAPE_LEFT_LEVEL_1 = 17;
  public static final int TILT_LANDSCAPE_LEFT_LEVEL_2 = 18;
  public static final int TILT_LANDSCAPE_RIGHT_LEVEL_1 = 19;
  public static final int TILT_LANDSCAPE_RIGHT_LEVEL_2 = 20;
  public static final int TILT_LANDSCAPE_LEFT_RIGHT_STOP = 21;
  public static final int TILT_FRONT = 22;
  public static final int TILT_BACK = 23;
  public static final int TILT_FRONT_BACK_END = 24;
  public static final int TILT_LEFT = 25;
  public static final int TILT_RIGHT = 26;
  public static final int TILT_LEFT_RIGHT_END = 27;
  public static final int ROTATE_0 = 28;    // rotate
  public static final int ROTATE_90 = 29;
  public static final int ROTATE_180 = 30;
  public static final int ROTATE_270 = 31;
  public static final int ROTATE_START = 32;
  public static final int ROTATE_STOP = 33;
  //		public static final int CHECK_ATTITUDE_ANGLE             = ;      //#if defined(MM_MRE_USE_CHECK_ATTITUDE_ANGLE_MOTION_EVENT)
  public static final int SHAKE = 34;    // shake
  public static final int SHAKE_START = 35;
  public static final int SHAKE_STOP = 36;
  public static final int SHORT_SHAKE = 37;    // short shake (100316, Lismore)
  public static final int SHORT_SHAKE_START = 38;
  public static final int SHORT_SHAKE_STOP = 39;
  public static final int BT_SHARING_RECEIVE_READY = 40;    // BT sharing Event
  public static final int BT_SHARING_RECEIVE_NOT_READY = 41;
  public static final int BT_SHARING_SEND_START = 42;
  public static final int BT_SHARING_SEND_PAUSE = 43;
  public static final int BT_SHARING_SEND_STOP = 44;
  public static final int ROTATE_HORIZONTAL = 45;    // rotate additional information
  public static final int BOUNCE = 46;    // bounce
  public static final int SNAP1_X_POSITIVE = 47;    // snap1
  public static final int SNAP1_X_NEGATIVE = 48;
  public static final int SNAP1_Y_POSITIVE = 49;
  public static final int SNAP1_Y_NEGATIVE = 50;
  public static final int SNAP1_Z_POSITIVE = 51;
  public static final int SNAP1_Z_NEGATIVE = 52;
  public static final int SNAP2_X_POSITIVE = 53;    // snap2
  public static final int SNAP2_X_NEGATIVE = 54;
  public static final int SNAP2_Y_POSITIVE = 55;
  public static final int SNAP2_Y_NEGATIVE = 56;
  public static final int SNAP2_Z_POSITIVE = 57;
  public static final int SNAP2_Z_NEGATIVE = 58;
  public static final int SNAP_LEFT = 59;    // snap for the photobook
  public static final int SNAP_RIGHT = 60;
  public static final int PANNING_GYRO = 61;    // panning gyro
  public static final int ONE_TAPPING_GYRO = 62;    // tapping tipping
  public static final int TWO_TAPPING_GYRO = 63;
  public static final int ONE_TIPPING_GYRO = 64;
  public static final int TWO_TIPPING_GYRO = 65;
  public static final int BLOW = 66;
  public static final int TILT = 67;
  public static final int MAX = 68;

  private int motion;
  private int panningDx;
  private int panningDy;
  private int tilt;

  public MREvent() {
    motion = NONE;
    panningDx = 0;
    panningDy = 0;
  }

  public MREvent(Parcel src) {
    readFromParcel(src);
  }

  public int getMotion() {
    return motion;
  }

  public void setMotion(int m) {
    motion = NONE;
    if (m >= NONE && m <= MAX) {
      motion = m;
    }
  }

  public void setPanningDx(int dx) {
    panningDx = dx;
  }

  public void setPanningDy(int dy) {
    panningDy = dy;
  }

  public void setTilt(int t) {
    tilt = t;
  }

  public int getPanningDx() {
    return panningDx;
  }

  public int getPanningDy() {
    return panningDy;
  }

  public int getTilt() {
    return tilt;
  }

  public String toString() {
    String string = Integer.toString(motion) + "=";

    switch (motion) {
      case NONE:
        string += "NONE";
        break;
      case TWO_TAPPING:
        string += "TWO_TAPPING";
        break;
      case SNAP_X_POSITIVE:
        string += "SNAP_X_POSITIVE";
        break;
      case SNAP_X_NEGATIVE:
        string += "SNAP_X_NEGATIVE";
        break;
      case SNAP_Y_POSITIVE:
        string += "SNAP_Y_POSITIVE";
        break;
      case SNAP_Y_NEGATIVE:
        string += "SNAP_Y_NEGATIVE";
        break;
      case SNAP_Z_POSITIVE:
        string += "SNAP_Z_POSITIVE";
        break;
      case SNAP_Z_NEGATIVE:
        string += "SNAP_Z_NEGATIVE";
        break;
      case SPEAKER_PHONE_ON:
        string += "SPEAKER_PHONE_ON";
        break;
      case SPEAKER_PHONE_OFF:
        string += "SPEAKER_PHONE_OFF";
        break;
      case FLIP_TOP_TO_BOTTOM:
        string += "FLIP_TOP_TO_BOTTOM";
        break;
      case TILT_PORTRAIT_FRONT:
        string += "TILT_PORTRAIT_FRONT";
        break;
      case TILT_PORTRAIT_BACK:
        string += "TILT_PORTRAIT_BACK";
        break;
      case TILT_PORTRAIT_FRONT_BACK_STOP:
        string += "TILT_PORTRAIT_FRONT_BACK_STOP";
        break;
      case TILT_PORTRAIT_LEFT:
        string += "TILT_PORTRAIT_LEFT";
        break;
      case TILT_PORTRAIT_RIGHT:
        string += "TILT_PORTRAIT_RIGHT";
        break;
      case TILT_PORTRAIT_LEFT_RIGHT_STOP:
        string += "TILT_PORTRAIT_LEFT_RIGHT_STOP";
        break;
      case TILT_LANDSCAPE_LEFT_LEVEL_1:
        string += "TILT_LANDSCAPE_LEFT_LEVEL_1";
        break;
      case TILT_LANDSCAPE_LEFT_LEVEL_2:
        string += "TILT_LANDSCAPE_LEFT_LEVEL_2";
        break;
      case TILT_LANDSCAPE_RIGHT_LEVEL_1:
        string += "TILT_LANDSCAPE_RIGHT_LEVEL_1";
        break;
      case TILT_LANDSCAPE_RIGHT_LEVEL_2:
        string += "TILT_LANDSCAPE_RIGHT_LEVEL_2";
        break;
      case TILT_LANDSCAPE_LEFT_RIGHT_STOP:
        string += "TILT_LANDSCAPE_LEFT_RIGHT_STOP";
        break;
      case TILT_FRONT:
        string += "TILT_FRONT";
        break;
      case TILT_BACK:
        string += "TILT_BACK";
        break;
      case TILT_FRONT_BACK_END:
        string += "TILT_FRONT_BACK_END";
        break;
      case TILT_LEFT:
        string += "TILT_LEFT";
        break;
      case TILT_RIGHT:
        string += "TILT_RIGHT";
        break;
      case TILT_LEFT_RIGHT_END:
        string += "TILT_LEFT_RIGHT_END";
        break;
      case ROTATE_0:
        string += "ROTATE_0";
        break;
      case ROTATE_90:
        string += "ROTATE_90";
        break;
      case ROTATE_180:
        string += "ROTATE_180";
        break;
      case ROTATE_270:
        string += "ROTATE_270";
        break;
      case ROTATE_START:
        string += "ROTATE_START";
        break;
      case ROTATE_STOP:
        string += "ROTATE_STOP";
        break;
      case SHAKE:
        string += "SHAKE";
        break;
      case SHAKE_START:
        string += "SHAKE_START";
        break;
      case SHAKE_STOP:
        string += "SHAKE_STOP";
        break;
      case SHORT_SHAKE:
        string += "SHORT_SHAKE";
        break;
      case SHORT_SHAKE_START:
        string += "SHORT_SHAKE_START";
        break;
      case SHORT_SHAKE_STOP:
        string += "SHORT_SHAKE_STOP";
        break;
      case BT_SHARING_RECEIVE_READY:
        string += "BT_SHARING_RECEIVE_READY";
        break;
      case BT_SHARING_RECEIVE_NOT_READY:
        string += "BT_SHARING_RECEIVE_NOT_READY";
        break;
      case BT_SHARING_SEND_START:
        string += "BT_SHARING_SEND_START";
        break;
      case BT_SHARING_SEND_PAUSE:
        string += "BT_SHARING_SEND_PAUSE";
        break;
      case BT_SHARING_SEND_STOP:
        string += "BT_SHARING_SEND_STOP";
        break;
      case ROTATE_HORIZONTAL:
        string += "ROTATE_HORIZONTAL";
        break;
      case BOUNCE:
        string += "BOUNCE";
        break;
      case SNAP1_X_POSITIVE:
        string += "SNAP1_X_POSITIVE";
        break;
      case SNAP1_X_NEGATIVE:
        string += "SNAP1_X_NEGATIVE";
        break;
      case SNAP1_Y_POSITIVE:
        string += "SNAP1_Y_POSITIVE";
        break;
      case SNAP1_Y_NEGATIVE:
        string += "SNAP1_Y_NEGATIVE";
        break;
      case SNAP1_Z_POSITIVE:
        string += "SNAP1_Z_POSITIVE";
        break;
      case SNAP1_Z_NEGATIVE:
        string += "SNAP1_Z_NEGATIVE";
        break;
      case SNAP2_X_POSITIVE:
        string += "SNAP2_X_POSITIVE";
        break;
      case SNAP2_X_NEGATIVE:
        string += "SNAP2_X_NEGATIVE";
        break;
      case SNAP2_Y_POSITIVE:
        string += "SNAP2_Y_POSITIVE";
        break;
      case SNAP2_Y_NEGATIVE:
        string += "SNAP2_Y_NEGATIVE";
        break;
      case SNAP2_Z_POSITIVE:
        string += "SNAP2_Z_POSITIVE";
        break;
      case SNAP2_Z_NEGATIVE:
        string += "SNAP2_Z_NEGATIVE";
        break;
      case SNAP_LEFT:
        string += "SNAP_LEFT";
        break;
      case SNAP_RIGHT:
        string += "SNAP_RIGHT";
        break;
      case PANNING_GYRO:
        string += "PANNING_GYRO";
        break;
      case ONE_TAPPING_GYRO:
        string += "ONE_TAPPING_GYRO";
        break;
      case TWO_TAPPING_GYRO:
        string += "TWO_TAPPING_GYRO";
        break;
      case ONE_TIPPING_GYRO:
        string += "ONE_TIPPING_GYRO";
        break;
      case TWO_TIPPING_GYRO:
        string += "TWO_TIPPING_GYRO";
        break;
      case BLOW:
        string += "BLOW";
        break;
      case TILT:
        string += "TILT";
        break;
      case MAX:
        string += "MAX";
        break;
    }
    string = string.trim();

    if (motion == PANNING_GYRO) {
      string += " (" + panningDx + ", " + panningDy + ")";
    }
    if (motion == TILT) {
      string += " (" + tilt + ")";
    }
    return string;
  }

  public int describeContents() {
    return 0;
  }

  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(motion);
    dest.writeInt(panningDx);
    dest.writeInt(panningDy);
    dest.writeInt(tilt);
  }

  public void readFromParcel(Parcel src) {
    motion = src.readInt();
    panningDx = src.readInt();
    panningDy = src.readInt();
    tilt = src.readInt();
  }

  public static final Parcelable.Creator<MREvent> CREATOR =
      new Parcelable.Creator<MREvent>() {
        public MREvent createFromParcel(Parcel in) {
          return new MREvent(in);
        }

        public MREvent[] newArray(int size) {
          return new MREvent[size];
        }
      };
}
