package com.ioplske.clsd.com;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ioplske.clsd.com.models.Airline;
import com.ioplske.clsd.com.other.src.need.wheel.widget.OnWheelScrollListener;
import com.ioplske.clsd.com.other.src.need.wheel.widget.WheelView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class ForGameActivity extends AppCompatActivity implements OnWheelScrollListener {
    private static final String TAG = ForGameActivity.class.getSimpleName();

    private static final int INIT_CREDIT = 1200;

    private ArrayList<Integer> integers;

    private ArrayList<Boolean> mstatewheels; // false - not finished, true - finished

    private TextView tvTotal;
    private TextView tvBet;

    private static final String PREF_SPINNER_POS = "spinner_pos";
    /** Magic constant to indicate that no value is stored for PREF_SPINNER_POS. */
    private static final int PREF_SPINNER_VALUE_ISNULL = -1;


    private TextView tvCredit;

    private AlertDialog mDialogWin;

    private int mCurrentTotal;
    private int mCurrentCredit;
    private int mCurrentBet;

    private ArrayList<WheelView> views;

    private boolean mSyncItems;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_game);

        tvTotal = findViewById(R.id.total_text_view);
        tvBet = findViewById(R.id.bet_text_view);
        tvCredit = findViewById(R.id.credit_text_view);

        if (savedInstanceState == null) {
            Airline airline = new Airline();
            if (airline.isPartner("hello")) {
                Log.i(TAG, "Yes - " + PREF_SPINNER_VALUE_ISNULL);
            }
        }

        views = new ArrayList<>();
        mstatewheels = new ArrayList<>();
        TypedArray wheels = getResources().obtainTypedArray(R.array.wheel_items);
        for (int i = 0; i < wheels.length(); ++i) {
            WheelView view = findViewById(wheels.getResourceId(i, 0));
            views.add(view);
            mstatewheels.add(false);
        }
        wheels.recycle();
        initSlots();

        Button playButton = findViewById(R.id.btn_play);
        playButton.setOnClickListener(__ -> {
            mSyncItems = false;
            updateStates();
            random();
        });

        Button increaseTotalBet = findViewById(R.id.increase_total_button);
        increaseTotalBet.setOnClickListener(__ -> {
            int temp = mCurrentTotal + 1;
            if (temp <= mCurrentCredit) { // Нельзя поставить больше чем у тебя есть
                mCurrentTotal = temp;
            }
            updateViewData();
        });

        Button decreaseTotal = findViewById(R.id.decrease_total_button);
        decreaseTotal.setOnClickListener(__ -> {
            int temp = mCurrentTotal - 1;
            if (temp >= 1) {
                mCurrentTotal = temp; // Нельзя уменьшить меньше единицы
            }
            updateViewData();
        });

        initViewData();
        updateViewData();
    }

    @Override
    public void onStart() {
        super.onStart();
        mDialogWin = initDialogWin();
        for (WheelView view : views) {
            view.addScrollingListener(this);
        }
    }

    @Override
    public void onStop() {
        if (mDialogWin != null) mDialogWin.dismiss();
        mDialogWin = null;
        for (WheelView view : views) {
            view.removeScrollingListener(this);
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        mDialogWin = null;
        super.onDestroy();
    }

    @Override
    public void onScrollingStarted(WheelView wheel) {
        Log.d(TAG, "onScrollingStarted - " + String.valueOf(wheel.getCurrentItem()));
    }

    @Override
    public void onScrollingFinished(WheelView wheel) {
        Log.d(TAG, "onScrollingFinished - " + String.valueOf(wheel.getCurrentItem()));

        mstatewheels.set(views.indexOf(wheel), true);
        if (checkAllWheelsFinished() && !mSyncItems) {
            generateWin();
        }
    }

    @NonNull
    public static Intent getGameActivityIntent(Context context) {
        return new Intent(context, ForGameActivity.class);
    }



    private void initViewData() {
        mCurrentCredit = INIT_CREDIT;
        mCurrentTotal = 1;
        mCurrentBet = 1;
    }

    private void updateViewData() {
        tvTotal.setText(String.format(Locale.getDefault(),"%d", mCurrentTotal));
        tvCredit.setText(String.format(Locale.getDefault(),"%d", mCurrentCredit));
        tvBet.setText(String.format(Locale.getDefault(),"%d", mCurrentBet));
    }

    private void increaseCredit(boolean isSuperWin) {
        if (isSuperWin) { // Если джек-пот
            mCurrentCredit += mCurrentCredit * 0.3;
        } else {
            mCurrentCredit += mCurrentTotal;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void decreaseCredit() {
        mCurrentCredit -= mCurrentTotal;
        if (mCurrentCredit < 0) {
            mCurrentCredit = 0;
        }
    }

    private void initSlots() {
        for (WheelView view : views) {
            iniWheel(view, images());
        }
    }

    private ArrayList<Integer> images(){
        if (integers == null) {
            final ArrayList<Integer> list = new ArrayList<>();
            TypedArray images = getResources().obtainTypedArray(R.array.slots_images);
            for (int i = 0; i < images.length(); ++i) {
                list.add(images.getResourceId(i, 0));
            }
            images.recycle();
            this.integers = list;
        }
        return integers;
    }

    private void iniWheel(WheelView wheelView, ArrayList<Integer> list) {
        wheelView.setViewAdapter(new SimpleGameAdapter(this, list));
        wheelView.setCurrentItem((int) (Math.random() * 10.0d));
        wheelView.setVisibleItems(4);
        wheelView.setCyclic(true);
        wheelView.setEnabled(false);
        wheelView.addScrollingListener(this);
    }

    private void random() {
        Random random = new Random();
        for (WheelView view : views) {
            view.scroll(((int) ((Math.random() * ((double) random.nextInt(30))) + 20.0d)) - 350,
                    random.nextInt(3000) + 2000);
        }
    }

    @SuppressLint("InflateParams")
    private AlertDialog initDialogWin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.item_dialog_win, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

    private void showDialogWin() {
        if (mDialogWin != null) {
            if (!mDialogWin.isShowing()) {
                mDialogWin.show();
            }
        }
    }

    private void generateWin() {
        mSyncItems = true;
        Random random = new Random();
        int value = random.nextInt(10 + 1); // [1;10]
        Log.d(TAG, "Random win value - " + value);
        if (value % 2 == 0) { // Если четное то победил
            if (value == 4 || value == 8) { // Если джек-пот
                showDialogWin();
                increaseCredit(true);
            } else {
                increaseCredit(false);
            }
        } else {
            decreaseCredit();
        }
        updateViewData();
    }

    private void updateStates() {
        for (int i = 0; i < mstatewheels.size(); ++i){
            mstatewheels.set(i, false);
        }
    }

    private boolean checkAllWheelsFinished() {
        return !mstatewheels.contains(false);
    }
}
