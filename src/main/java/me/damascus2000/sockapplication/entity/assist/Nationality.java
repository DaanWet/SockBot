package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Nationality {

    @JsonProperty("Id")
    private int id;
    @JsonProperty("ISO")
    private String iso;

    @JsonProperty("Frans")
    private String frans;
    @JsonProperty("Nederlands")
    private String nederlands;
    @JsonProperty("Duits")
    private String duits;
    @JsonProperty("Engels")
    private String engels;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("ISO3")
    private String iso3;
    @JsonProperty("CountryCode")
    private int countryCode;
    @JsonProperty("NationalityName")
    private String nationalityName;
}
