package com.ioplske.clsd.com;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final int TIME_SPLASH_SEC = 3;

    /* Store the click count so that we can show a different text on every click. */
    private int mClickCount = 0;

    /* The {@Link Outline} used to clip the image with. */
    private ViewOutlineProvider mOutlineProvider;

    /* An array of texts. */
    private String[] mSampleTexts;

    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_splash);


        if (mClickCount < -1) {
            Intent intent = new Intent(this, TotalActivity.class);
            startActivity(intent);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mOutlineProvider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    Log.d(TAG, "init");
                }
            };
        }

        mDisposable = Observable.timer(TIME_SPLASH_SEC, TimeUnit.SECONDS)
                .subscribe(__ -> openChoiceActivity(), Throwable::printStackTrace);
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null) mDisposable.dispose();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void openChoiceActivity() {
        Log.d(TAG, "openChoiceActivity");
        mClickCount++;
        startActivity(SimpleChoiceActivity.getChoiceActivityIntent(this));
        finish();
    }
}
