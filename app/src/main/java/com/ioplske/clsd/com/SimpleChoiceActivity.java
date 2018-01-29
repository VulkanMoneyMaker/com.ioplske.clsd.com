package com.ioplske.clsd.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class SimpleChoiceActivity extends AppCompatActivity {
    private static final String TAG = SimpleChoiceActivity.class.getSimpleName();

    /** Handle to default shared preferences for this activity. */
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_choice);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(SimpleChoiceActivity.this);

        findViewById(R.id.btn_choice_play).setOnClickListener(__ -> {
            openScreenGameActivity();
        });
        toggleHideyBar();

        findViewById(R.id.btn_choice_feedback).setOnClickListener(__ -> {
            openFeedBackActivity();
        });
    }

    public void toggleHideyBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        // END_INCLUDE (get_current_ui_flags)
        // BEGIN_INCLUDE (toggle_ui_flags)
        boolean isImmersiveModeEnabled =
                false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            isImmersiveModeEnabled = ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        }
        if (isImmersiveModeEnabled) {
            Log.i(TAG, "Turning immersive mode mode off. ");
        } else {
            Log.i(TAG, "Turning immersive mode mode on.");
        }


        if (Build.VERSION.SDK_INT >= 16) {
            newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
    }

    private void openScreenGameActivity() {
        Log.d(TAG, "openScreenMainActivity");
        startActivity(ForGameActivity.getGameActivityIntent(this));
        finish();
    }

    private void openFeedBackActivity() {
        Log.d(TAG, "openFeedBackActivity");
        startActivity(ForFeedbackActivity.getFeedbackActivityIntent(this));
    }

    @NonNull
    public static Intent getChoiceActivityIntent(Context context) {
        return new Intent(context, SimpleChoiceActivity.class);
    }
}
