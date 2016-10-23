package com.siuts.siutsapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.siuts.siutsapplication.adapters.ClassificationResultsAdapter;
import com.siuts.siutsapplication.domain.BirdData;
import com.siuts.siutsapplication.domain.ClassifiedBird;
import com.siuts.siutsapplication.domain.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassificationResultsActivity extends Activity {

    @BindView(R.id.birdListView) ListView listView;
    ClassificationResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification_results);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        List<ClassifiedBird> birds = null;
        if (intent == null || !intent.hasExtra(Constants.INTENT_EXTRA_CLASSIFICATION_RESULTS)) {
            birds = getDemoResults();
        } else {
            birds = getBirdsFromIntent(intent);
        }
        adapter = new ClassificationResultsAdapter(this, birds);
        listView.setAdapter(adapter);
    }

    List<ClassifiedBird> getBirdsFromIntent(Intent intent) {
        return (List<ClassifiedBird>) intent.getSerializableExtra(Constants.INTENT_EXTRA_CLASSIFICATION_RESULTS);
    }

    List<ClassifiedBird> getDemoResults() {
        List<ClassifiedBird> birds = new ArrayList<>();
        for (String slug : BirdData.slugs) {
            birds.add(new ClassifiedBird((slug)));
        }
        return birds;
    }
}
