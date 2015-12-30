package com.wagner.android;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

import java.math.BigInteger;
import java.util.Random;

/**
 * This Class defines an Observable of RXJava that will process
 * a time consuming calculation and emmit the results to its
 * subscribers.
 *
 * @author Stephan Wagner
 */
public class RandomPrimeNumGenerator
{

    /**
     *  The Observable that will emit a Strings of its result
     *  to random prime number calculation
     */
    private Observable<String> primNumCalculationObservable;

    /**
     * The Constructor will crate the primNumCalculationObservable.
     */
    public RandomPrimeNumGenerator()
    {

        primNumCalculationObservable = Observable.create(
                new Observable.OnSubscribe<String>()
                {
                    @Override
                    public void call(Subscriber<? super String> sub)
                    {
                        for (int i = 0; i < 10; i++) {

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

                            sub.onNext("Observable emits CrossSum for iteration: " + i);
                            sub.onNext(String.valueOf(summe));

                        }
                        sub.onCompleted();
                    }
                }
        );

    }

    /**
     * Returns the Observable from the constructor but filters its items
     * in an predefined observable.
     *
     * @return Observable<String> instance.
     */
    public Observable<String> getFilteredObservable()
    {
        return primNumCalculationObservable.filter(new Func1<String, Boolean>()
        {
            @Override
            public Boolean call(final String item) {
                try
                {
                    Integer.valueOf(item);
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
                return true;
            }
        }
        );
    }

}
