package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder()
public class MinimalPerson {

    @JsonProperty("Id")
    private int id;
    @JsonProperty("ExternalId")
    private Integer externalId;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("CannotDeleteMessage")
    private String cannotDeleteMessage;
    @JsonProperty("OrganizationId")
    private int organizationId;
    @JsonProperty("SalutationId")
    private Integer salutationId;
    @JsonProperty("Salutation")
    private Salutation salutation;
    @JsonProperty("AccessKey")
    private String accessKey;
    @JsonProperty("PublicSavedPersonId")
    private Integer publicSavedPersonId;
    @JsonProperty("IBAN")
    private String iban;
    @JsonProperty("NumberFederation")
    private Integer numberFederation;
    @JsonProperty("Gsm")
    private String mobilePhone;
    @JsonProperty("HomePhone")
    private String homePhone;
    @JsonProperty("WorkPhone")
    private String workPhone;
    @JsonProperty("HomeEmail")
    private String privateEmail;
    @JsonProperty("WorkEmail")
    private String workEmail;
    @JsonProperty("Website")
    private String website;
    @JsonProperty("BirthDate")
    private String birthDate;
    @JsonProperty("HasSocialDiscount")
    private boolean hasSocialDiscount;
    @JsonProperty("CanSendCommercial")
    private Boolean canSendCommercial;
    @JsonProperty("LastUpdateByPerson")
    private String lastUpdateByPerson; //TODO: Needs to be Date?
    @JsonProperty("VolunteerStatusId")
    private Integer volunteerStatusId;
    @JsonProperty("CallName")
    private String callName;
    @JsonProperty("VotasNumber")
    private Integer votasNumber;
    @JsonProperty("BirthPlace")
    private String birthPlace;

    @JsonProperty("RegisterNumber")
    private String registerNumber;

    @JsonProperty("GenderId")
    private int genderId;
    @JsonProperty("NationalityId")
    private int nationalityId;
    @JsonProperty("HomeAddressId")
    private int homeAddressId;
    @JsonProperty("IsTemp")
    private boolean temp;
    //@JsonProperty("HasUser")
    //private boolean user;
    @JsonProperty("HomeAddress")
    private Address homeAddress;

    @JsonIgnore
    public String getName() {
        return firstName + " " + lastName;
    }
}
