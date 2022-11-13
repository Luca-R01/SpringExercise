package com.project.usermanager.controller.impl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.usermanager.controller.UserController;
import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private final UserService service;

    @Override
    public void createUser(@Valid UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException {

        service.createUser(requestDTO);
    }

    @Override
    public ResponseEntity<UserResponseDTO> findUser(String fiscalCode) throws BadRequestException, ConflictException, NotFoundException {
        
        UserResponseDTO responseDTO = service.findUser(fiscalCode);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @Override
    public void editUser(@Valid UserRequestDTOPut requestDTO, String fiscalCode) throws BadRequestException, NotFoundException {
        
        service.editUser(requestDTO, fiscalCode);
    }

    @Override
    public void deleteUser(String fiscalCode) throws NotFoundException {
        
        service.deleteUser(fiscalCode);  
    }

    @Override
    public ResponseEntity<List<UserResponseDTO>> findAll() {

        List<UserResponseDTO> responseDTOList = service.findAll();
        return new ResponseEntity<>(responseDTOList, HttpStatus.OK);
    }
    
}
