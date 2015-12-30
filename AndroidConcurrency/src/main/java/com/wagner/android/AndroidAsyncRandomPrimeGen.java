package com.wagner.android;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import com.wagner.android.sampleapp.R;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 *  AndroidAsyncRandomPrimeGen is a concrete
 *  implementation of the android.os.AsyncTask.
 *  @author Stephan Wagner
 */
public class AndroidAsyncRandomPrimeGen
        extends AsyncTask<Integer, String, AsyncTaskResult<Map<Integer, String>>>
{
    /**
     * Tag to identify the logging of this class.
     */
    private final static String TAG = "AndroidAsyncRandomPrimeGen";

    /**
     * The TextView that will be used as first output
     * panel.
     */
    private final TextView firstOutputView;

    /**
     * The TextView that will be used as second output
     * panel.
     */
    private final TextView secondOutputView;

    /**
     * The constructor that specifies the outputViews for the
     * presentation of the calculation result.
     * @param aFirstOutputView specifies the first output panel.
     * @param aSecondOutputView specifies the second output panel.
     */
    AndroidAsyncRandomPrimeGen(final TextView aFirstOutputView,
                               final TextView aSecondOutputView)
    {
        firstOutputView = aFirstOutputView;
        secondOutputView = aSecondOutputView;
    }

    /**
     * This method defines the logic that will run in the background
     * Thread. To build the right relation between the result of the
     * calculation and the output panel, you need set the triggered
     * view id (the id of the button in the view). This method will
     * relate the calculation result to the related OutputView.
     * @param params the triggered view id.
     * @return an Instance of AsyncTaskResult<String>.
     */
    @Override
    protected AsyncTaskResult<Map<Integer, String>> doInBackground(final Integer... params)
    {

        if (params == null || params.length != 1)
        {
            return new AsyncTaskResult<Map<Integer,String>>(
                    new IllegalArgumentException("Not the rights params:" + params));
        }
        final int triggerViewId = params[0].intValue();

        publishProgress("Init Calculation");
        StringBuilder targetString = new StringBuilder(10);

        for (int i = 0; i < 10; i++)
        {
            BigInteger veryBig = new BigInteger(1500, new Random());
            BigInteger randomPrimeNumber = veryBig.nextProbablePrime();
            int summe = 0;
            while (0 != randomPrimeNumber.compareTo(BigInteger.ZERO))
            {
                // addiere die letzte ziffer der uebergebenen zahl zur summe
                summe = summe + (randomPrimeNumber.mod(BigInteger.TEN)).intValue();
                // entferne die letzte ziffer der uebergebenen zahl
                randomPrimeNumber = randomPrimeNumber.divide(BigInteger.TEN);
            }
            targetString.append(summe + " \n ");
        }
        Map<Integer,String> resultMap = new HashMap<Integer, String>(1);
        resultMap.put(triggerViewId, targetString.toString());
        return new AsyncTaskResult<Map<Integer,String>>(resultMap);
    }

    /**
     * Callback that will be called before the background thread will be created
     * and starts processing.
     */
    protected void onPreExecute()
    {
        // Perform setup - runs on user interface thread
    }

    /**
     * The callbackFunction that will be used to process state
     * messages that are produced during the background calculation.
     * This method will run on the Main Thread.
     * @param processUpdateMsg Messages for processUpdate.
     */
    protected void onProgressUpdate(final String processUpdateMsg)
    {
        // Update user with progress bar or similar
        // - runs on user interface thread
        Log.i(TAG, processUpdateMsg);
    }

    /**
     * The callbackFunction that will be used to process errors in
     * the background calculation. This Method will run on the Main
     * Thread.
     * @param result the AsyncTaskResult as Map of ViewId and ResultString
     */
    @Override
    protected void onPostExecute(AsyncTaskResult<Map<Integer, String>> result)
    {
        if (result.getError() != null)
        {
            // error handling here
        }
        else if (isCancelled())
        {
            // cancel handling here
        }
        else
        {
            //update user interface
            Iterator<Map.Entry<Integer,String>> resultMap = result.getResult().entrySet().iterator();

            if ( resultMap.hasNext() )
            {
                final Map.Entry<Integer,String> entry  = resultMap.next();
                final int triggerViewId = entry.getKey();
                final String resultText = entry.getValue();

                if( triggerViewId == R.id.startCalculation1)
                {
                    firstOutputView.setText(resultText);
                    firstOutputView.invalidate();
                }

                if (triggerViewId == R.id.startCalculation2)
                {
                    secondOutputView.setText(resultText);
                    secondOutputView.invalidate();
                }
            }
        }
    }
}


