package com.siuts.siutsapplication.common;


import java.util.Locale;

import lombok.Data;

// TODO: make this work with annotations and rename underscore variable names
public class ClassifiedBird {
    private String slug;
    private String name;
    private String name_en;
    private String name_et;
    private String name_la;
    private String description;
    private String description_en;
    private String description_et;
    private String description_la;
    private String confidence;

    public ClassifiedBird() {}

    public ClassifiedBird(final String slug)
    {
        setSlug(slug);
        setName(slug);
        setName_en(slug + " name english");
        setName_et(slug + " name estonian");
        setConfidence(String.valueOf(Math.round(Math.random()*100)));
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_et() {
        return name_et;
    }

    public void setName_et(String name_et) {
        this.name_et = name_et;
    }

    public String getName_la() {
        return name_la;
    }

    public void setName_la(String name_la) {
        this.name_la = name_la;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public String getDescription_et() {
        return description_et;
    }

    public void setDescription_et(String description_et) {
        this.description_et = description_et;
    }

    public String getDescription_la() {
        return description_la;
    }

    public void setDescription_la(String description_la) {
        this.description_la = description_la;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
