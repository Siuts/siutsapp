package com.siuts.siutsapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.siuts.siutsapplication.adapters.ClassificationResultsAdapter;
import com.siuts.siutsapplication.common.BirdData;
import com.siuts.siutsapplication.common.ClassifiedBird;

import java.util.ArrayList;
import java.util.List;

public class ClassificationResultsActivity extends AppCompatActivity {

    ListView listView;
    ClassificationResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_results);
        listView = (ListView) findViewById(R.id.birdListView);
        adapter = new ClassificationResultsAdapter(this, getDemoResults());
        listView.setAdapter(adapter);
    }

    List<ClassifiedBird> getDemoResults() {
        List<ClassifiedBird> birds = new ArrayList<>();
        for (String slug : BirdData.slugs) {
            birds.add(new ClassifiedBird((slug)));
        }
        return birds;
    }
}
