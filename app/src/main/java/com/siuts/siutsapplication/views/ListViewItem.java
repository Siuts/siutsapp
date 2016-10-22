package com.siuts.siutsapplication.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siuts.siutsapplication.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListViewItem extends LinearLayout {
    @BindView(R.id.birdNameTextView) TextView birdNameTextView;
    @BindView(R.id.birdNameAlternativeName) TextView altNameTextView;
    @BindView(R.id.confidenceTextView) TextView confidenceTextView;
    @BindView(R.id.birdImageView) protected ImageView image;

    public ListViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, true);
    }

    protected void initialize(Context context, AttributeSet attrs, boolean useDefaultImage) {
        inflateLayout(context);
        ButterKnife.bind(this);
        if (!useDefaultImage) {
            image.setImageBitmap(null);
        }
    }

    protected void inflateLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.list_view_item, this);
    }

    public void setName(String name) {
        birdNameTextView.setText(name);
    }

    public void setAltName(String altName) {
         altNameTextView.setText(altName);
    }

    public void setConfidence(String confidence) {
        String concatenated = confidence + " %";
        confidenceTextView.setText(concatenated);
    }

    public void setImage(int resourceId) {
        Picasso.with(getContext())
                .load(resourceId)
                .fit()
                .centerCrop()
                .into(image);
    }
}
