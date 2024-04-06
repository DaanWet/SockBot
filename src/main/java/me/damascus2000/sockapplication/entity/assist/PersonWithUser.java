package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PersonWithUser extends MinimalPerson {

    @JsonProperty("HasUser")
    private Boolean user;
}
