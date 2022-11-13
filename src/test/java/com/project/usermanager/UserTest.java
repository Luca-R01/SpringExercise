package com.project.usermanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.project.usermanager.controller.UserController;
import com.project.usermanager.controller.impl.UserControllerImpl;
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
import com.project.usermanager.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository repository;

    private UserController controller;

    private UserRequestDTOPost requestDTOPost;
    private UserRequestDTOPut requestDTOPut;
    private Optional<UserEntity> optionalUser;
    private Optional<UserEntity> emptyOptionalUser;

    @BeforeEach
    void setup() throws BadRequestException {

        UserMapper mapper = new UserMapper();
        UserService service = new UserServiceImpl(repository, mapper);
        controller = new UserControllerImpl(service);

        // Inzialaze Data
        requestDTOPost = new UserRequestDTOPost();
        requestDTOPost.setBirthDate(LocalDate.parse("2001-11-06"));
        requestDTOPost.setConfirmPassword("password");
        requestDTOPost.setPassword("password");
        requestDTOPost.setEmail("email@email.it");
        requestDTOPost.setFiscalCode("fiscalcode");
        requestDTOPost.setGender("M");
        requestDTOPost.setLastName("lastname");
        requestDTOPost.setName("name");

        requestDTOPut = new UserRequestDTOPut();
        requestDTOPut.setName("new name");

        UserEntity user = new UserEntity();
        user.setBirthDate(LocalDate.parse("2001-11-06"));
        user.setPassword("password");
        user.setEmail("email@email.it");
        user.setFiscalCode("fiscalcode");
        user.setGender("M");
        user.setLastName("lastname");
        user.setName("name");
        user.setId(new ObjectId());

        optionalUser = Optional.of(user);

        emptyOptionalUser = Optional.empty();

    }

    @Test
    void createUser() throws BadRequestException, ConflictException {

        when(repository.findByFiscalCode(anyString())).thenReturn(emptyOptionalUser);
        controller.createUser(requestDTOPost);
    }

    @Test
    void findUser() throws BadRequestException, ConflictException, NotFoundException {

        when(repository.findByFiscalCode(anyString())).thenReturn(optionalUser);
        ResponseEntity<UserResponseDTO> result = controller.findUser("fiscalcode");
        assertEquals("fiscalcode", result.getBody().getFiscalCode());
    }

    @Test
    void editUser() throws BadRequestException, ConflictException, NotFoundException {

        when(repository.findByFiscalCode(anyString())).thenReturn(optionalUser);
        controller.editUser(requestDTOPut, "fiscalcode");
    }

    @Test 
    void deleteUser() throws NotFoundException {

        when(repository.findByFiscalCode(anyString())).thenReturn(optionalUser);
        controller.deleteUser("fiscalcode");
    }
    
}
