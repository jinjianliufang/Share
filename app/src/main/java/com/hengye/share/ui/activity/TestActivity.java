package com.hengye.share.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;

import com.hengye.share.ui.activity.web.WebViewActivity;
import com.hengye.share.ui.base.BaseActivity;
import com.hengye.share.R;
import com.hengye.share.ui.fragment.TestTabLayoutFragment;
import com.hengye.share.ui.widget.dialog.ListDialog;
import com.hengye.share.ui.widget.dialog.LoadingDialog;
import com.hengye.share.ui.widget.dialog.SimpleTwoBtnDialog;
import com.hengye.share.ui.widget.loading.FramesLoadingView;
import com.hengye.share.util.L;
import com.hengye.share.util.UserUtil;
import com.hengye.share.util.intercept.Action;
import com.hengye.share.util.intercept.AdTokenInterceptor;
import com.hengye.share.util.intercept.Interception;
import com.hengye.share.util.intercept.Interceptor;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.exceptions.OnErrorThrowable;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class TestActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected String getRequestTag() {
        return "TestActivity";
    }

    private static final String TAG = "RxAndroidSamples";

    private Handler backgroundHandler;
    private FramesLoadingView mLoading;
    private LoadingDialog mLoadingDialog;
    private ListDialog mListDialog;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
        findViewById(R.id.btn_test3).setOnClickListener(this);
        findViewById(R.id.btn_test4).setOnClickListener(this);
        findViewById(R.id.btn_test5).setOnClickListener(this);
        findViewById(R.id.btn_test6).setOnClickListener(this);
        findViewById(R.id.btn_test7).setOnClickListener(this);
        mLoading = (FramesLoadingView) findViewById(R.id.loading);
        mLoading.stop();
        mListDialog = new ListDialog(this, new ArrayList<ListDialog.KeyValue>());

        BackgroundThread backgroundThread = new BackgroundThread();
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_test) {
            onRunSchedulerExampleButtonClicked();
        } else if (v.getId() == R.id.btn_test2) {
            onRunSchedulerExampleButtonClicked2();
        } else if (v.getId() == R.id.btn_test3) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this, "正在加载中...", false);
            }
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            } else {
                mLoadingDialog.show();
            }
        } else if (v.getId() == R.id.btn_test4) {
            if (mLoading.isRunning()) {
                mLoading.stop();
            } else {
                mLoading.start();
            }
        } else if (v.getId() == R.id.btn_test5) {
            mListDialog.show();
        } else if (v.getId() == R.id.btn_test6) {
//            testInterceptor();
            startActivity(FragmentActivity.getStartIntent(this, TestTabLayoutFragment.class));
//            startActivity(WebViewActivity.getStartIntent(this, "http://www.baidu.com"));
        } else if (v.getId() == R.id.btn_test7) {
            startActivity(SetTokenActivity.class);
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
                .subscribeOn(HandlerScheduler.from(backgroundHandler))
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
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
                .subscribeOn(Schedulers.computation())
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

    private Dialog mLoginDialog;
//    private Interceptor mInterceptor;

    public Dialog getLoginDialog() {
        if (mLoginDialog == null) {
            SimpleTwoBtnDialog build = new SimpleTwoBtnDialog();
            build.setContent("需要高级授权, 是否继续?");
            build.setPositiveButtonClickListener(new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(ThirdPartyLoginActivity.getAdTokenStartIntent(TestActivity.this), 3);
                }
            });
            mLoginDialog = build.create(this);
        }
        return mLoginDialog;
    }

    AdTokenInterceptor mAdTokenInterceptor;

    public void testInterceptor() {
        if (mAdTokenInterceptor == null) {
            mAdTokenInterceptor = new AdTokenInterceptor(this, new Action() {
                @Override
                public void run() {
                    startActivity(TopicPublishActivity.getStartIntent(TestActivity.this, "test"));
                }
            });
        }
        mAdTokenInterceptor.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


























