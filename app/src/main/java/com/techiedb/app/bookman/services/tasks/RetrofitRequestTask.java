package com.techiedb.app.bookman.services.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.techiedb.app.bookman.Properties;
import com.techiedb.app.bookman.controllers.ControllerMessage;
import com.techiedb.app.bookman.models.*;
import com.techiedb.app.bookman.services.BaseResult;
import com.techiedb.app.bookman.services.BookResult;
import com.techiedb.app.bookman.services.BooksResult;
import com.techiedb.app.bookman.utils.LogUtils;
import com.techiedb.app.bookman.models.Error;
import android.os.Handler;
import android.os.Message;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class RetrofitRequestTask extends Thread {
    public static final String TAG = Properties.PREFIX + RetrofitRequestTask.class.getSimpleName();

    public static final int PDF_TYPE = 1;
    public static final int UNKNOWN_TYPE = 2;
    public String mAction = null;

    private String mKeyword = null;
    public int mBookId = 0;

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

    public RetrofitRequestTask(String action, int bookId, Handler handler) {
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

    public void setBookId(int bookId) {
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

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public RequestInterceptor getInterceptor() {
        return mInterceptor;
    }

    public void setInterceptor(RequestInterceptor interceptor) {
        mInterceptor = interceptor;
    }

    public void getBooksCollection() {
        final String fn = "[GET] getBookCollections()";
        final BooksResult result = new BooksResult();
        final AppRestApi appRestApi = RestApiUtils.getAppRestApi(getInterceptor(), new GsonConverter(mGson));
        final Callback<BooksResult> callback = new Callback<BooksResult>() {

            @Override
            public void success(BooksResult booksResult, Response response) {
                if (booksResult != null && booksResult.getJsonBooks() != null) {
                    final List<JsonBookItem> bookItemList = booksResult.getJsonBooks().getJsonBookList();
                    result.setJsonBooks(booksResult.getJsonBooks());
                    if (bookItemList != null && bookItemList.size() > 0) {
                        LogUtils.LOGD(TAG, String.format("BookList's Size: %d", bookItemList.size()));
                    }
                }
                result.setResultCode(response.getStatus());
                result.setResultMessage(response.getReason());
                LogUtils.LOGD(TAG, String.format("%s REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD_COMPLETED %s", fn, result));
                sendMessage(ControllerMessage.REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD_COMPLETED, mRequestOwner, mRequestMessage, result);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                final Error error = new Error();
                LogUtils.LOGD(TAG, String.format("Response: %s", retrofitError.getResponse()));

                final int errorCode = retrofitError.getResponse().getStatus();
                final String errorMessage = retrofitError.getMessage();
                final String errorException = retrofitError.getResponse().getReason();

                error.setErrorCode(errorCode);
                error.setErrorMsg(errorMessage);
                error.setErrorException(errorException);
                LogUtils.LOGD(TAG, String.format("%s REQUEST_TO_SERVER_ERROR --%d,%s", fn, errorCode, errorMessage));
                sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
            }
        };
        appRestApi.getBooksByQuery(mKeyword, callback);
    }

    public void getBookRequestedBookDetail() {
        final String fn = "[GET] getBookDetail()";
        final BookResult result = new BookResult();
        final AppRestApi appRestApi = RestApiUtils.getAppRestApi();
        final Callback<BookResult> callback = new Callback<BookResult>() {
            @Override
            public void success(BookResult bookResult, Response response) {
                if (bookResult != null && bookResult.getBook() != null) {
                    result.setBook(bookResult.getBook());
                    result.setResultCode(response.getStatus());
                    result.setResultMessage(response.getReason());

                    LogUtils.LOGD(TAG, String.format("%s REQUEST_TO_SERVER_GET_BOOK_DETAIL %s", fn, result));
                    sendMessage(ControllerMessage.REQUEST_TO_SERVER_EBOOK_DETAIL_COMPLETED, mRequestOwner, mRequestMessage, result);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                final Error error = new Error();
                LogUtils.LOGD(TAG, String.format("Response: %s", retrofitError.getResponse()));
                final int errorCode = retrofitError.getResponse().getStatus();
                final String errorMessage = retrofitError.getMessage();
                final String errorException = retrofitError.getResponse().getReason();

                error.setErrorCode(errorCode);
                error.setErrorMsg(errorMessage);
                error.setErrorException(errorException);

                LogUtils.LOGD(TAG, String.format("%s REQUEST_TO_SERVER_ERROR --- %d,%s", fn, errorCode, errorMessage));
                sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
            }
        };
        appRestApi.getBookDetail(mBookId, callback);
    }

    public void getRequestedBookCollectionByPaging() {
        final String fn = "[GET] getBookCollectionsByPaginatedQuery() ";
        final BooksResult result = new BooksResult();
        final AppRestApi appRestApi = RestApiUtils.getAppRestApi();
        final Callback<BooksResult> callback = new Callback<BooksResult>() {
            @Override
            public void success(BooksResult booksResult, Response response) {
                if (booksResult != null && booksResult.getJsonBooks() != null) {
                    result.setJsonBooks(booksResult.getJsonBooks());
                    final List<JsonBookItem> bookItems = booksResult.getJsonBooks().getJsonBookList();
                    if (bookItems != null && bookItems.size() > 0) {
                        LogUtils.LOGD(TAG, String.format("REQUEST_TO_SERVER_GET_BOOKS_BY_PAGINATED_QUERY - BookList's Size: %d",
                                bookItems.size()));
                    }
                    result.setResultCode(response.getStatus());
                    result.setResultMessage(response.getReason());
                    LogUtils.LOGD(TAG, String.format("%s REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD_PAGING_COMPLETED %s", fn, result));
                    sendMessage(ControllerMessage.REQUEST_TO_SERVER_EBOOK_LIST_BY_KEYWORD_PAGING_COMPLETED, mRequestOwner, mRequestMessage, result);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                final Error error = new Error();
                LogUtils.LOGD(TAG, String.format("Response: %s", retrofitError.getResponse()));

                final int errorCode = retrofitError.getResponse().getStatus();
                final String errorMessage = retrofitError.getMessage();
                final String errorException = retrofitError.getResponse().getReason();

                error.setErrorCode(errorCode);
                error.setErrorMsg(errorMessage);
                error.setErrorException(errorException);
                LogUtils.LOGD(TAG, String.format("%s REQUEST_TO_SERVER_ERROR --%d,%s", fn, errorCode, errorMessage));
                sendMessage(ControllerMessage.REQUEST_TO_SERVER_ERROR, mRequestOwner, mRequestMessage, error);
            }
        };
        appRestApi.getBooksByQueryPaging(mKeyword, mPageIndex, callback);
    }

    public void sendMessage(final int code, final int requestOwner, final int requestMessage, final Object obj) {
        final String fn = "sendMessage() ";
        final Message msg = new Message();
        LogUtils.LOGD(TAG, fn + String.format("code: %s", ControllerMessage.toString(code)) + "("+ code +")");
        LogUtils.LOGD(TAG, fn + String.format("requestOwner: %d", requestOwner));
        LogUtils.LOGD(TAG, fn + String.format("requestMessage: %s", ControllerMessage.toString(requestMessage)) + "("+ requestMessage +")");
        if (obj instanceof Error) {
            final Error error = (Error) obj;
            int errorCode = error.getErrorCode();
            LogUtils.LOGD(TAG, fn + String.format("errorCode: %s", ControllerMessage.toString(errorCode)) + "(" + errorCode + ")");
        }
        msg.what = code;
        msg.arg1 = requestOwner;
        msg.arg2 = requestMessage;
        msg.obj = obj;

        if (this.isCancelled()) {
            LogUtils.LOGD(TAG, "Task cancelled. Don't send any message!");
        } else {
            mServiceHandler.sendMessage(msg);
        }
    }

}
