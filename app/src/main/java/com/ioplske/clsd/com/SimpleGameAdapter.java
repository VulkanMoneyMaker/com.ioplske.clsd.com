package com.ioplske.clsd.com;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ioplske.clsd.com.other.src.need.wheel.widget.adapters.AbstractWheelAdapter;

import java.util.ArrayList;


public class SimpleGameAdapter extends AbstractWheelAdapter {
    private LayoutInflater mLayoutInflater;
    private ArrayList<Integer> mListImages;
    private Context mContext;

    @SuppressLint("WrongConstant")
    public SimpleGameAdapter(Context mContext, ArrayList<Integer> list) {
        this.mContext = mContext;
        this.mListImages = list;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService("layout_inflater");
    }

    static final int WEATHER_NOSELECTION = 0;
    static final int WEATHER_SUNNY = 1;
    static final int WEATHER_PARTLY_CLOUDY = 2;
    static final int WEATHER_CLOUDY = 3;
    static final int WEATHER_RAIN = 4;
    static final int WEATHER_SNOW = 5;
    static final int WEATHER_HURRICANE = 6;

    @Override
    public int getItemsCount() {
        return mListImages == null ? 0 : mListImages.size();
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        View view = this.mLayoutInflater.inflate(R.layout.item_list, parent, false);
        ((ImageView) view.findViewById(R.id.image)).setImageDrawable(
                ContextCompat.getDrawable(this.mContext, this.mListImages.get(index))
        );
        return view;
    }
}
