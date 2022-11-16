package com.project.usermanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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

import com.project.usermanager.controller.UserRegistryController;
import com.project.usermanager.controller.impl.UserRegistryControllerImpl;
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
import com.project.usermanager.service.impl.UserRegistryServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private UserRepository repository;

    private UserRegistryController controller;

    private UserRequestDTOPost requestDTOPost;
    private UserRequestDTOPut requestDTOPut;
    private Optional<UserEntity> optionalUser;
    private Optional<UserEntity> emptyOptionalUser;

    @BeforeEach
    void setup() throws BadRequestException {

        UserMapper mapper = new UserMapper();
        UserRegistryService service = new UserRegistryServiceImpl(repository, mapper);
        controller = new UserRegistryControllerImpl(service);

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
    void createUserRegistry() throws BadRequestException, ConflictException {

        when(repository.findByFiscalCode(anyString())).thenReturn(emptyOptionalUser);
        ResponseEntity<UserRegistryResponseDTO> result = controller.createUserRegistry(requestDTOPost);
        assertEquals(requestDTOPost.getName(), result.getBody().getName());
    }

    @Test
    void findUserRegistry() throws BadRequestException, ConflictException, NotFoundException {

        when(repository.findByFiscalCode(anyString())).thenReturn(optionalUser);
        ResponseEntity<UserRegistryResponseDTO> result = controller.findUserRegistry("fiscalcode");
        assertEquals("fiscalcode", result.getBody().getFiscalCode());
    }

    @Test
    void editUserRegistry() throws BadRequestException, ConflictException, NotFoundException {

        when(repository.findByFiscalCode(anyString())).thenReturn(optionalUser);
        controller.editUserRegistry(requestDTOPut, "fiscalcode");
    }

    @Test 
    void deleteUserRegistry() throws NotFoundException {

        when(repository.findByFiscalCode(anyString())).thenReturn(optionalUser);
        controller.deleteUserRegistry("fiscalcode");
    }
    
}
