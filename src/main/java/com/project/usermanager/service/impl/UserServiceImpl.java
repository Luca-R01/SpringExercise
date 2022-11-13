package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.UserMapper;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final UserMapper mapper;

    @Override
    public void createUser(UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException {

        Optional<UserEntity> findUser = repository.findByFiscalCode(requestDTO.getFiscalCode());
        if (findUser.isPresent()) {
            throw new ConflictException("User alredy exixts!");
        }
        else {
            UserEntity user = mapper.toEntity(requestDTO);
            repository.save(user);
        }
    }

    public UserResponseDTO findUser(String fiscalCode) throws NotFoundException {

        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        else {
            UserResponseDTO responseDTO = mapper.toDTO(user.get());

            return responseDTO;
        }
    }

    @Override
    public void editUser(UserRequestDTOPut requestDTO, String fiscalCode) throws BadRequestException, NotFoundException {
        
        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        else {
            UserEntity editUser = mapper.editUser(requestDTO, user.get());
            repository.save(editUser);
        }    
    }

    @Override
    public void deleteUser(String fiscalCode) throws NotFoundException {
        
        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }
        else {
            repository.delete(user.get());
        }
        
    }

    @Override
    public List<UserResponseDTO> findAll() {

        List<UserEntity> userList = repository.findAll();
        List<UserResponseDTO> responseDTOList = mapper.toDTOList(userList);
        
        return responseDTOList;
    }
    
}
