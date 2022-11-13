package com.project.usermanager.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.service.CarService;
import com.project.usermanager.service.UserFullService;
import com.project.usermanager.service.UserRegistryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserFullServiceImpl implements UserFullService {

    @Autowired
    private final UserRegistryService userRegistryService;

    @Autowired
    private final CarService carService;

    private static final Logger logger = LoggerFactory.getLogger(UserFullServiceImpl.class);

    @Override
    public UserFullResponseDTO findUser(String fiscalCode) throws NotFoundException {
        
        logger.info("findUser - IN: fiscalCode({}) ", fiscalCode);

        UserRegistryResponseDTO userRegistry = userRegistryService.findUserRegistry(fiscalCode);
        List<CarResponseDTO> carList = carService.findAllByOwner(fiscalCode);
        UserFullResponseDTO responseDTO = new UserFullResponseDTO(userRegistry, carList);

        logger.info("findUser - OUT: {} ", responseDTO.toString());
        return responseDTO;
    }
    
}
