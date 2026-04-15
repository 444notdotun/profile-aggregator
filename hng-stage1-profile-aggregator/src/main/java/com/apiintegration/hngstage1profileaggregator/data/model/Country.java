package com.apiintegration.hngstage1profileaggregator.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class Country {
        private String countryId;
        private Double probability;
}
