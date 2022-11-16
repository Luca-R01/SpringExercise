package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.CarMapper;
import com.project.usermanager.mapper.UserMapper;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.repository.CarRepository;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.service.UserFullService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserFullServiceImpl implements UserFullService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final CarRepository carRepository;

    @Autowired
    private final CarMapper carMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserFullServiceImpl.class);

    @Override
    public UserFullResponseDTO findUser(String fiscalCode) throws NotFoundException {
        
        logger.info("findUser - IN: fiscalCode({}) ", fiscalCode);

        Optional<UserEntity> userRegistry = userRepository.findByFiscalCode(fiscalCode);
        if (userRegistry.isEmpty()) {
            logger.info("findUser - OUT: NotFoundException ");
            throw new NotFoundException("User Not Found!");
        }
        List<CarEntity> carList = carRepository.findAllByOwnerFiscalCode(fiscalCode);
        UserFullResponseDTO response = UserFullResponseDTO.builder()
            .user(userMapper.toDTO(userRegistry.get()))
            .carList(carMapper.toDTOList(carList))
            .build(
        );

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }
    
}
