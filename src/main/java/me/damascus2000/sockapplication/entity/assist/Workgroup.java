package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Workgroup {

    @JsonProperty("Id")
    private int id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Short")
    private String shortName;
    @JsonProperty("Editable")
    private Boolean editable;
    @JsonProperty("Deleteable")
    private Boolean deleteable;
    @JsonProperty("MasterWorkgroupId")
    private Integer masterWorkgroupId;
    @JsonProperty("LoadDuringSetup")
    private boolean loadDuringSetup;
    @JsonProperty("CannotDeleteMessage")
    private String cannotDeleteMessage;
    @JsonProperty("MoreInfo")
    private String moreInfo;
    @JsonProperty("ParentId")
    private Integer parentId;
    @JsonProperty("HasChildren")
    private boolean hasChildren;
    @JsonProperty("Disabled")
    private boolean disabled;
    @JsonProperty("IsStandardVzfWorkgroup")
    private Boolean isStandardVzfWorkgroup;
    @JsonProperty("ShowInWaitinglist")
    private boolean showInWaitinglist;
    @JsonProperty("DefaultFunctionId")
    private Integer defaultFunctionId;
}
