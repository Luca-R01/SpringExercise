package com.project.usermanager.mapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.model.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserFullMapper {

    @Autowired
    private final UserMapper userMapper;

    @Autowired
    private final CarMapper carMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserFullMapper.class);

    public UserFullResponseDTO toDTO(UserEntity userEntity, List<CarEntity> carsList) {

        logger.info("toDTO - IN: {}, {} ", userEntity.toString(), carsList.toString());

        UserFullResponseDTO response = UserFullResponseDTO.builder()
            .user(userMapper.toDTO(userEntity))
            .carList(carMapper.toDTOList(carsList))
            .build(
        );

        logger.info("toDTO - IN: {} ", response.toString());
        return response;
    }


    
}
