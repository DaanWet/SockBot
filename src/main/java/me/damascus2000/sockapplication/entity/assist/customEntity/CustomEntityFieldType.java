package me.damascus2000.sockapplication.entity.assist.customEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomEntityFieldType {

    @JsonProperty("Id")
    private int id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("ListOrder")
    private int listOrder;
    @JsonProperty("Alias")
    private String alias;
    @JsonProperty("HasOptions")
    private boolean hasOptions;
    @JsonProperty("ValidationPattern")
    private String validationPattern;
}
