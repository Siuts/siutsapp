package com.siuts.siutsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.siuts.siutsapplication.adapters.ClassificationResultsAdapter;

public class ClassificationResultsActivity extends AppCompatActivity {

    ListView listView;
    ClassificationResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_results);
        listView = (ListView) findViewById(R.id.birdListView);
        adapter = new ClassificationResultsAdapter(this);
        listView.setAdapter(adapter);
    }
}
