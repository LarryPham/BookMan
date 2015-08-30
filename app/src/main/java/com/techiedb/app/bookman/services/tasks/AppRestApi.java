package com.techiedb.app.bookman.services.tasks;

import com.techiedb.app.bookman.services.BookResult;
import com.techiedb.app.bookman.services.BooksResult;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface AppRestApi {
    /**
     * Interface method which used to get the book's detail based onto the book's id
     * @param callback an instance of <code>Callback<BookResult></code>, into this callback which the result after requesting from client
     *                 to server for getting the data from server, will be return into the <code>BookResult</code> object into the method
     *                 of successfully request.
     */
    @GET("/book/{id}")
    void getStory(@Path("id") int bookId,
                  Callback<BookResult> callback);
    /**
     * Interface method which used to get the List of Books based onto the specified query string
     * @param callback an instance of <code>Callback<BooksResult></code>, into this callback which the result after requesting from client
     *                 to server for getting the data from server, will be return into the <code>BooksResult</code> object into the method
     *                 of successfully request.
     */
    @GET("/search/{query}")
    void getBooksByQuery(@Path("query") String query,
                         Callback<BooksResult> callback);
    /**
     * Interface method which used to get the List of Books based onto the specified query string and paginated number
     * @param callback an instance of <code>Callback<BooksResult></code>, into this callback which the result after requesting from client
     *                 to server for getting the data from server, will be return into the <code>BooksResult</code> object into the method
     *                 of successfully request.
     */
    @GET("/search/{query}/page/{number}")
    void getBooksByQueryPaging(@Path("query") String query,
                               @Path("number") int numberPaging,
                               Callback<BookResult> callback);
}
