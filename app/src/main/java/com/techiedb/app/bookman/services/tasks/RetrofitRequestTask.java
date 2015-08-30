package com.techiedb.app.bookman.services.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.services.BooksResult;

import android.os.Handler;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RequestInterceptor;

public class RetrofitRequestTask extends Thread {
    public static final String TAG = Properties.PREFIX + RetrofitRequestTask.class.getSimpleName();

    public static final int PDF_TYPE = 1;
    public static final int UNKNOWN_TYPE = 2;
    public String mAction = null;

    private String mKeyword = null;
    private long mBookId = 0;

    public RequestType mRequestType = RequestType.NONE;
    private int mRequestOwner = 0;
    private int mRequestMessage = 0;
    private boolean mCancelled = false;
    private Handler mServiceHandler;

    private int mMaxSize = Properties.MAX_SIZE_INIT_VALUE;
    private int mPageSize = Properties.PAGE_SIZE_INIT_VALUE;
    private int mPageIndex = Properties.PAGE_INDEX_INIT_VALUE;

    public Gson mGson;
    public RequestInterceptor mInterceptor;
    public Callback mCallback;
    public String mUrl;

    public enum RequestType {
        NONE, BOOK_LIST, BOOK_DETAIL, BOOK_LIST_PAGING
    }

    public RetrofitRequestTask() {

    }

    public RetrofitRequestTask(String action, Handler handler) {
        this.mAction = action;
        this.mServiceHandler = handler;
        this.mGson = new GsonBuilder()
                        .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                        .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                        .create();
    }

    public RetrofitRequestTask(String action, long bookId, Handler handler) {
        this.mServiceHandler = handler;
        this.mAction = action;
        this.mBookId = bookId;
    }

    public void run() {
        final String fn = "[RetrofitRequestTask] execute " ;
        switch (mRequestType) {
            case BOOK_LIST:
                getBooksCollection();
                break;
            case BOOK_DETAIL:
                getBookRequestedBookDetail();
                break;
            case BOOK_LIST_PAGING:
                getRequestedBookCollectionByPaging();
                break;
            default:
                break;
        }
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public String getKeyword() {
        return mKeyword;
    }

    public void setKeyword(String keyword) {
        mKeyword = keyword;
    }

    public long getBookId() {
        return mBookId;
    }

    public void setBookId(long bookId) {
        mBookId = bookId;
    }

    public RequestType getRequestType() {
        return mRequestType;
    }

    public void setRequestType(RequestType requestType) {
        mRequestType = requestType;
    }

    public int getRequestOwner() {
        return mRequestOwner;
    }

    public void setRequestOwner(int requestOwner) {
        mRequestOwner = requestOwner;
    }

    public int getRequestMessage() {
        return mRequestMessage;
    }

    public void setRequestMessage(int requestMessage) {
        mRequestMessage = requestMessage;
    }

    public boolean isCancelled() {
        return mCancelled;
    }

    public void setCancelled(boolean cancelled) {
        mCancelled = cancelled;
    }

    public Handler getServiceHandler() {
        return mServiceHandler;
    }

    public void setServiceHandler(Handler serviceHandler) {
        mServiceHandler = serviceHandler;
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

    public Callback getCallback() {
        return mCallback;
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void getBooksCollection() {
        final String fn = "[GET] getBookCollections()";
        final BooksResult result = new BooksResult();

    }

    public void getBookRequestedBookDetail() {

    }

    public void getRequestedBookCollectionByPaging() {

    }
}
