package com.siuts.siutsapplication.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ClassifiedBird implements Serializable {
    private String slug;
    private String name;

    @JsonProperty("name_en")
    private String nameEn;

    @JsonProperty("name_et")
    private String nameEt;

    @JsonProperty("name_la")
    private String nameLa;

    private String description;

    @JsonProperty("description_en")
    private String descriptionEn;

    @JsonProperty("description_et")
    private String descriptionEt;

    @JsonProperty("description_la")
    private String descriptionLa;
    private String confidence;

    public ClassifiedBird() {}

    public ClassifiedBird(final String slug)
    {
        this.slug = slug;
        this.name = slug;
        this.nameEn = "Great Tit";
        this.nameLa = "Parus Major";
        this.confidence = String.valueOf(Math.round(Math.random()*100));
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameEt() {
        return nameEt;
    }

    public void setNameEt(String nameEt) {
        this.nameEt = nameEt;
    }

    public String getNameLa() {
        return nameLa;
    }

    public void setNameLa(String nameLa) {
        this.nameLa = nameLa;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionEt() {
        return descriptionEt;
    }

    public void setDescriptionEt(String descriptionEt) {
        this.descriptionEt = descriptionEt;
    }

    public String getDescriptionLa() {
        return descriptionLa;
    }

    public void setDescriptionLa(String descriptionLa) {
        this.descriptionLa = descriptionLa;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
