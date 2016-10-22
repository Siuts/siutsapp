package com.siuts.siutsapplication;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BirdData {

    static Map<Birds, Integer> birdImages = getImages();
    static Map<Birds, String> birdSlugs = getSlugs();
    static Map<String, Integer> slugImages = getSlugImages();
    static List<String> slugs = new ArrayList<>(getSlugs().values());

    static protected Map<Birds, String> getSlugs() {
        Map<Birds, String> images = new HashMap<>();
        images.put(Birds.PARUS_MAJOR, "parus_major");
        images.put(Birds.FRINGILLA_COELEBS, "fringilla_coelebs");
        images.put(Birds.TURDUS_MERULA, "turdus_merula");
        images.put(Birds.EMBERIZA_CITRINELLA, "emberiza_citrinella");
        images.put(Birds.CHLORIS_CHLORIS, "chloris_chloris");
        images.put(Birds.APUS_APUS, "apus_apus");
        images.put(Birds.PHOENICURUS_PHOENICURUS, "phoenicurus_phoenicurus");
        images.put(Birds.LOCUSTELLA_FLUVIATILIS, "locustella_fluviatilis");
        images.put(Birds.PHYLLOSCOPUS_SIBILATRIX, "phylloscopus_sibilatrix");
        images.put(Birds.DENDROCOPOS_MAJOR, "dendrocopus_major");
        return images;
    }

    static protected Map<Birds, Integer> getImages() {
        Map<Birds, Integer> images = new HashMap<>();
        images.put(Birds.PARUS_MAJOR, R.drawable.parus_major);
        images.put(Birds.FRINGILLA_COELEBS, R.drawable.fringilla_coelebs);
        images.put(Birds.TURDUS_MERULA, R.drawable.turdus_merula);
        images.put(Birds.EMBERIZA_CITRINELLA, R.drawable.emberiza_citrinella);
        images.put(Birds.CHLORIS_CHLORIS, R.drawable.chloris_chloris);
        images.put(Birds.APUS_APUS, R.drawable.apus_apus);
        images.put(Birds.PHOENICURUS_PHOENICURUS, R.drawable.phoenicurus_phoenicurus);
        images.put(Birds.LOCUSTELLA_FLUVIATILIS, R.drawable.locustella_fluviatilis);
        images.put(Birds.PHYLLOSCOPUS_SIBILATRIX, R.drawable.phylloscopus_sibilatrix);
        images.put(Birds.DENDROCOPOS_MAJOR, R.drawable.dendrocopos_major);
        return images;
    }

    static protected Map<String, Integer> getSlugImages() {
        Map<Birds, Integer> images = getImages();
        Map<Birds, String> slugs = getSlugs();
        Map<String, Integer> slugImages = new HashMap<>();
        for (Birds bird : images.keySet()) {
            slugImages.put(slugs.get(bird), images.get(bird));
        }
        return slugImages;
    }
}
