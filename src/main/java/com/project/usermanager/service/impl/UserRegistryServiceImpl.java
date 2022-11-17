package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.dto.request.user.UserRequestDTOPost;
import com.project.usermanager.dto.request.user.UserRequestDTOPut;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.UserMapper;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.service.UserRegistryService;
import com.project.usermanager.util.PasswordUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserRegistryServiceImpl implements UserRegistryService {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private final UserMapper mapper;

    private static final Logger logger = LoggerFactory.getLogger(UserRegistryServiceImpl.class);

    @Override
    public UserRegistryResponseDTO createUserRegistry(UserRequestDTOPost requestDTO) throws BadRequestException, ConflictException {

        logger.info("createUser - IN: {} ", requestDTO.toString());

        Optional<UserEntity> findUser = repository.findByFiscalCode(requestDTO.getFiscalCode());
        if (findUser.isPresent()) {
            logger.info("createUser - OUT: ConflictException ");
            throw new ConflictException("User alredy exixts!");
        }
        UserEntity user = mapper.toEntity(requestDTO);
        user = repository.save(user);

        logger.info("createUser - OUT: {} ", user.toString());
        return mapper.toDTO(user);
    }

    public UserRegistryResponseDTO findUserRegistry(String fiscalCode) throws NotFoundException {

        logger.info("findUser - IN: fiscalCode({}) ", fiscalCode);

        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {
            logger.info("findUser - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }
        UserRegistryResponseDTO response = mapper.toDTO(user.get());

        logger.info("findUser - OUT: {} ", response.toString());
        return response;
    }

    @Override
    public void editUserRegistry(UserRequestDTOPut requestDTO, String fiscalCode, String password) throws BadRequestException, NotFoundException {

        logger.info("editUser - IN: {}, fiscalCode({}) ", requestDTO.toString(), fiscalCode);
        
        String encryptedPassword = PasswordUtil.encryptPassword(password);
        Optional<UserEntity> user = repository.findByFiscalCodeAndPassword(fiscalCode, encryptedPassword);
        if (user.isEmpty()) {
            logger.info("editUser - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }
        UserEntity editUser = mapper.editUser(requestDTO, user.get());
        repository.save(editUser);

        logger.info("editUser - OUT: {} ", editUser.toString());  
    }

    @Override
    public void deleteUserRegistry(String fiscalCode, String password) throws NotFoundException {

        logger.info("deleteUser - IN: fiscalCode({}) ", fiscalCode);

        String encryptedPassword = PasswordUtil.encryptPassword(password);
        Optional<UserEntity> user = repository.findByFiscalCodeAndPassword(fiscalCode, encryptedPassword);
        if (user.isEmpty()) {
            logger.info("deleteUser - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }
        repository.delete(user.get());

        logger.info("deleteUser - OUT: {} ", user.get().toString());
    }

    @Override
    public List<UserRegistryResponseDTO> findAllUserRegistry() {

        logger.info("findAll - IN: none ");

        List<UserEntity> userList = repository.findAll();
        List<UserRegistryResponseDTO> response = mapper.toDTOList(userList);
        
        logger.info("findAll - OUT: {} ", response.toString());
        return response;
    }
    
}
