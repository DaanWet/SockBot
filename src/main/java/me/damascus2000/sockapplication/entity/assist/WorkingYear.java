package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WorkingYear {
    @JsonProperty("IsSelected")
    private boolean selected;

    @JsonProperty("OrganizationId")
    private int organizationId;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("MoreInfo")
    private String moreInfo;
    @JsonProperty("FullYear")
    private boolean fullYear;
}
