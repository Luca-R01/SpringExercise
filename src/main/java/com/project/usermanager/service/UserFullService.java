package com.project.usermanager.service;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.NotFoundException;

public interface UserFullService {

    UserFullResponseDTO findUser(String username) throws NotFoundException;
    
}
