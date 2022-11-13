package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void createUser(UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException {

        logger.info("createUser - IN: {} ", requestDTO.toString());

        Optional<UserEntity> findUser = repository.findByFiscalCode(requestDTO.getFiscalCode());
        if (findUser.isPresent()) {
            logger.info("createUser - OUT: ConflictException ");
            throw new ConflictException("User alredy exixts!");
        }
        else {
            UserEntity user = mapper.toEntity(requestDTO);
            repository.save(user);

            logger.info("createUser - OUT: {} ", user.toString());
        }
    }

    public UserResponseDTO findUser(String fiscalCode) throws NotFoundException {

        logger.info("findUser - IN: fiscalCode({}) ", fiscalCode);

        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {
            logger.info("findUser - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }
        else {
            UserResponseDTO responseDTO = mapper.toDTO(user.get());

            logger.info("findUser - OUT: {} ", responseDTO.toString());
            return responseDTO;
        }
    }

    @Override
    public void editUser(UserRequestDTOPut requestDTO, String fiscalCode) throws BadRequestException, NotFoundException {

        logger.info("editUser - IN: {}, fiscalCode({}) ", requestDTO.toString(), fiscalCode);
        
        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {
            logger.info("editUser - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }
        else {
            UserEntity editUser = mapper.editUser(requestDTO, user.get());
            repository.save(editUser);

            logger.info("editUser - OUT: {} ", editUser.toString());
        }    
    }

    @Override
    public void deleteUser(String fiscalCode) throws NotFoundException {

        logger.info("deleteUser - IN: fiscalCode({}) ", fiscalCode);
        
        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {
            logger.info("deleteUser - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }
        else {
            repository.delete(user.get());

            logger.info("deleteUser - OUT: {} ", user.get());
        }
    }

    @Override
    public List<UserResponseDTO> findAll() {

        logger.info("findAll - IN: none ");

        List<UserEntity> userList = repository.findAll();
        List<UserResponseDTO> responseDTOList = mapper.toDTOList(userList);
        
        logger.info("findAll - OUT: {} ", responseDTOList.toString());
        return responseDTOList;
    }
    
}
