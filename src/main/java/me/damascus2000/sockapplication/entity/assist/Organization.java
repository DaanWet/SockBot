package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Organization {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("ExternalId")
    private Integer externalId;
    @JsonProperty("ClubCode")
    private String clubCode;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Short")
    private String shortName;
    @JsonProperty("BTWNumber")
    private String bTWNumber;
    @JsonProperty("Province")
    private String province;
    @JsonProperty("SubscriptionStatus")
    private String subscriptionStatus;
    @JsonProperty("FederationRegistrationNumber")
    private Integer federationRegistrationNumber;
    @JsonProperty("DateLastInvoice")
    private String dateLastInvoice;
    @JsonProperty("FederationId")
    private int federationId;
    @JsonProperty("ToNewAssist")
    private boolean toNewAssist;
    @JsonProperty("PublicDescription")
    private String publicDescription;
    @JsonProperty("ParentOrganizationWorkingYearIdForProductValidation")
    private String parentOrganizationWorkingYearIdForProductValidation;
}
