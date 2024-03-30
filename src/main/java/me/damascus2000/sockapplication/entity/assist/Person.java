package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import me.damascus2000.sockapplication.entity.assist.customEntity.CustomEntityFieldValue;
import me.damascus2000.sockapplication.entity.assist.customEntity.CustomEntityObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter

@JsonPropertyOrder({"Street", "PostCode", "Location", "Country", "OldMemberId", "OldMemberYearId", "GeneratedHistory", "Memo1", "Memo2", "Memo3", "Memo4",
    "Memo5", "Disabilities", "FederationMembers", "TaskPersons", "PersonTalents", "TitleId", "Picture", "Achtervoegsel", "FamilySituation", "MoreInformation",
    "Nationality", "CustomEntityObjectId", "CustomEntityObject", "DisciplineClasificationStatusId", "DisciplineClasificationRevisionDate",
    "IdentityDocumentUrl", "VolunteerStartDate", "VolunteerEndDate", "VolunteerMotivation", "AllOtherAddresses", "PersonXAddresses", "AddressMembers",
    "CallName", "VotasNumber", "BirthPlace", "RegisterNumber", "GenderId", "NationalityId", "HomeAddressId", "IsTemp", "Organization", "HomeAddress",
    "AssignedFunctions", "Gsm", "HomePhone", "WorkPhone", "HomeEmail", "WorkEmail", "Website", "BirthDate", "HasSocialDiscount", "CanSendCommercial",
    "LastUpdateByPerson", "VolunteerStatusId", "Id", "ExternalId", "FirstName", "LastName", "CannotDeleteMessage", "OrganizationId", "SalutationId",
    "Salutation", "AccessKey", "PublicSavedPersonId", "IBAN", "NumberFederation", "LicensePlate"})
public class Person extends MinimalPerson {
    private static final int DISCORD_NAME_CUSTOM_ENTITY_ID = 252461;
    private static final int DISCORD_ID_CUSTOM_ENTITY_ID = 356178;
    @JsonProperty("Street")
    private String street;
    @JsonProperty("PostCode")
    private String postCode;
    @JsonProperty("Location")
    private String location;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("OldMemberId")
    private Integer oldMemberId;
    @JsonProperty("OldMemberYearId")
    private Integer oldMemberYearId;
    @JsonProperty("GeneratedHistory")
    private String generatedHistory;
    @JsonProperty("Memo1")
    private String memo1;
    @JsonProperty("Memo2")
    private String memo2;
    @JsonProperty("Memo3")
    private String memo3;
    @JsonProperty("Memo4")
    private String memo4;
    @JsonProperty("Memo5")
    private String memo5;
    @JsonProperty("Disabilities")
    private List<String> disabilities;
    @JsonProperty("FederationMembers")
    private List<String> federationMembers;
    @JsonProperty("TaskPersons")
    private List<String> taskPersons;
    @JsonProperty("PersonTalents")
    private List<String> personTalents;
    @JsonProperty("TitleId")
    private String titleId;
    @JsonProperty("Picture")
    private String picture;
    @JsonProperty("Achtervoegsel")
    private String achtervoegsel;
    @JsonProperty("FamilySituation")
    private String familySituation;
    @JsonProperty("MoreInformation")
    private String moreInformation;
    @JsonProperty("Nationality")
    private Nationality nationality;
    @JsonProperty("CustomEntityObjectId")
    private int customEntityObjectId;

    @JsonProperty("DisciplineClasificationStatusId")
    private Integer disciplineClasificationStatusId;
    @JsonProperty("DisciplineClasificationRevisionDate")
    private String disciplineClasificationRevisionDate;
    @JsonProperty("IdentityDocumentUrl")
    private String identityDocumentUrl;
    @JsonProperty("VolunteerStartDate")
    private String volunteerStartDate;
    @JsonProperty("VolunteerEndDate")
    private String volunteerEndDate;
    @JsonProperty("VolunteerMotivation")
    private String volunteerMotivation;
    @JsonProperty("AllOtherAddresses")
    private String allOtherAddresses;

    @JsonProperty("PersonXAddresses")
    private List<PersonAndAddress> personXAddresses;

    @JsonProperty("AddressMembers")
    private List<String> addressMembers;

    @JsonProperty("Organization")
    private Organization organization;
    @JsonProperty("AssignedFunctions")
    private List<AssignedFunction> assignedFunctions;
    @JsonProperty("LicensePlate")
    private String licensePlate;

    @JsonProperty("CustomEntityObject")
    private CustomEntityObject customEntityObject;
    @JsonProperty("HomeAddress")
    private AddressWithRooms homeAddress;

    @JsonIgnore
    public List<CustomEntityFieldValue> getCustomEntityFieldValues() {
        return customEntityObject.getCustomEntityFieldValues();
    }

    @JsonIgnore
    public String getDiscordName() {
        return getCustomEntityField(DISCORD_NAME_CUSTOM_ENTITY_ID).getValue();
    }

    @JsonIgnore
    public String getDiscordId() {
        return getCustomEntityField(DISCORD_ID_CUSTOM_ENTITY_ID).getValue();
    }

    @JsonIgnore
    public boolean hasDiscordId() {
        return getDiscordId() != null && !getDiscordId().isBlank();
    }

    @JsonIgnore
    public boolean hasDiscordName() {
        return getDiscordName() != null && !getDiscordName().isBlank();
    }

    @NotNull
    @JsonIgnore
    private CustomEntityFieldValue getCustomEntityField(int id) {
        return getCustomEntityFieldValues().stream()
            .filter(customEntityFieldValue -> customEntityFieldValue.getCustomEntityFieldId() == id)
            .findFirst().get();
    }

    public void setDiscordName(String name) {
        setCustomEntityFieldValue(DISCORD_NAME_CUSTOM_ENTITY_ID, name);
    }

    public void setDiscordUserId(String id) {
        setCustomEntityFieldValue(DISCORD_ID_CUSTOM_ENTITY_ID, id);
    }

    private void setCustomEntityFieldValue(int id, String value) {
        getCustomEntityField(id).setValue(value);
    }
}
