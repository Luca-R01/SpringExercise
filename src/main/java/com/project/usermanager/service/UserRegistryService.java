package com.project.usermanager.service;

import java.util.List;

import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.model.UserEntity;

public interface UserRegistryService {

    UserEntity createUserRegistry(UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException;

    UserEntity findUserRegistry(String username) throws NotFoundException;

    void editUserRegistry(UserRequestDTOPut requestDTO, String username, String password) throws BadRequestException, NotFoundException, ConflictException;

    List<UserEntity> findAllUserRegistry();

    void deleteUserRegistry(String username, String password) throws NotFoundException, BadRequestException;
    
}