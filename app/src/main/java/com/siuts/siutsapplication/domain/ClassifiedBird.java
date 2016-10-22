package com.siuts.siutsapplication.domain;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import lombok.Data;

@Data
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
        this.nameEn = slug + " name english";
        this.nameEt = slug + " name estonian";
        confidence = String.valueOf(Math.round(Math.random()*100));
    }
}
