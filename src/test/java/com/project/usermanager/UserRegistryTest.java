package com.project.usermanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.usermanager.component.UserRegistryServiceComponent;
import com.project.usermanager.component.impl.UserRegistryServiceComponentImpl;
import com.project.usermanager.controller.UserRegistryController;
import com.project.usermanager.controller.impl.UserRegistryControllerImpl;
import com.project.usermanager.dto.request.UserRequestDTO;
import com.project.usermanager.dto.response.UserRegistryResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.UserMapper;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.service.UserRegistryService;
import com.project.usermanager.service.impl.UserRegistryServiceImpl;
import com.project.usermanager.util.PasswordUtil;

@ExtendWith(MockitoExtension.class)
class UserRegistryTest {

    @Mock
    private UserRepository repository;

    private UserRegistryController controller;

    // Data
    private UserRequestDTO requestDTO;
    private UserEntity user;

    @BeforeEach
    void setup() throws BadRequestException {

        // Inizialize Beans
        UserMapper mapper = UserMapper.builder().build();

        UserRegistryService service = UserRegistryServiceImpl.builder()
            .repository(repository)
        .build();

        UserRegistryServiceComponent serviceComponent = UserRegistryServiceComponentImpl.builder()
            .service(service)
            .mapper(mapper)
        .build();

        controller = UserRegistryControllerImpl.builder()
            .serviceComponent(serviceComponent)
        .build();

        // Inzialize Data
        requestDTO = UserRequestDTO.builder()
            .birthDate(LocalDate.now())
            .confirmPassword("passwd")
            .password("passwd")
            .email("email@email.it")
            .username("username")
            .gender("M")
            .lastName("lastname")
            .name("name")
        .build();

        user = UserEntity.builder()
            .birthDate(LocalDate.now())
            .password(PasswordUtil.encryptPassword("passwd"))
            .email("email@email.it")
            .username("username")
            .gender("M")
            .lastName("lastname")
            .name("name")
            .id(new ObjectId())
        .build();

    }

    @Test
    void createUserRegistry() throws BadRequestException, ConflictException {

        // When
        when(repository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(UserEntity.class))).thenReturn(user);
        // Then
        ResponseEntity<UserRegistryResponseDTO> result = controller.createUserRegistry(requestDTO);
        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void createUserRegistryWithConflictException() throws BadRequestException, ConflictException {

        // When
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // Then Assert
        assertThrows(ConflictException.class, () -> { 
            controller.createUserRegistry(requestDTO);
        });
    }

    @Test
    void findUserRegistry() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // Then
        ResponseEntity<UserRegistryResponseDTO> result = controller.findUserRegistry("username");
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void findUserRegistryWithNotFoundException() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(repository.findByUsername(anyString())).thenReturn(Optional.empty());
        // Then Assert
        assertThrows(NotFoundException.class, () -> {
            controller.findUserRegistry("username");
        });
    }

    @Test
    void findAll() {

        // When
        when(repository.findAll()).thenReturn(List.of(user));
        // Then
        ResponseEntity<List<UserRegistryResponseDTO>> result = controller.findAllUserRegistry();
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void editUserRegistry() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // Then
        ResponseEntity<String> result = controller.editUserRegistry(requestDTO, "username", "passwd");
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test
    void editUserRegistryWithNotFoundException() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(repository.findByUsername(anyString())).thenReturn(Optional.empty());
        // Then Assert
        assertThrows(NotFoundException.class, () -> {
            controller.editUserRegistry(requestDTO, "username", "passwd");
        });
    }

    @Test
    void editUserRegistryWithWrongPassword() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // Then Assert
        assertThrows(BadRequestException.class, () -> {
            controller.editUserRegistry(requestDTO, "username", "wrong");
        });
    }
    
}
