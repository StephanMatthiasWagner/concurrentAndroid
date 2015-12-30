package com.wagner.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.wagner.android.sampleapp.R;

import java.math.BigInteger;
import java.util.Random;

/**
 * The Activity that will block if the time consuming
 * operation is started.
 *
 * @author Stephan Wagner
 */
public class MainActivity extends Activity {

    /**
     * The tag for identify this class action in the logging.
     */
    private static final String TAG = "MainActivity";

    /**
     * The Constructor
     */
    public MainActivity()
    {
        Log.d(TAG, "call constructor");
    }

    /**
     * The textView of the first panel.
     */
    private TextView firstCalculationOutput;

    /**
     * The textView of the second panel.
     */
    private TextView secondCalculationOutput;

    /**
     * Called when the activity is first created.
     * This method initialises the layout.
     * @param savedInstanceState ignored in this example
     */
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        Log.d(TAG, "ACTIVITY JUST CREATED");
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        //create view with different fields
        //create button for starting with each field

        firstCalculationOutput =
                (TextView) findViewById(R.id.firstObserverOutput);
        firstCalculationOutput
                .setText("This is the output of first calculation:\n");

        secondCalculationOutput =
                (TextView) findViewById(R.id.secondObserverOutput);
        secondCalculationOutput.
                setText("This is the output of second calculation:\n");
    }

    /**
     *  Called when this activity will be destroyed.
     */
    @Override
    public void onDestroy()
    {
        //for later usage in the other examples.
    }

    /**
     * Starts the time consuming calculation that
     * will calculate large prime numbers.
     * @param aView the view that triggers this method.
     */
    public void startCalculation(final View aView)
    {
        StringBuilder targetString = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {

            BigInteger veryBig = new BigInteger(1500, new Random());
            BigInteger randomPrimeNumber = veryBig.nextProbablePrime();
            int summe = 0;
            while (0 != randomPrimeNumber.compareTo(BigInteger.ZERO)) {
                // addiere die letzte ziffer der uebergebenen zahl zur summe
                summe = summe + (randomPrimeNumber.mod(BigInteger.TEN)).intValue();
                // entferne die letzte ziffer der uebergebenen zahl
                randomPrimeNumber = randomPrimeNumber.divide(BigInteger.TEN);
            }
            targetString.append(summe + " \n ");
        }
        if (aView.getId() == R.id.startFirstCalculation_btn) {
            firstCalculationOutput.setText(targetString.toString());
            firstCalculationOutput.invalidate();
        }
        if (aView.getId() == R.id.startSecondCalculation_btn) {
            secondCalculationOutput.setText(targetString.toString());
            secondCalculationOutput.invalidate();

        }
    }

    /**
     * Clearing the output panels for new calculations.
     * @param aView the view to identify the panel
     *              that should be cleared.
     */
    public void clearOutput(final View aView)
    {
        if (aView.getId() == R.id.clearOutput1) {
            firstCalculationOutput.setText("");
            firstCalculationOutput.invalidate();
        }
        if (aView.getId() == R.id.clearOutput2) {
            secondCalculationOutput.setText("");
            secondCalculationOutput.invalidate();
        }
    }
}