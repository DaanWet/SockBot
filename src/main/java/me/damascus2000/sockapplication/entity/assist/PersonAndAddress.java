package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonAndAddress {
    @JsonProperty("Address")
    private AddressWithRooms address;

    @JsonProperty("Id")
    private int id;
    @JsonProperty("AddressId")
    private int addressId;
    @JsonProperty("PersonId")
    private int personId;
    @JsonProperty("AddressMemberTypeId")
    private Integer addressMemberTypeId;
    @JsonProperty("SendPost")
    private boolean sendPost;
    @JsonProperty("IsHidden")
    private Boolean isHidden;
}
