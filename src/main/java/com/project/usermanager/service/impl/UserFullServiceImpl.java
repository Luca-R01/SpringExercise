package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.UserFullMapper;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.repository.CarRepository;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.service.UserFullService;

import lombok.Builder;

@Builder
@Service
public class UserFullServiceImpl implements UserFullService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final UserFullMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(UserFullServiceImpl.class);

    @Override
    public UserFullResponseDTO findUser(String username) throws NotFoundException {
        
        logger.info("findUser - IN: username({}) ", username);

        // Find User
        Optional<UserEntity> userRegistry = userRepository.findByUsername(username);
        if (userRegistry.isEmpty()) {
            
            logger.info("findUser - OUT: NotFoundException ");
            throw new NotFoundException("User Not Found!");
        }
        // Find User's Cars
        List<CarEntity> carList = carRepository.findAllByOwnerUsername(username);

        UserFullResponseDTO response = mapper.toDTO(userRegistry.get(), carList);

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }
    
}
