package com.techiedb.app.bookman;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved.
 * Mobile UX Promotion Division.
 * This software and its documentation are confidential and proprietary
 * information of Techie Digital Benchwork Inc.  No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Techie Digital Benchwork.
 * Techie Digital Benchwork makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 * History
 *
 * @author Larry Pham
 * @project BookMan
 * @since Dec.18.2014
 */
public class AnimationHelper {
    public static final int ANIMATION_UNSPECIFIED = -1;
    public static final int ANIMATION_LISTVIEW_SORT_TO_START = 0;
    public static final int ANIMATION_LISTVIEW_SORT_TO_END = 1;
    private Rotate3dAnimation mRotate3DAnimation = null;
    private AnimationSet mAnimationSet = null;

    private ScaleAnimation mScaleAnim = null;
    private AlphaAnimation mAlphaAnim = null;
    private final int ROTATE_ANIMATION_DELAYED = 50;

    private int mAnimType = ANIMATION_UNSPECIFIED;

    public AnimationHelper(){

    }

    public AnimationHelper(int animType){
        this.mAnimType = animType;
    }

    public static void overridePendingTransition(Context context,int enterAnim, int exitAnim){
        if (context == null){
            return;
        }
        ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * Initialize animation, instantiate actual animation object
     * @param animatedView used for calculating position & pivot value
     * @param duration The animation's duration
     */
    public void initAnimation(View animatedView, int duration){
        float pivotX = 0;
        float pivotY = 0;
        float centerX = 0;
        float centerY = 0;
        switch (mAnimType){
            case ANIMATION_LISTVIEW_SORT_TO_START:{
                float ROTATE_ANIMATION_START_DEGREE = 360f;
                float ROTATE_ANIMATION_MIDDLE_DEGREE = 270f;
                mRotate3DAnimation = new Rotate3dAnimation(ROTATE_ANIMATION_START_DEGREE
                        , ROTATE_ANIMATION_MIDDLE_DEGREE, animatedView.getWidth() / 2.0f
                        , animatedView.getHeight() / 2.0f, 0f, true);
                mRotate3DAnimation.setDuration(duration);
                mRotate3DAnimation.setFillAfter(true);
                break;
            }
            case ANIMATION_LISTVIEW_SORT_TO_END:{
                centerX = 0;
                centerY = 0;
                if (animatedView != null){
                    centerX = animatedView.getWidth() / 2.0f;
                    centerY = animatedView.getHeight() / 2.0f;
                }
                float ROTATE_ANIMATION_MIDDLE_DEGREE2 = 90f;
                float ROTATE_ANIMATION_END_DEGREE = 0f;
                mRotate3DAnimation = new Rotate3dAnimation(ROTATE_ANIMATION_MIDDLE_DEGREE2,
                        ROTATE_ANIMATION_END_DEGREE, centerX, centerY, 0f, true);
                mRotate3DAnimation.setDuration(duration);
                mRotate3DAnimation.setFillAfter(true);
                mRotate3DAnimation.setInterpolator(new DecelerateInterpolator());
                break;
            }
            default:
                break;

        }
    }

    public Object getAnimation(){
        Object anim = null;
        switch (mAnimType){
            case ANIMATION_LISTVIEW_SORT_TO_START:
            case ANIMATION_LISTVIEW_SORT_TO_END:
                anim = mRotate3DAnimation;
                break;
            default:
                break;
        }
        return anim;
    }
}
