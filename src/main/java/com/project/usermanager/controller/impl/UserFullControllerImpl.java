package com.project.usermanager.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.usermanager.controller.UserFullController;
import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.service.UserFullService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserFullControllerImpl implements UserFullController {

    @Autowired
    private final UserFullService service;

    @Override
    public ResponseEntity<UserFullResponseDTO> findUser(String fiscalCode) throws NotFoundException {

        UserFullResponseDTO response = service.findUser(fiscalCode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
