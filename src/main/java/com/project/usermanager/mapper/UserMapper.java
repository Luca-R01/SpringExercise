package com.project.usermanager.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public UserEntity toEntity(UserRequestDTOPost requestDTO) throws BadRequestException {

        logger.info("toEntity - IN: {} ", requestDTO.toString());

        UserEntity user = UserEntity.builder()
            .birthDate(requestDTO.getBirthDate())
            .email(requestDTO.getEmail())
            .fiscalCode(requestDTO.getFiscalCode())
            .gender(requestDTO.getGender())
            .lastName(requestDTO.getLastName())
            .name(requestDTO.getName())
        .build();

        // Password control
        if (requestDTO.getPassword().equals(requestDTO.getConfirmPassword())) {
            String encryptedPassword = PasswordUtil.encryptPassword(requestDTO.getPassword());
            user.setPassword(encryptedPassword);
        }
        else {
            throw new BadRequestException("The passwords entered are not the same!");
        }

        logger.info("toEntity - OUT: {} ", user.toString());
        return user;
    }


    public UserRegistryResponseDTO toDTO(UserEntity user) {

        logger.info("toDTO - IN: {}", user.toString());

        UserRegistryResponseDTO responseDTO = UserRegistryResponseDTO.builder()
            .age(Period.between(user.getBirthDate(), LocalDate.now()).getYears())
            .email(user.getEmail())
            .birthDate(user.getBirthDate())
            .fiscalCode(user.getFiscalCode())
            .gender(user.getGender())
            .id(user.getId().toString())
            .lastName(user.getLastName())
            .name(user.getName())
        .build();

        logger.info("toDTO - OUT: {} ", responseDTO.toString());
        return responseDTO;
    }

    public List<UserRegistryResponseDTO> toDTOList(List<UserEntity> userList) {

        logger.info("toDTOList - IN: {} ", userList.toString());

        List<UserRegistryResponseDTO> responseDTOList = new ArrayList<>();
        for (UserEntity user : userList) {
            responseDTOList.add(this.toDTO(user));
        }

        logger.info("toDTOList - OUT: {} ", responseDTOList.toString());
        return responseDTOList;
    }

    public UserEntity editUser(UserRequestDTOPut requestDTO, UserEntity user) throws BadRequestException {

        logger.info("editUser - IN: {}, {} ", requestDTO.toString(), user.toString());

        if (requestDTO.getBirthDate() != null) {
            user.setBirthDate(requestDTO.getBirthDate());
        }
        if (requestDTO.getEmail() != null) {
            user.setEmail(requestDTO.getEmail());
        }
        if (requestDTO.getFiscalCode() != null) {
            user.setFiscalCode(requestDTO.getFiscalCode());
        }
        if (requestDTO.getFiscalCode() != null) {
            user.setFiscalCode(requestDTO.getFiscalCode());
        }
        if (requestDTO.getGender() != null) {
            user.setGender(requestDTO.getGender());
        }
        if (requestDTO.getLastName() != null) {
            user.setLastName(requestDTO.getLastName());
        }
        if (requestDTO.getName() != null) {
            user.setName(requestDTO.getName());
        }
        if (requestDTO.getPassword() != null && requestDTO.getConfirmPassword() != null) {
            // Password control
            if (requestDTO.getPassword().equals(requestDTO.getConfirmPassword())) {
                user.setPassword(requestDTO.getPassword());
            }
            else {
                throw new BadRequestException("The passwords entered are not the same!");
            }
        }

        logger.info("editUser - OUT: {} ", user.toString());
        return user;
    }
    
}
