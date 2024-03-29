package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter

public class MinimalAssistMember extends Member {

    @JsonProperty("Person")
    private PersonWithUser person;
    @JsonProperty("Products")
    private List<String> products;
    @JsonProperty("ProductsForPerson")
    private List<String> productsForPerson;
    @JsonProperty("AllProductsPayed")
    private List<String> allProductsPayed;
}
