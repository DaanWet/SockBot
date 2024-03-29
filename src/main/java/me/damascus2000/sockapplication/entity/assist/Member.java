package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonPropertyOrder({"Person", "AssignedFunctions", "Products", "ProductsForPerson", "AllProductsPayed", "Id", "Number", "NumberFederation", "WorkingYearId",
    "ExportCode", "Saldo", "FirstTeamName", "FirstFunctionName", "FamilyMemberNr", "DroppedOut", "DropOutHasBeenProcessed", "MemberMoneyTeam1",
    "MemberMoneyTeam2", "MemberMoneyTeam3", "MemberMoneyTeam4", "MemberMoneyTeam5", "FamilyDiscount", "SocialDiscount", "InvalidDiscount", "OtherDiscount",
    "InsuranceFee", "Surplus", "PayedAmount", "AccessKey", "PublicSavedPersonId", "MemberMoneySend", "MemberMoneySendDate",})
public abstract class Member {
    @JsonProperty("Person")
    private MinimalPerson person;
    @JsonProperty("AssignedFunctions")
    private List<String> assignedFunctions;

    @JsonProperty("Id")
    private int id;
    @JsonProperty("Number")
    private Integer number;
    @JsonProperty("NumberFederation")
    private Integer numberFederation;
    @JsonProperty("WorkingYearId")
    private Integer workingYearId;
    @JsonProperty("ExportCode")
    private Integer exportCode;
    @JsonProperty("Saldo")
    private double saldo;

    @JsonProperty("FirstTeamName")
    private String firstTeamName;
    @JsonProperty("FirstFunctionName")
    private String firstFunctionName;
    @JsonProperty("FamilyMemberNr")
    private Integer familyMemberNr;
    @JsonProperty("DroppedOut")
    private Boolean droppedOut;
    @JsonProperty("DropOutHasBeenProcessed")
    private Boolean dropOutHasBeenProcessed;
    @JsonProperty("MemberMoneyTeam1")
    private double memberMoneyTeam1;
    @JsonProperty("MemberMoneyTeam2")
    private double memberMoneyTeam2;
    @JsonProperty("MemberMoneyTeam3")
    private double memberMoneyTeam3;
    @JsonProperty("MemberMoneyTeam4")
    private double memberMoneyTeam4;
    @JsonProperty("MemberMoneyTeam5")
    private double memberMoneyTeam5;
    @JsonProperty("FamilyDiscount")
    private double familyDiscount;
    @JsonProperty("SocialDiscount")
    private double socialDiscount;
    @JsonProperty("InvalidDiscount")
    private Double invalidDiscount;
    @JsonProperty("OtherDiscount")
    private double otherDiscount;
    @JsonProperty("InsuranceFee")
    private double insuranceFee;
    @JsonProperty("Surplus")
    private double surplus;
    @JsonProperty("PayedAmount")
    private double payedAmount;
    @JsonProperty("AccessKey")
    private String accessKey;
    @JsonProperty("PublicSavedPersonId")
    private Integer publicSavedPersonId;
    @JsonProperty("MemberMoneySend")
    private Boolean memberMoneySend;
    @JsonProperty("MemberMoneySendDate")
    private String memberMoneySendDate;

    public boolean hasPayed() {
        return saldo == 0.0;
    }
}
