package com.siuts.siutsapplication;


import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClassificationResultsAdapter implements ListAdapter {

    protected Activity activity;
    protected List<DataSetObserver> dataSetObserverList;


    public ClassificationResultsAdapter(final Activity activity) {
        this.activity = activity;
        dataSetObserverList = new ArrayList<>();
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
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final ListViewItem view;
        if (convertView != null) {
            view = (ListViewItem) convertView;
        } else {
            view = new ListViewItem(activity, activity.getResources().getLayout(R.layout.list_view_item));
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
        return false;
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
