package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"CreationDate", "SubscriptionDate", "StartDate", "EndDate", "PersonId", "Payed", "MemberTypeId", "OrganizationId", "Organization",
    "NumberFederation", "NumberBond", "MemberMoneyTeam1", "MemberMoneyTeam2", "MemberMoneyTeam3", "MemberMoneyTeam4", "MemberMoneyTeam5", "FamilyDiscount",
    "SocialDiscount", "InvalidDiscount", "OtherDiscount", "InsuranceFee", "Surplus", "PayedAmount", "MemberMoneyPaidOnDate", "MemberMoneyIsNoLongerCollectible",
    "ExtraTrainings", "CompetitionDate", "DroppedOut", "DateOfDropout", "YearDropout", "YearDropoutMinusOne", "DropOutHasBeenProcessed", "HasCopiedVzfData",
    "AssignedFunctions", "TeamNames", "FirstTeamName", "FunctionNames", "FirstFunctionName", "Phone", "Email", "PostCode", "FamilyMemberNr", "AccessKey",
    "HasSocialDiscount", "Id", "Number", "WorkingYearId", "ExportCode", "WorkingYear", "Person", "ReceivedHealthInsuranceCertificateOn", "PublicSavedPersonId",
    "Saldo", "MemberMoneySend", "MemberMoneySendDate", "WaitinglistMemberAccessKey"})
public class AssistMember extends Member {

    @JsonProperty("CreationDate")
    private String creationDate;
    @JsonProperty("SubscriptionDate")
    private String subscriptionDate;
    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("PersonId")
    private int personId;
    @JsonProperty("Payed")
    private Boolean payed;
    @JsonProperty("MemberTypeId")
    private Integer memberTypeId;
    @JsonProperty("OrganizationId")
    private int organizationId;
    @JsonProperty("Organization")
    private Organization organization;
    @JsonProperty("NumberBond")
    private int numberBond;
    @JsonProperty("MemberMoneyPaidOnDate")
    private String memberMoneyPaidOnDate;
    @JsonProperty("MemberMoneyIsNoLongerCollectible")
    private boolean memberMoneyIsNoLongerCollectible;
    @JsonProperty("ExtraTrainings")
    private String extraTrainings;
    @JsonProperty("CompetitionDate")
    private String competitionDate;
    @JsonProperty("DateOfDropout")
    private String dateOfDropout;
    @JsonProperty("YearDropout")
    private Integer yearDropout;
    @JsonProperty("YearDropoutMinusOne")
    private Integer yearDropoutMinusOne;
    @JsonProperty("DropOutHasBeenProcessed")
    private Boolean dropOutHasBeenProcessed;
    @JsonProperty("HasCopiedVzfData")
    private Boolean hasCopiedVzfData;
    @JsonProperty("TeamNames")
    private String teamNames;
    @JsonProperty("FunctionNames")
    private String functionNames;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("PostCode")
    private Integer postCode;
    @JsonProperty("FamilyMemberNr")
    private Integer familyMemberNr;
    @JsonProperty("AccessKey")
    private String accessKey;
    @JsonProperty("HasSocialDiscount")
    private boolean hasSocialDiscount;
    @JsonProperty("WorkingYear")
    private WorkingYear workingYear;
    @JsonProperty("ReceivedHealthInsuranceCertificateOn")
    private String receivedHealthInsuranceCertificateOn;
    @JsonProperty("Person")
    private Person person;
    @JsonProperty("WaitinglistMemberAccessKey")
    private String waitinglistMemberAccessKey;
}
