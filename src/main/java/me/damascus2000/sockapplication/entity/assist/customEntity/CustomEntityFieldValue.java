package me.damascus2000.sockapplication.entity.assist.customEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CustomEntityFieldValue {

    @JsonProperty("CustomEntityField")
    private CustomEntityField customEntityField;
    @JsonProperty("CustomEntityOption")
    private String customEntityOption;
    @JsonProperty("Id")
    private int id;
    @JsonProperty("CustomEntityObjectId")
    private int customEntityObjectId;
    @JsonProperty("CustomEntityFieldId") // Same as customEntityField.id ?
    private int customEntityFieldId;
    @Setter
    @JsonProperty("Value")
    private String value;
    @JsonProperty("OrganizationId")
    private int organizationId;
    @JsonProperty("OptionId")
    private Integer optionId;
    @JsonProperty("Salt")
    private String salt;
    @JsonProperty("EncryptedValue")
    private String encryptedValue;
    @JsonProperty("EncryptedOptionId")
    private String encryptedOptionId;
}
