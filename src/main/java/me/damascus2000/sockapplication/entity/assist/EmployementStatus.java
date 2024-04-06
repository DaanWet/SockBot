package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployementStatus {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("IsVolunteer")
    private boolean isVolunteer;
    @JsonProperty("OrganizationId")
    private Integer organizationId;
    @JsonProperty("NamePlural")
    private String namePlural;
}
