package com.wagner.android;

/**
 *  AsyncTaskResult defines the result of
 *  of the background processing in android.os.AsyncTask.
 *  @author Stephan Wagner
 */
public class AsyncTaskResult<T>
{
    /**
     * The generic type of AsyncTaskResult
     */
    private T result;

    /**
     * The Exception that will be thrown in case of an error.
     */
    private Exception error;


    /**
     * The constructor that defines the result of the AsyncTask processing.
     * @param result of the background processing.
     */
    public AsyncTaskResult(final T result)
    {
        super();
        this.result = result;
    }

    /**
     * The constructor that defines the exception that was thrown
     * during AsyncTask processing.
     * @param error as exception of background processing.
     */
    public AsyncTaskResult(final Exception error)
    {
        super();
        this.error = error;
    }

    /**
     * Returns the result of AsyncTask Processing.
     * @return generic type.
     */
    public T getResult()
    {
        return result;
    }

    /**
     * Returns the exception that was thrown because of an error
     * during background processing in AsyncTask.
     * @return the exception that was thrown during background processing.
     */
    public Exception getError()
    {
        return error;
    }

}