package com.project.usermanager.component;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.NotFoundException;

public interface UserFullServiceComponent {

    UserFullResponseDTO findUser(String username) throws NotFoundException;

    void deleteUserAndCars(String username, String password) throws NotFoundException, BadRequestException;
    
}
