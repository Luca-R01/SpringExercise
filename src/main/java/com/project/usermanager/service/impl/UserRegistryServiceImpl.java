package com.project.usermanager.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
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

    private static final Logger logger = LoggerFactory.getLogger(UserRegistryServiceImpl.class);

    @Override
    public UserEntity createUserRegistry(UserEntity user) throws BadRequestException, ConflictException {

        logger.info("createUser - IN: {} ", user.toString());

        // Control if User already exists
        Optional<UserEntity> findUser = repository.findByUsername(user.getUsername());
        if (findUser.isPresent()) {

            logger.info("createUser - OUT: ConflictException(User alredy exixts!) ");
            throw new ConflictException("User alredy exixts!");
        }

        // Create User
        user = repository.save(user);

        logger.info("createUser - OUT: {} ", user.toString());
        return user;
    }

    public UserEntity findUserRegistry(String username) throws NotFoundException {

        logger.info("findUser - IN: username({}) ", username);

        // Find User
        Optional<UserEntity> user = repository.findByUsername(username);
        if (user.isEmpty()) {

            logger.info("findUser - OUT: NotFoundException(User not found!) ");
            throw new NotFoundException("User not found!");
        }

        logger.info("findUser - OUT: {} ", user.get().toString());
        return user.get();
    }

    @Override
    public void editUserRegistry(UserEntity newUser, String username, String password) throws BadRequestException, NotFoundException, ConflictException {

        logger.info("editUser - IN: {}, username({}) ", newUser.toString(), username);

        // Control if User with input Username exists
        UserEntity user = this.findUserRegistry(username);

        // Control if Username in DTO Not Exists
        if (! newUser.getUsername().equals(user.getUsername())) {

            Optional<UserEntity> check = repository.findByUsername(user.getUsername());
            if (check.isPresent()) {

                logger.info("editUser - OUT: ConflictException(User with input Username alredy Exists!) ");
                throw new ConflictException("User with input Username alredy Exists!");
            }
        }
        
        // Encrypt Password
        String encryptedPassword = PasswordUtil.encryptPassword(password);

        // Control if input Password is correct
        if (! user.getPassword().equals(encryptedPassword)) {

            logger.info("editUser - OUT: BadRequestException(Password is not correct!) ");
            throw new BadRequestException("Password is not correct!");
        }

        // Edit User
        newUser.setId(user.getId());
        repository.save(newUser);

        logger.info("editUser - OUT: {} ", newUser.toString());  
    }

    @Override
    public List<UserEntity> findAllUserRegistry() {

        logger.info("findAll - IN: none ");

        // Find Users
        List<UserEntity> userList = repository.findAll();
        
        logger.info("findAll - OUT: {} ", userList.toString());
        return userList;
    }

    @Override
    public void deleteUserRegistry(String username, String password) throws NotFoundException, BadRequestException {

        logger.info("deleteUserRegistry - IN: username({}) ", username);
        
        // Control if User with input Username exists
        UserEntity user = this.findUserRegistry(username);

        // Encrypt Password
        String encryptedPassword = PasswordUtil.encryptPassword(password);

        // Control if input Password is correct
        if (! user.getPassword().equals(encryptedPassword)) {

            logger.info("editUser - OUT: BadRequestException(Password is not correct!) ");
            throw new BadRequestException("Password is not correct!");
        }

        // Delete 
        repository.delete(user);

        logger.info("deleteUserRegistry - OUT: {} ", user.toString());
        
    }
    
}
