package me.damascus2000.sockapplication.entity.assist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class ListResponseEntity<T> {
    @JsonProperty("Items")
    private List<T> items;
    @JsonProperty("NextPageLink")
    private String nextPageLink;
    @JsonProperty("Count")
    private int count;
}
