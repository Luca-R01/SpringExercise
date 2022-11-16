package com.project.usermanager.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFullResponseDTO {

    @JsonProperty("USER_REGISTRY")
    private UserRegistryResponseDTO user;
    
    @JsonProperty("CARS_LIST")
    private List<CarResponseDTO> carList;
    
}
