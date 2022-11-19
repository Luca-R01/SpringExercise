package com.project.usermanager.service;

import java.util.List;

import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;

public interface UserRegistryService {

    UserRegistryResponseDTO createUserRegistry(UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException;

    UserRegistryResponseDTO findUserRegistry(String username) throws NotFoundException;

    void editUserRegistry(UserRequestDTOPut requestDTO, String username, String password) throws BadRequestException, NotFoundException, ConflictException;

    List<UserRegistryResponseDTO> findAllUserRegistry();
    
}