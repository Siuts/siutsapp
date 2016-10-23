package com.siuts.siutsapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.gson.Gson;
import com.siuts.siutsapplication.adapters.ClassificationResultsAdapter;
import com.siuts.siutsapplication.domain.BirdData;
import com.siuts.siutsapplication.domain.ClassifiedBird;
import com.siuts.siutsapplication.domain.Constants;

import java.util.ArrayList;
import java.util.Arrays;
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

        /*if (intent == null || !intent.hasExtra(Constants.INTENT_EXTRA_CLASSIFICATION_RESULTS)) {
            birds = getDemoResults();
        } else {
            birds = getBirdsFromIntent(intent);
        }*/
        birds = getFinalDemoBirds();
        adapter = new ClassificationResultsAdapter(this, birds);
        listView.setAdapter(adapter);
    }

    List<ClassifiedBird> getBirdsFromIntent(Intent intent) {
        return (List<ClassifiedBird>) intent.getSerializableExtra(Constants.INTENT_EXTRA_CLASSIFICATION_RESULTS);
    }
    
    List<ClassifiedBird> getFinalDemoBirds() {
        /*String data = "[{\"confidence\": \"53.11\", \"description\": \"The tail is relatively short, the wings long, and overall the plumage appears vivid and fresh. The sexes are similar; yellowish-green on top, white underneath with a distinctive yellow throat and breast, pinkish-brown legs, yellow strip above the eye and a dark one through it.\", \"name_la\": \"Phylloscopus Sibilatrix\", \"name_et\": \"Mets-lehelind\", \"name_en\": \"Wood Warbler\", \"slug\": \"phylloscopus_sibilatrix\"}, {\"confidence\": \"72.15\", \"description\": \"Familiar visitors to gardens and bird tables. Head mainly black with white cheek patch. Yellow belly marked with a broad medial black stripe. Back greenish grey.\", \"name_la\": \"Parus Major\", \"name_et\": \"Rasvatihane\", \"name_en\": \"Great tit\", \"slug\": \"parus_major\"}]";
        Gson gson = new Gson();
        ClassifiedBird[] birds = gson.fromJson(data, ClassifiedBird[].class);
        return Arrays.asList(birds);*/

        ClassifiedBird bird1 = new ClassifiedBird();
        bird1.setNameEn("Swift");
        bird1.setNameLa("Apus Apus");
        bird1.setSlug("apus_apus");
        bird1.setConfidence("76.12");

        ClassifiedBird bird2 = new ClassifiedBird();
        bird2.setNameEn("Yellowhammer");
        bird2.setNameLa("Emberiza Citrinella");
        bird2.setSlug("emberiza_citrinella");
        bird2.setConfidence("23.81");

        ClassifiedBird bird3 = new ClassifiedBird();
        bird3.setNameEn("European Greenfinch");
        bird3.setNameLa("Chloris Chloris");
        bird3.setSlug("chloris_chloris");
        bird3.setConfidence("0.07");

        List<ClassifiedBird> birds = new ArrayList<>();
        birds.add(bird1);
        birds.add(bird2);
        birds.add(bird3);
        return birds;
    }

    /*List<ClassifiedBird> getDemoResults() {
        List<ClassifiedBird> birds = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            birds.add(new ClassifiedBird((BirdData.slugs.toArray()[i].toString())));
        }
        return birds;
    }*/
}
