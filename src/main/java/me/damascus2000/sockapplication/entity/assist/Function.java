package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Function {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("NeedToBeUnique")
    private boolean needToBeUnique;
    @JsonProperty("OrganizationId")
    private int organizationId;
    @JsonProperty("IsDirector")
    private Boolean isDirector;
    @JsonProperty("IsTrainer")
    private Boolean isTrainer;
    @JsonProperty("ParentId")
    private Integer parentId;
    @JsonProperty("MoreInfo")
    private String moreInfo;
    @JsonProperty("ShowToClubs")
    private boolean showToClubs;
    @JsonProperty("PreventDelete")
    private boolean preventDelete;
}
