package com.project.usermanager.controller.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.project.usermanager.controller.UserFullController;
import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.service.UserFullService;

import lombok.Builder;

@Builder
@RestController
public class UserFullControllerImpl implements UserFullController {

    @Autowired
    private final UserFullService service;

    private static final Logger logger = LoggerFactory.getLogger(UserFullControllerImpl.class);

    @Override
    public ResponseEntity<UserFullResponseDTO> findUser(String username) throws NotFoundException {

        logger.info("findUser - IN: username({}) ", username);

        UserFullResponseDTO responseDTO = service.findUser(username);
        ResponseEntity<UserFullResponseDTO> response = new ResponseEntity<>(responseDTO, HttpStatus.OK);

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }
    
}
