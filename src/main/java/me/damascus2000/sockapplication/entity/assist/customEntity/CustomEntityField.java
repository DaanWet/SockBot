package me.damascus2000.sockapplication.entity.assist.customEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonPropertyOrder({"Id", "CustomEntityId", "CustomEntityPartId", "CustomEntityFieldTypeId", "CustomEntityDefaultFieldId", "OrganizationId", "Name",
    "RequiredPublic", "Required", "IsEncrypted", "FederationId", "VisibleForClub", "FederationFieldId", "ShowPublic", "Show", "ListOrder", "ShowAlways",
    "RequiredWaitinglist", "ShowWaitinglist", "CustomEntityDefaultField", "CustomEntityFieldType", "CustomEntityOptions"})
public class CustomEntityField {
    @JsonProperty("Id")
    private int id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("CustomEntityId")
    private int customEntityId;
    @JsonProperty("CustomEntityPartId")
    private int customEntityPartId;
    @JsonProperty("CustomEntityFieldTypeId")
    private int customEntityFieldTypeId;
    @JsonProperty("CustomEntityDefaultFieldId")
    private Integer customEntityDefaultFieldId;
    @JsonProperty("OrganizationId")
    private int organizationId;
    @JsonProperty("RequiredPublic")
    private boolean requiredPublic;
    @JsonProperty("Required")
    private boolean required;
    @JsonProperty("IsEncrypted")
    private boolean encrypted;
    @JsonProperty("FederationId")
    private Integer federationId;
    @JsonProperty("VisibleForClub")
    private Boolean visibleForClub;
    @JsonProperty("FederationFieldId")
    private Integer federationFieldId;
    @JsonProperty("ShowPublic")
    private boolean showPublic;
    @JsonProperty("Show")
    private boolean show;
    @JsonProperty("ListOrder")
    private int listOrder;
    @JsonProperty("ShowAlways")
    private boolean showAlways;
    @JsonProperty("RequiredWaitinglist")
    private boolean requiredWaitinglist;
    @JsonProperty("ShowWaitinglist")
    private boolean showWaitinglist;
    @JsonProperty("CustomEntityDefaultField")
    private CustomEntityDefaultFieldWithFederationSettings customEntityDefaultField;
    @JsonProperty("CustomEntityFieldType")
    private CustomEntityFieldType customEntityFieldType;
    @JsonProperty("CustomEntityOptions")
    private List<String> customEntityOptions;
}
