package com.project.usermanager.component;

import java.util.List;

import com.project.usermanager.dto.request.UserRequestDTO;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;

public interface UserRegistryServiceComponent {

    UserRegistryResponseDTO createUserRegistry(UserRequestDTO requestDTO) throws BadRequestException, ConflictException;

    UserRegistryResponseDTO findUserRegistry(String username) throws NotFoundException;

    void editUserRegistry(UserRequestDTO requestDTO, String username, String password) throws BadRequestException, NotFoundException, ConflictException;

    List<UserRegistryResponseDTO> findAllUserRegistry();
    
}