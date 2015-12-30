package com.wagner.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.wagner.android.sampleapp.R;
import rx.Subscriber;
import rx.Subscription;
//import rx.android.observables.AndroidObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * The RXJava example implementation of an activity.
 * @author Stephan Wagner
 */
public class MainActivity extends Activity
{

    /**
     * The Tag to identify the logging of this class.
     */
    private static final String TAG = "MainActivity";


    /**
     * The first observer output view.
     */
    private TextView firstObserverOutput;

    /**
     * The second observer output view.
     */
    private TextView secondObserverOutput;

    /**
     * The first Subscription.
     */
    private Subscription firstSubscription;

    /**
     * The second Subscription.
     */
    private Subscription secondSubscription;

    /**
     * The Constructor
     */
    public MainActivity() {
        Log.d(TAG, "call constructor");
    }

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this
     *                           Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           <b>Note: Otherwise it is
     *                           null.</b>
     */
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        Log.d(TAG, "ACTIVITY JUST CREATED");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //create view with different fields
        //create button for starting with each field

        firstObserverOutput = (TextView) findViewById(R.id.firstObserverOutput);
        firstObserverOutput.setText("This is the output of first observer:\n");

        secondObserverOutput = (TextView) findViewById(R.id.secondObserverOutput);
        secondObserverOutput.setText("This is the output of second observer:\n");

    }

    /**
     * If Activity will be destroyed unSubscribe from the observers
     * to prevent memory leaks and terminate the processing in the
     * observables.
     */
    @Override
    public void onDestroy()
    {
        if (firstSubscription != null)
        {
            firstSubscription.unsubscribe();
        }

        if (secondSubscription != null)
        {
            secondSubscription.unsubscribe();
        }
    }

    /**
     * Initialize the subscription to the view that called this method.
     * @param aView the view that called this method.
     */
    public void initSubscription(final View aView)
    {

        if (aView.getId() == R.id.startSubscription1) {

            Subscriber<String> firstSubscriber = getFirstSubscriber();

            firstSubscription = getSubscription(firstSubscriber);

        }
        if (aView.getId() == R.id.startSubscription2) {

            Subscriber<String> secondSubscriber = getSecondSubscriber();

            secondSubscription = getSubscription(secondSubscriber);

        }
    }

    /**
     * Returns a subscription that specifies the Observable of RandomPrimeNumGenerator,
     * a given subscriber and the Schedules. The observable will run on a new Thread
     * and the subscriber will be processed on the main Thread - the ui-Thread of
     * this activity.
     * @param aSubscriber that will subscribe from the Observable of a new
     *                    RandomPrimeNumGenerator instance.
     * @return a Subscription that contains the observable and subscriber.
     */
    private Subscription getSubscription(final Subscriber<String> aSubscriber)
    {
        RandomPrimeNumGenerator randomPrimeNumGenerator = new RandomPrimeNumGenerator();

        return randomPrimeNumGenerator
                .getFilteredObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(aSubscriber);
    }

    /**
     * Defines the firstSubscriber with view specific Callback
     * implementations.
     * @return the firstSubscriber for the first output panel
     */
    private Subscriber<String> getFirstSubscriber() {

        return new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                //Actualize the view + setting value
                String lastOutput = firstObserverOutput.getText().toString();
                firstObserverOutput.setText(lastOutput + " \n " + s);
            firstObserverOutput.invalidate();
            }

            @Override
            public void onCompleted() {
                //show in view that observer is ready
            }

            @Override
            public void onError(Throwable e) {
                //show in view that observer ran in an error
            }
        };
    }


    /**
     * Defines the secondSubscriber with view specific Callback
     * implementations.
     * @return the secondSubscriber for the first output panel
     */
    private Subscriber<String> getSecondSubscriber() {

        return new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                //Actualize the view + setting value
                String lastOutput = secondObserverOutput.getText().toString();
                secondObserverOutput.setText(lastOutput + " \n " + s);

                secondObserverOutput.invalidate();
            }

            @Override
            public void onCompleted() {
                //show in view that observer is ready
            }

            @Override
            public void onError(Throwable e) {
                //show in view that observer ran in an error
            }
        };
    }

    /**
     * Clearing the output panel to the view that called
     * this method.
     * @param aView the view that called this method.
     */
    public void clearOutput(View aView)
    {
        if (aView.getId() == R.id.clearOutput1)
        {
            firstObserverOutput.setText("");
            firstObserverOutput.invalidate();
        }
        if (aView.getId() == R.id.clearOutput2)
        {
            secondObserverOutput.setText("");
            secondObserverOutput.invalidate();
        }
    }
}