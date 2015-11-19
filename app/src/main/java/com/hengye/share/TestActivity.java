package com.hengye.share;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func0;
import rx.functions.Func1;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class TestActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected String getRequestTag() {
        return "TestActivity";
    }

    private static final String TAG = "RxAndroidSamples";

    private Handler backgroundHandler;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);

        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_test) {
            onRunSchedulerExampleButtonClicked();
        }if(v.getId() == R.id.btn_test2) {
            onRunSchedulerExampleButtonClicked2();
        }
    }

    void onRunSchedulerExampleButtonClicked2() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hi");
            }
        }).flatMap(new Func1<String, Observable<Float>>() {
            @Override
            public Observable<Float> call(String integer) {
                return Observable.just(1.0f, 2.0f, 3.0f);
            }
        })
//                // Run on a background thread
//                .subscribeOn(HandlerScheduler.from(backgroundHandler))
//                        // Be notified on the main thread
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Float>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override
                    public void onNext(Float number) {
                        Log.d(TAG, "onNext(" + number + ")");
                    }
                });
    }

    void onRunSchedulerExampleButtonClicked() {
        sampleObservable()
                // Run on a background thread
                .subscribeOn(HandlerScheduler.from(backgroundHandler))
                        // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError()", e);
                    }

                    @Override
                    public void onNext(String string) {
                        Log.d(TAG, "onNext(" + string + ")");
                    }
                });
    }

    static Observable<String> sampleObservable() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                try {
                    // Do some long running operation
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));
                } catch (InterruptedException e) {
                    throw OnErrorThrowable.from(e);
                }
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    static class BackgroundThread extends HandlerThread {
        BackgroundThread() {
            super("SchedulerSample-BackgroundThread", THREAD_PRIORITY_BACKGROUND);
        }
    }
}
