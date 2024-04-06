package me.damascus2000.sockapplication.entity.assist.customEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonPropertyOrder({"CustomEntity", "CustomEntityFieldValues", "Id", "CustomEntityId", "OrganizationId"})
public class CustomEntityObject {

    @JsonProperty("CustomEntity")
    private CustomEntity customEntity;
    @JsonProperty("CustomEntityFieldValues")
    private List<CustomEntityFieldValue> customEntityFieldValues;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("CustomEntityId")
    private int customEntityId;
    @JsonProperty("OrganizationId")
    private int organizationId;
}
