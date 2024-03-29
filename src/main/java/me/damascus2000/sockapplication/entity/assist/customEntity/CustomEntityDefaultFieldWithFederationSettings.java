package me.damascus2000.sockapplication.entity.assist.customEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"CustomEntityFederationSettings", "Id", "CustomEntityId", "Name", "CustomEntityPartId", "ListOrder", "CustomEntityFieldTypeId",
    "TypeName"})
public class CustomEntityDefaultFieldWithFederationSettings extends CustomEntityDefaultField {
    @JsonProperty("CustomEntityFederationSettings")
    private List<CustomEntityFederationSetting> customEntityFederationSettings;
}
