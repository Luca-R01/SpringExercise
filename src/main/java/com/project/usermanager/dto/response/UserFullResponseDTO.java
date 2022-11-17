package com.project.usermanager.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Builder
@Getter
@Setter
@ToString
public class UserFullResponseDTO {

    @JsonProperty("USER_REGISTRY")
    private UserRegistryResponseDTO user;
    
    @JsonProperty("CARS_LIST")
    private List<CarResponseDTO> carList;
    
}
