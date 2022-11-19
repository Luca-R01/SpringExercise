package com.project.usermanager.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.NotFoundException;

public interface UserFullController {
    
    @GetMapping("/api/user/{username}")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    ResponseEntity<UserFullResponseDTO> findUser (
        @PathVariable String username
    ) throws NotFoundException;

    @DeleteMapping("/api/user/{username}/{password}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, value = HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteUserAndCars (
        @PathVariable String username, 
        @PathVariable String password
    ) throws NotFoundException, BadRequestException;

}
