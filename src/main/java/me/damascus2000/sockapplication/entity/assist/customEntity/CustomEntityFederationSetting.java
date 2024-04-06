package me.damascus2000.sockapplication.entity.assist.customEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomEntityFederationSetting {

    @JsonProperty("Id")
    private int id;
    @JsonProperty("FederationId")
    private Integer federationId;
    @JsonProperty("CustomEntityDefaultFieldId")
    private Integer customEntityDefaultFieldId;
    @JsonProperty("CustomEntityFieldTypeId")
    private int customEntityFieldTypeId;
    @JsonProperty("CanEditShowPublic")
    private boolean canEditShowPublic;
    @JsonProperty("CanEditRequiredPublic")
    private boolean canEditRequiredPublic;
    @JsonProperty("ShowPublic")
    private boolean showPublic;
    @JsonProperty("Show")
    private boolean show;
    @JsonProperty("RequiredPublic")
    private boolean requiredPublic;
    @JsonProperty("Required")
    private boolean required;
    @JsonProperty("CustomEntityDefaultField")
    private CustomEntityDefaultField customEntityDefaultField;
}
