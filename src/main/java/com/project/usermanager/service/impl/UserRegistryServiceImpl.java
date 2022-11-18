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

import lombok.Builder;

@Builder
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

        // Control if User already exists
        Optional<UserEntity> findUser = repository.findByFiscalCode(requestDTO.getFiscalCode());
        if (findUser.isPresent()) {

            logger.info("createUser - OUT: ConflictException ");
            throw new ConflictException("User alredy exixts!");
        }

        // Create User
        UserEntity user = mapper.toEntity(requestDTO);
        user = repository.save(user);

        logger.info("createUser - OUT: {} ", user.toString());
        return mapper.toDTO(user);
    }

    public UserRegistryResponseDTO findUserRegistry(String fiscalCode) throws NotFoundException {

        logger.info("findUser - IN: fiscalCode({}) ", fiscalCode);

        // Find User
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
    public void editUserRegistry(UserRequestDTOPut requestDTO, String fiscalCode, String password) throws BadRequestException, NotFoundException, ConflictException {

        logger.info("editUser - IN: {}, fiscalCode({}) ", requestDTO.toString(), fiscalCode);

        // Control if User with input FiscalCode exists
        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {

            logger.info("editUser - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }

        // Control if FiscalCode in DTO Not Exists
        if (requestDTO.getFiscalCode() != null) {

            Optional<UserEntity> check = repository.findByFiscalCode(requestDTO.getFiscalCode());
            if (check.isPresent()) {

                logger.info("editUser - OUT: ConflictException ");
                throw new ConflictException("User with input FiscalCode alredy Exists!");
            }
        }
        
        // Encrypt Password
        String encryptedPassword = PasswordUtil.encryptPassword(password);

        // Control if input Password is correct
        if (! user.get().getPassword().equals(encryptedPassword)) {

            logger.info("editUser - OUT: BadRequestException ");
            throw new BadRequestException("Password is not correct!");
        }

        // Edit User
        UserEntity editUser = mapper.editUser(requestDTO, user.get());
        repository.save(editUser);

        logger.info("editUser - OUT: {} ", editUser.toString());  
    }

    @Override
    public void deleteUserRegistry(String fiscalCode, String password) throws NotFoundException, BadRequestException {

        logger.info("deleteUser - IN: fiscalCode({}) ", fiscalCode);

        // Control if User with input FiscalCode exists
        Optional<UserEntity> user = repository.findByFiscalCode(fiscalCode);
        if (user.isEmpty()) {

            logger.info("deleteUser - OUT: NotFoundException ");
            throw new NotFoundException("User not found!");
        }

        // Encrypt Password
        String encryptedPassword = PasswordUtil.encryptPassword(password);

        // Control if input Password is correct
        if (! user.get().getPassword().equals(encryptedPassword)) {

            logger.info("deleteUser - OUT: BadRequestException ");
            throw new BadRequestException("Password is not correct!");
        }

        // Delete User
        repository.delete(user.get());

        logger.info("deleteUser - OUT: {} ", user.get().toString());
    }

    @Override
    public List<UserRegistryResponseDTO> findAllUserRegistry() {

        logger.info("findAll - IN: none ");

        // Find Users
        List<UserEntity> userList = repository.findAll();
        List<UserRegistryResponseDTO> response = mapper.toDTOList(userList);
        
        logger.info("findAll - OUT: {} ", response.toString());
        return response;
    }
    
}
