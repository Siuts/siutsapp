package com.siuts.siutsapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siuts.siutsapplication.R;

public class ListViewItem extends LinearLayout {
    protected TextView birdNameTextView;
    protected TextView altNameTextView;
    protected TextView confidenceTextView;
    protected ImageView image;

    public ListViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, true);
    }

    protected void initialize(Context context, AttributeSet attrs, boolean useDefaultImage) {
        inflateLayout(context);

        birdNameTextView = (TextView) findViewById(R.id.birdNameTextView);
        altNameTextView = (TextView) findViewById(R.id.birdNameAlternativeName);

        image = (ImageView) findViewById(R.id.birdImageView);
        if (!useDefaultImage) {
            image.setImageBitmap(null);
        }
    }

    protected void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.list_view_item, this);
    }
}