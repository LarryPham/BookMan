package com.techiedb.app.bookman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder.Callback;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
public class BackgroundView extends SurfaceView implements Callback{

    public static final String TAG = Properties.PREFIX + BackgroundView.class.getSimpleName();
    private Context context;
    SurfaceHolder surfaceHolder;
    private BackgroundDrawThread thread;

    public BackgroundView(Context context) {
        super(context);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        initVariables(holder,context);
    }

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        initVariables(holder, context);
    }

    public BackgroundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        initVariables(holder, context);
    }

    private void initVariables(SurfaceHolder holder, Context context){
        this.surfaceHolder = holder;
        this.context = context;
        this.thread = new BackgroundDrawThread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        this.thread.interrupt();
    }

    class BackgroundDrawThread extends Thread{
        Bitmap backgroundImage;

        public BackgroundDrawThread(){
            backgroundImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.abc_cab_background_top_material);
        }

        @SuppressWarnings("SynchronizeOnNonFinalField")
        public void run(){
            Canvas canvas = null;
            while (!Thread.interrupted()){
                canvas = surfaceHolder.lockCanvas();
                try {
                    synchronized (surfaceHolder){
                        canvas.drawBitmap(backgroundImage, 0, 0, null);
                    }
                }finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
