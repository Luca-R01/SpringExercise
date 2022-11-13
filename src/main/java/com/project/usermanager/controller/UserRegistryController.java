package com.project.usermanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;

public interface UserRegistryController {
    
    @PostMapping("/api/user/registry")
    @ResponseStatus(code = HttpStatus.CREATED, value = HttpStatus.CREATED)
    void createUserRegistry(@RequestBody @Valid UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException;

    @GetMapping("/api/user/registry/{fiscalCode}")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    ResponseEntity<UserRegistryResponseDTO> findUserRegistry(@PathVariable String fiscalCode) throws BadRequestException, ConflictException, NotFoundException;

    @PutMapping("/api/user/registry/{fiscalCode}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, value = HttpStatus.NO_CONTENT)
    void editUserRegistry(@RequestBody @Valid UserRequestDTOPut requestDTO, @PathVariable String fiscalCode) throws BadRequestException, NotFoundException;

    @DeleteMapping("/api/user/registry/{fiscalCode}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT, value = HttpStatus.NO_CONTENT)
    void deleteUserRegistry(@PathVariable String fiscalCode) throws NotFoundException;

    @GetMapping("/api/user/registry")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    ResponseEntity<List<UserRegistryResponseDTO>> findAllUserRegistry();
}

