package com.siuts.siutsapplication;

import com.google.gson.Gson;
import com.siuts.siutsapplication.domain.ClassifiedBird;

import org.junit.Test;

import java.util.ArrayList;

public class ClassifiedBirdTest {
    
    public static String getInput() {
        return "[{\"description\": \"The tail is relatively short, the wings long, and overall the plumage appears vivid and fresh. The sexes are similar; yellowish-green on top, white underneath with a distinctive yellow throat and breast, pinkish-brown legs, yellow strip above the eye and a dark one through it.\", \"name_la\": \"Phylloscopus Sibilatrix\", \"name_ee\": \"Mets-lehelind\", \"name_en\": \"Wood Warbler\", \"confidence\": \"53.11\", \"name\": \"phylloscopus_sibilatrix\"}, {\"description\": \"Familiar visitors to gardens and bird tables. Head mainly black with white cheek patch. Yellow belly marked with a broad medial black stripe. Back greenish grey.\", \"name_la\": \"Parus Major\", \"name_ee\": \"Rasvatihane\", \"name_en\": \"Great tit\", \"confidence\": \"72.1\", \"name\": \"parus_major\"}]";
    }

    @Test
    public void testCanParse() {
        Gson gson = new Gson();
        ClassifiedBird[] birds = gson.fromJson(getInput(), ClassifiedBird[].class);
        for (ClassifiedBird bird : birds) {
            System.out.println("=========================");
            System.out.println(bird.getNameEn());
            System.out.println(bird.getNameEt());
            System.out.println(bird.getName());
            System.out.println(bird.getConfidence());
            System.out.println(bird.getDescription());
        }
    }
}
