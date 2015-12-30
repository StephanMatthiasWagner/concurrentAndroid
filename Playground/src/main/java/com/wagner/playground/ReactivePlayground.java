package com.wagner.playground;

import rx.*;
import rx.schedulers.Schedulers;

import java.math.BigInteger;
import java.util.Random;

/**
 * First playground class to test subscription and concurrency with rxJava.
 * @author Stephan Wagner
 */
public class ReactivePlayground
{

    static Observable<String> myObservable = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> sub) {
                    for (int i = 0; i < 10; i++) {
                        sub.onNext("ObserverThread: Hello, world!" + i);


                        BigInteger veryBig = new BigInteger(500, new Random());
                        veryBig.nextProbablePrime();

                    }
                    sub.onCompleted();
                }
            }
    );


    static Subscriber<String> mySubscriber = new Subscriber<String>() {


        @Override
        public void onNext(final String s)
        {
            System.out.println("1rst Thread itemProcessing:");
            System.out.println(s);
        }

        @Override
        public void onCompleted()
        {
            firstObservableHasFinished=true;
            System.out.println("1rst item Completed");
        }


        @Override
        public void onError(Throwable e)
        {
            System.out.println("There was an error on first subscriber:" + e);
        }
    };

    static Subscriber<String> mySndSubscriber = new Subscriber<String>()
    {
        @Override
        public void onNext(String s) {
            System.out.println("                                2ndThread: itemProcessing:");
            System.out.println("                                " + s);
        }

        @Override
        public void onCompleted() {
            secondObservableHasFinished = true;
            System.out.println("                                2nd Thred: item Completed");
        }


        @Override
        public void onError(Throwable e) {
        }
    };

    /**
     * First observable has finished flag.
     */
    static boolean firstObservableHasFinished = false;

    /**
     * second observable has finished flag.
     */
    public static boolean secondObservableHasFinished = false;

    /**
     * The main for launching the test impl.
     * @param args ignored.
     */
    public static void main(String[] args) {

        final Subscription subscription = myObservable.subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(mySubscriber);

        myObservable.subscribeOn(Schedulers.newThread())
                .subscribe(mySndSubscriber);

        while(!firstObservableHasFinished || !secondObservableHasFinished)
        {
            //wait for termination loop.
        }

        System.out.println("firstObservableHasFinished:"+firstObservableHasFinished);
        System.out.println("secondObservableHasFinished:"+secondObservableHasFinished);

   }

}
