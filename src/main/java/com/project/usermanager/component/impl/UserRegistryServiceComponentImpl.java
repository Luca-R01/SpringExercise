package com.project.usermanager.component.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.usermanager.component.UserRegistryServiceComponent;
import com.project.usermanager.dto.request.UserRequestDTO;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.UserMapper;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.service.UserRegistryService;

import lombok.Builder;

@Builder
@Component
public class UserRegistryServiceComponentImpl implements UserRegistryServiceComponent {

    @Autowired
    private final UserRegistryService service;

    @Autowired
    private final UserMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(UserRegistryServiceComponentImpl.class);

    @Override
    public UserRegistryResponseDTO createUserRegistry(UserRequestDTO requestDTO) throws BadRequestException, ConflictException {
        
        logger.info("createUser - IN: {} ", requestDTO.toString());

        UserEntity user = mapper.toEntity(requestDTO);
        UserEntity createdUser = service.createUserRegistry(user);
        UserRegistryResponseDTO response = mapper.toDTO(createdUser);

        logger.info("createUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public UserRegistryResponseDTO findUserRegistry(String username) throws NotFoundException {

        logger.info("findUser - IN: username({}) ", username);
        
        UserEntity user = service.findUserRegistry(username);
        UserRegistryResponseDTO response = mapper.toDTO(user);

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public void editUserRegistry(UserRequestDTO requestDTO, String username, String password) throws BadRequestException, NotFoundException, ConflictException {
        
        logger.info("editUser - IN: {}, username({}) ", requestDTO.toString(), username);

        UserEntity newUser = mapper.toEntity(requestDTO);
        service.editUserRegistry(newUser, username, password);
        
        logger.info("editUser - OUT: OK "); 
    }

    @Override
    public List<UserRegistryResponseDTO> findAllUserRegistry() {

        logger.info("findAll - IN: none ");
        
        List<UserEntity> userList = service.findAllUserRegistry();
        List<UserRegistryResponseDTO> response = mapper.toDTOList(userList);

        logger.info("findAll - OUT: {} ", response);
        return response;
    }
    
}
