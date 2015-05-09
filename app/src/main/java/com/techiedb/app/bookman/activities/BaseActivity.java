package com.techiedb.app.bookman.activities;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.techiedb.app.bookman.R;
import com.techiedb.app.bookman.controllers.BaseController;
import com.techiedb.app.bookman.models.Image;
import com.techiedb.app.bookman.utils.LogUtils;
import com.techiedb.app.bookman.utils.PreferenceUtils;
import com.techiedb.app.bookman.widgets.ScrimInsetsScrollView;

import java.util.ArrayList;

/**
 * Copyright (C) 2014 Techie Digital Benchwork Inc. All rights reserved. Mobile UX Promotion Division. This software and its documentation
 * are confidential and proprietary information of Techie Digital Benchwork Inc.  No part of the software and documents may be copied,
 * reproduced, transmitted, translated, or reduced to any electronic medium or machine-readable form without the prior written consent of
 * Techie Digital Benchwork. Techie Digital Benchwork makes no representations with respect to the contents, and assumes no responsibility
 * for any errors that might appear in the software and documents. This publication and the contents hereof are subject to change without
 * notice. History
 *
 * @author Larry Pham
 * @since 2014.10.02
 *
 * A BaseActivity that handles common functionality in the app. This includes the navigation drawer, login and authentication,
 * ActionBar Tweaks, amongst others.
 */
public abstract class BaseActivity extends ActionBarActivity {

  public static final String TAG = LogUtils.makeLogTag(BaseActivity.class);
  // Navigation Drawer
  private DrawerLayout mDrawerLayout;
  private ObjectAnimator mStatusBarColorAnimator;
  private LinearLayout mAccountListContainer;
  private ViewGroup mDrawerItemListContainer;
  private Handler mUIHandler;

  private ImageView mExpandAccountBoxIndicator;
  private boolean mAccountBoxExpanded = false;

  // When se, these components will shown/hidden in sync with the action bar to implement the "quick recall" effect
  // (The ActionBar and the header views disappear when you scroll down a list, and reappear quickly when you scroll up).

  private ArrayList<View> mHideableHeaderViews = new ArrayList<View>();
  // Durations for certain animations we use
  private static final int HEADER_HIDE_ANIM_DURATION = 300;
  private static final int ACCOUNT_BOX_EXPAND_ANIM_DURATION = 200;
  // Symbols for navdrawer items (indices must correspond to array below). This is not a list of items that are necessarily *present* in
  // the Nav Drawer; rather it's a list of all possible items.

  protected static final int NAVDRAWER_ITEM_BOOK_COLLECTIONS = 0;
  protected static final int NAVDRAWER_ITEM_BOOK_RECENTS = 1;
  protected static final int NAVDRAWER_ITEM_BOOK_FAVOURITE = 2;
  protected static final int NAVDRAWER_ITEM_BOOK_DOWNLOADS = 3;
  protected static final int NAVDRAWER_ITEM_BOOK_SETTINGS = 4;
  protected static final int NAVDRAWER_ITEM_BOOK_INVALID = -1;
  protected static final int NAVDRAWER_ITEM_BOOK_SEPARATOR = -2;
  protected static final int NAVDRAWER_ITEM_BOOK_SEPARATOR_SPECIFAL = -3;

  // titles for navdrawer items ( indices msut correspond to the above)
  private static final int[] NAVDRAWER_TITLE_RES_ID = new int[] {
      R.string.nav_drawer_item_collections,
      R.string.nav_drawer_item_history,
      R.string.nav_drawer_item_favourites,
      R.string.nav_drawer_item_downloads,
      R.string.nav_drawer_item_settings
  };

  private static final int[] NAV_DRAWER_ICON_RES_ID = new int[] {
      R.drawable.nav_drawer_shelf,
      R.drawable.nav_drawer_history,
      R.drawable.nav_drawer_favourites,
      R.drawable.nav_drawer_downloads,
      R.drawable.nav_drawer_settings
  };

  // delay to launch nav drawer item, to allow close animation to play
  private static final int NAVDRAWER_LAUNCH_DELAY = 250;
  // fade in and fade out durations for the main content when switching between
  // different Activities of the app through the nav drawer
  private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;
  private static final int MAIN_CONTENT_FADEIN_DURATION = 250;

  // list of navdrawer items that were actually added to the navdrawer, in order
  private ArrayList<Integer> mNavDrawerItems = new ArrayList<Integer>();

  // Views that correspond to each navdrawer item, null if not yet created
  private View[] mNavDrawerItemViews = null;
  // Primary Toolbar and drawer toggle
  private Toolbar mActionBarToolBar;
  private boolean mActionBarAutoHideEnabled = false;

  private int mActionBarAutoHideMinY = 0;
  private int mActionBarAutoHideSensivity = 0;
  private int mActionBarAutoHideSignal = 0;
  private boolean mActionBarShown = true;

  // A Runnable that we should execute when the navigation drawer finishes its closing animation
  private Runnable mDeferredDrawerClosedRunnable;
  private int mThemedStatusBarColor;
  private int mNormalStatusBarColor;
  private int mProgressBarTopWhenActionBarShown;
  private static final TypeEvaluator ARGB_EVALUATOR = new ArgbEvaluator();

  protected BaseController controller = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mUIHandler = new Handler();

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    mThemedStatusBarColor = getResources().getColor(R.color.theme_primary_dark);
    mNormalStatusBarColor = mThemedStatusBarColor;
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  /**
   * Returns the navigation bar item that correspond to this Activity. Subclasses of BaseActivity overrides this to indicate what nav
   * drawer item corresponds to them
   * @return  NAVDRAWER_ITEM_INVALID to mean that this Activity should not have a Nav Drawer.
   */
  protected int getSelfNavDrawerItem() {
    return NAVDRAWER_ITEM_BOOK_INVALID;
  }

  /**
   * Sets up the navigation drawer as appropriate. Note that the nav drawer will be different depending on whether the attendee indicated
   * they are attending the event on-site vs . attending remotely.
   */
  private void setupNavDrawer() {
    // What nav drawer item should be selected?
    int selfItem = getSelfNavDrawerItem();

    mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
    if (mDrawerLayout == null) {
      return;
    }

    mDrawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.theme_primary_dark));
    ScrimInsetsScrollView navDrawer = (ScrimInsetsScrollView) mDrawerLayout.findViewById(R.id.nav_drawer);
    if (selfItem == NAVDRAWER_ITEM_BOOK_INVALID) {
      // do not show a nav drawer
      if (navDrawer != null) {
        ((ViewGroup) navDrawer.getParent()).removeView(navDrawer);
      }
      mDrawerLayout = null;
      return;
    }

    if (navDrawer != null) {
      final View chosenAccountContentView = findViewById(R.id.chosen_account_content_view);
      final View chosenAccountView = findViewById(R.id.chosen_account_view);
      final int navDrawerChosenAccountHeight = getResources().getDimensionPixelSize(R.dimen.nav_drawer_chosen_account_height);
      navDrawer.setOnInsetsCallback(new ScrimInsetsScrollView.OnInsetsCallback() {
        @Override
        public void onInsetsChanged(Rect insets) {
          ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) chosenAccountContentView.getLayoutParams();
          layoutParams.topMargin = insets.top;
          chosenAccountContentView.setLayoutParams(layoutParams);
          ViewGroup.LayoutParams layoutParams2 = chosenAccountView.getLayoutParams();
          layoutParams2.height = navDrawerChosenAccountHeight + insets.top;
          chosenAccountView.setLayoutParams(layoutParams2);
        }
      });
    }

    if (mActionBarToolBar != null) {
      mActionBarToolBar.setNavigationIcon(R.drawable.ic_drawer);
      mActionBarToolBar.setNavigationOnClickListener( new View.OnClickListener(){

        @Override
        public void onClick(View v) {
          mDrawerLayout.openDrawer(Gravity.START);
        }
      });
    }

    mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
      @Override
      public void onDrawerSlide(View drawerView, float slideOffset) {
        onNavDrawerSlide(slideOffset);
      }

      @Override
      public void onDrawerOpened(View drawerView) {
        onNavDrawerStateChanged(true, false);
      }

      @Override
      public void onDrawerClosed(View drawerView) {
        // Run deferred action, if we have one
        if (mDeferredDrawerClosedRunnable != null) {
          mDeferredDrawerClosedRunnable.run();
          mDeferredDrawerClosedRunnable = null;
        }

        if (mAccountBoxExpanded) {
          mAccountBoxExpanded = false;
          //setupAccountBoxToggle();
        }
        onNavDrawerStateChanged(false, false);
      }

      @Override
      public void onDrawerStateChanged(int newState) {
        onNavDrawerStateChanged(isNavDrawerOpen(), newState != DrawerLayout.STATE_IDLE);
      }
    });
    // When the user runs the app for the first time, we want to land them with the
    // navigation drawer open, But just the first time.
    if (!PreferenceUtils.isWelcomeDone(this)) {
      // first run of the app starts with the nav drawer open
      PreferenceUtils.markWelcomeDone(this);
      mDrawerLayout.openDrawer(Gravity.START);
    }
  }

  @Override
  public void setContentView(int layoutResID) {
    super.setContentView(layoutResID);
    getActionBarToolBar();
  }

  protected void onNavDrawerSlide(float offset) {

  }

  protected void closeNavDrawer() {
    if (mDrawerLayout != null) {
      mDrawerLayout.closeDrawer(Gravity.START);
    }
  }

  protected boolean isNavDrawerOpen() {
    return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START);
  }

  // Subclasses can override this for custom behavior
  protected void onNavDrawerStateChanged(boolean isOpened, boolean isAnimating) {
    if (mActionBarAutoHideEnabled && isOpened) {
      //autoShowOrHideActionBar(true);
    }
  }

  protected Toolbar getActionBarToolBar() {
    if (mActionBarToolBar != null) {
      mActionBarToolBar = (Toolbar) findViewById(R.id.toolbar_actionbar);
      if (mActionBarToolBar != null) {
        setSupportActionBar(getActionBarToolBar());
      }
    }
    return mActionBarToolBar;
  }

  
  public abstract void invalidate();

  public abstract void invalidate(Object param);

  @Override
  protected void onDestroy() {
    if (controller != null) {
      controller.onDestroy();
    }
    super.onDestroy();
  }

  protected String getTopActivity() {
    ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    ComponentName topActivity = am.getRunningTasks(1).get(0).topActivity;
    Log.d(TAG, String.format("TopActivity: %s, ClassName: %s", topActivity.getPackageName(),
                             topActivity.getClassName()));
    return topActivity.getClassName();
  }

  /**
   * Method getDefaultBookImage, used to choose the default book's image cover for books item into Gallery or List of books
   */

  public Image getBookCoverDefaultImage(String key) {
    return null;
  }
}
