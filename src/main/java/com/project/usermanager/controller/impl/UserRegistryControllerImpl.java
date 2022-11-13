package com.project.usermanager.controller.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.usermanager.controller.UserRegistryController;
import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.service.UserRegistryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserRegistryControllerImpl implements UserRegistryController {

    @Autowired
    private final UserRegistryService service;

    @Override
    public void createUserRegistry(@Valid UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException {

        service.createUserRegistry(requestDTO);
    }

    @Override
    public ResponseEntity<UserRegistryResponseDTO> findUserRegistry(String fiscalCode) throws BadRequestException, ConflictException, NotFoundException {
        
        UserRegistryResponseDTO responseDTO = service.findUserRegistry(fiscalCode);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Override
    public void editUserRegistry(@Valid UserRequestDTOPut requestDTO, String fiscalCode) throws BadRequestException, NotFoundException {
        
        service.editUserRegistry(requestDTO, fiscalCode);
    }

    @Override
    public void deleteUserRegistry(String fiscalCode) throws NotFoundException {
        
        service.deleteUserRegistry(fiscalCode);  
    }

    @Override
    public ResponseEntity<List<UserRegistryResponseDTO>> findAllUserRegistry() {

        List<UserRegistryResponseDTO> responseDTOList = service.findAllUserRegistry();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }
    
}
