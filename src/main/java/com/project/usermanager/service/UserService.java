package com.project.usermanager.service;

import java.util.List;

import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;

public interface UserService {

    void createUser(UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException;

    UserResponseDTO findUser(String fiscalCode) throws NotFoundException;

    void editUser(UserRequestDTOPut requestDTO, String fiscalCode) throws BadRequestException, NotFoundException;

    void deleteUser(String fiscalCode) throws NotFoundException;

    List<UserResponseDTO> findAll();

    
}