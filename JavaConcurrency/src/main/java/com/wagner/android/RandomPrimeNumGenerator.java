package com.wagner.android;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.math.BigInteger;
import java.util.Random;

/**
 * RandomPrimeNumGenerator that implements a Runnable that will
 * run in a Background Thread.
 * @author Stephan Wagner
 */
public class RandomPrimeNumGenerator implements Runnable {

    /**
     * Tag for identify the log messages of this runnable.
     */
    private static final String TAG = "RandomPrimeNumGenerator";


    /**
     *  A targetView that will that assigns the output of
     *  the calculation to the view that will display it.
     */
    private View targetView;

    /**
     *  The Handler instance that helps to realise the communication
     *  between the background thread and the UI-Thread.
     */
    private Handler handler;

    /**
     * The constructor.
     * @param aView as targetView.
     * @param aCallbackHandler as MessageHandler.
     */
    public RandomPrimeNumGenerator(final View aView, final Handler aCallbackHandler)
    {
        Log.d(TAG,"Call Constructor");
        targetView = aView;
        handler = aCallbackHandler;
    }

    /**
     * This method will be called by new thread.
     */
    @Override
    public void run()
    {
        Log.d(TAG,"Call run");
        String result = startCalculation();
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putCharArray(String.valueOf(targetView.getId()), result.toCharArray());
        message.setData(bundle);
        Log.d(TAG,"Call handler");
        handler.sendMessage(message);
    }


    /**
     * The time consuming calculation. This method tries
     * to find a number of big prime numbers.
     * @return result of the calculation as string
     */
    private String startCalculation() {
        Log.d(TAG," startCalculation");

        StringBuilder targetString = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {

            Log.d(TAG," calculation iteration: "+i);
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
        return targetString.toString();
    }

}
