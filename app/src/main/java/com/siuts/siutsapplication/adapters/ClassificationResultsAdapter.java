package com.siuts.siutsapplication.adapters;


import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.siuts.siutsapplication.common.BirdData;
import com.siuts.siutsapplication.common.ClassifiedBird;
import com.siuts.siutsapplication.views.ListViewItem;
import com.siuts.siutsapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ClassificationResultsAdapter implements ListAdapter {

    protected Activity activity;
    protected List<DataSetObserver> dataSetObserverList;
    protected List<ClassifiedBird> birds;

    public ClassificationResultsAdapter(final Activity activity, List<ClassifiedBird> birds) {
        this.activity = activity;
        dataSetObserverList = new ArrayList<>();
        this.birds = birds;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        dataSetObserverList.add(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        dataSetObserverList.remove(dataSetObserver);
    }

    @Override
    public int getCount() {
        return birds.size();
    }

    @Override
    public Object getItem(int i) {
        return birds.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int idx, View convertView, ViewGroup viewGroup) {
        final ListViewItem view;
        if (convertView != null) {
            view = (ListViewItem) convertView;
        } else {
            view = new ListViewItem(activity, activity.getResources().getLayout(R.layout.list_view_item));
        }
        ClassifiedBird bird = (ClassifiedBird)getItem(idx);
        view.setName(bird.getName_en());
        view.setAltName(bird.getName_et());
        view.setConfidence(bird.getConfidence());
        String slug = bird.getSlug();
        if (BirdData.slugImages.containsKey(slug)) {
            view.setImage(BirdData.slugImages.get(slug));
        } else {
            view.setImage(R.drawable.bird_placeholder);
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return birds.size() == 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }
}
