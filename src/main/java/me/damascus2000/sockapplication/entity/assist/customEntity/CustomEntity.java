package me.damascus2000.sockapplication.entity.assist.customEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"Id", "Name", "NameProperty", "TypeName",})
public class CustomEntity {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("NameProperty")
    private String nameProperty;
    @JsonProperty("TypeName")
    private String typeName;
}
