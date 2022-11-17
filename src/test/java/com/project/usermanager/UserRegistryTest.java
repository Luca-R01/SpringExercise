package com.project.usermanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.http.HttpStatus;
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
class UserRegistryTest {

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
        requestDTOPost = UserRequestDTOPost.builder()
            .birthDate(LocalDate.now())
            .confirmPassword("passwd")
            .password("passwd")
            .email("email@email.it")
            .fiscalCode("fiscalcode")
            .gender("M")
            .lastName("lastname")
            .name("name")
        .build();

        requestDTOPut = UserRequestDTOPut.builder()
            .name("New Name")
        .build();

        UserEntity user = UserEntity.builder()
            .birthDate(LocalDate.now())
            .password("passwd")
            .email("email@email.it")
            .fiscalCode("fiscalcode")
            .gender("M")
            .lastName("lastname")
            .name("name")
            .id(new ObjectId())
        .build();

        optionalUser = Optional.of(user);

        emptyOptionalUser = Optional.empty();

    }

    @Test
    void createUserRegistry() throws BadRequestException, ConflictException {

        when(repository.findByFiscalCode(anyString())).thenReturn(emptyOptionalUser);
        when(repository.save(any(UserEntity.class))).thenReturn(optionalUser.get());
        ResponseEntity<UserRegistryResponseDTO> result = controller.createUserRegistry(requestDTOPost);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void findUserRegistry() throws BadRequestException, ConflictException, NotFoundException {

        when(repository.findByFiscalCode(anyString())).thenReturn(optionalUser);
        ResponseEntity<UserRegistryResponseDTO> result = controller.findUserRegistry("fiscalcode");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void editUserRegistry() throws BadRequestException, ConflictException, NotFoundException {

        when(repository.findByFiscalCodeAndPassword(anyString(), anyString())).thenReturn(optionalUser);
        ResponseEntity<String> result = controller.editUserRegistry(requestDTOPut, "fiscalcode", "passwd");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test 
    void deleteUserRegistry() throws NotFoundException {

        when(repository.findByFiscalCodeAndPassword(anyString(), anyString())).thenReturn(optionalUser);
        ResponseEntity<String> result = controller.deleteUserRegistry("fiscalcode", "passwd");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
    
}
