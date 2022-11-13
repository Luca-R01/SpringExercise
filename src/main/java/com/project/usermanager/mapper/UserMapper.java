package com.project.usermanager.mapper;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.model.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UserMapper {

    public UserEntity toEntity(UserRequestDTOPost requestDTO) throws BadRequestException {

        UserEntity user = new UserEntity();
        user.setBirthDate(requestDTO.getBirthDate());
        user.setEmail(requestDTO.getEmail());
        user.setFiscalCode(requestDTO.getFiscalCode());
        user.setGender(requestDTO.getGender());
        user.setLastName(requestDTO.getLastName());
        user.setName(requestDTO.getName());

        // Password control
        if (requestDTO.getPassword().equals(requestDTO.getConfirmPassword())) {
            user.setPassword(requestDTO.getPassword());
        }
        else {
            throw new BadRequestException("The passwords entered are not the same!");
        }

        return user;
    }


    public UserResponseDTO toDTO(UserEntity user) {

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setAge(Period.between(user.getBirthDate(), LocalDate.now()).getYears());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setBirthDate(user.getBirthDate());
        responseDTO.setFiscalCode(user.getFiscalCode());
        responseDTO.setGender(user.getGender());
        responseDTO.setId(user.getId().toString());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setName(user.getName());

        return responseDTO;
    }

    public List<UserResponseDTO> toDTOList(List<UserEntity> userList) {

        List<UserResponseDTO> responseDTOList = new ArrayList<>();
        for (UserEntity user : userList) {
            responseDTOList.add(this.toDTO(user));
        }

        return responseDTOList;
    }

    public UserEntity editUser(UserRequestDTOPut requestDTO, UserEntity user) throws BadRequestException {

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
        return user;
    }
    
}
