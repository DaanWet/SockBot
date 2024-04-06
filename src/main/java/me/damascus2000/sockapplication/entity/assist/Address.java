package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    @JsonProperty("Id")
    private int id;
    @JsonProperty("ExternalId")
    private Integer externalId;
    @JsonProperty("OrganizationId")
    private int organizationId;
    @JsonProperty("AddressTypeId")
    private Integer addressTypeId;
    @JsonProperty("Name")
    private String name;

    @JsonProperty("NameTicket")
    private String nameTicket;
    @JsonProperty("Street")
    private String street;
    @JsonProperty("Nr")
    private Integer nr;
    @JsonProperty("Bus")
    private String bus;
    @JsonProperty("PostCode")
    private String postCode;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("Province")
    private String province;
    @JsonProperty("GSM")
    private String gsm;

    @JsonProperty("HomePhone")
    private String homePhone;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Website")
    private String website;
    @JsonProperty("Country")
    private String country;

    @JsonProperty("Fax")
    private String Fax;
    @JsonProperty("JuridicStatuteId")
    private String JuridicStatuteId;
    @JsonProperty("JuridicStatuteName")
    private String JuridicStatuteName;
    @JsonProperty("Building")
    private String Building;
    @JsonProperty("IsSwimmingPool")
    private String IsSwimmingPool;
    @JsonProperty("Family")
    private String Family;
    @JsonProperty("AddressGroupId")
    private String AddressGroupId;
    @JsonProperty("Short")
    private String Short;
    @JsonProperty("MoreInformation")
    private String MoreInformation;
    @JsonProperty("AccountNumber")
    private String AccountNumber;
    @JsonProperty("Iban")
    private String Iban;
    @JsonProperty("Bic")
    private String Bic;
    @JsonProperty("FollowNumber")
    private String FollowNumber;
    @JsonProperty("EnterpriseNumber")
    private String EnterpriseNumber;
    @JsonProperty("customJuridicStatute")
    private String customJuridicStatute;
    @JsonProperty("IsUsedForCompetitions")
    private String IsUsedForCompetitions;
    @JsonProperty("LengthOfPool")
    private String LengthOfPool;
    @JsonProperty("DateOfLastExamination")
    private String DateOfLastExamination;
    @JsonProperty("Gewest")
    private String Gewest;
    @JsonProperty("ServiceName")
    private String ServiceName;
    @JsonProperty("SocialGoal")
    private String SocialGoal;
    @JsonProperty("VatNumber")
    private String VatNumber;

    @JsonProperty("Disabled")
    private boolean disabled;

    @JsonProperty("Latitude")
    private Double latitude;
    @JsonProperty("Longitude")
    private Double longitude;
    @JsonProperty("NameOfContact")
    private String nameOfContact;
}
