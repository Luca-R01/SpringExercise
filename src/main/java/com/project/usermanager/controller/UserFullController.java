package com.project.usermanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.NotFoundException;

public interface UserFullController {
    
    @GetMapping("/api/user/{fiscalCode}")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    ResponseEntity<UserFullResponseDTO> findUser (
        @PathVariable String fiscalCode
    ) throws NotFoundException;

}
