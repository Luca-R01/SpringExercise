package com.project.usermanager.service;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.NotFoundException;

public interface UserFullService {

    UserFullResponseDTO findUser(String username) throws NotFoundException;

    void deleteUserAndCars(String username, String password) throws NotFoundException, BadRequestException;
    
}
