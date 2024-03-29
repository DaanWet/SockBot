package me.damascus2000.sockapplication.entity.assist.customEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"Id", "CustomEntityId", "Name", "CustomEntityPartId", "ListOrder", "CustomEntityFieldTypeId",
    "TypeName"})
public class CustomEntityDefaultField {

    @JsonProperty("Id")
    private int id;
    @JsonProperty("CustomEntityId")
    private int customEntityId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("CustomEntityPartId")
    private int customEntityPartId;
    @JsonProperty("ListOrder")
    private int listOrder;
    @JsonProperty("CustomEntityFieldTypeId")
    private int customEntityFieldTypeId;
    @JsonProperty("TypeName")
    private String typeName;
}
