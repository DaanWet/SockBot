package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"Rooms", "Id", "ExternalId", "OrganizationId", "AddressTypeId", "Name", "NameTicket", "Street", "Nr", "Bus", "PostCode", "Location",
    "Province", "GSM", "HomePhone", "Email", "Website", "Country", "Fax", "JuridicStatuteId", "JuridicStatuteName", "Building", "IsSwimmingPool", "Family",
    "AddressGroupId", "Short", "MoreInformation", "AccountNumber", "Iban", "Bic", "FollowNumber", "EnterpriseNumber", "customJuridicStatute",
    "IsUsedForCompetitions", "LengthOfPool", "DateOfLastExamination", "Gewest", "ServiceName", "SocialGoal", "VatNumber", "Disabled", "Latitude", "Longitude",
    "NameOfContact",})
public class AddressWithRooms extends Address {
    @JsonProperty("Rooms")
    private List<String> rooms;
}
