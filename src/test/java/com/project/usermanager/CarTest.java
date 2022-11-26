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

import com.project.usermanager.component.CarServiceComponent;
import com.project.usermanager.component.impl.CarServiceComponentImpl;
import com.project.usermanager.controller.CarController;
import com.project.usermanager.controller.impl.CarControllerImpl;
import com.project.usermanager.dto.request.CarRequestDTO;
import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.CarMapper;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.repository.CarRepository;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.service.CarService;
import com.project.usermanager.service.impl.CarServiceImpl;
import com.project.usermanager.util.PasswordUtil;

@ExtendWith(MockitoExtension.class)
class CarTest {

    @Mock
    private CarRepository repository;

    @Mock
    private UserRepository userRepository;

    private CarController controller;

    // Data
    private CarRequestDTO requestDTO;
    private CarEntity car;
    private UserEntity user;

    @BeforeEach
    void setup() throws BadRequestException {

        // Inizialize Beans
        CarMapper mapper = CarMapper.builder().build();

        CarService service = CarServiceImpl.builder()
            .repository(repository)
            .userRepository(userRepository)
        .build();

        CarServiceComponent serviceComponent = CarServiceComponentImpl.builder()
            .service(service)
            .mapper(mapper)
        .build();

        controller = CarControllerImpl.builder()
            .serviceComponent(serviceComponent)
        .build();

        // Inzialize Data
        requestDTO = CarRequestDTO.builder()
            .brand("brand")
            .licensePlate("licenseplate")
            .model("mode")
            .ownerUsername("username")
        .build();

        car = CarEntity.builder()
            .brand("brand")
            .licensePlate("licenseplate")
            .model("mode")
            .ownerUsername("username")
            .id(new ObjectId())
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
    void createCar() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(repository.findByLicensePlate(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(CarEntity.class))).thenReturn(car);
        // Then
        ResponseEntity<CarResponseDTO> result = controller.createCar(requestDTO, "passwd");
        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void createCarWithConflictException() throws BadRequestException, ConflictException {

        // When
        when(repository.findByLicensePlate(anyString())).thenReturn(Optional.of(car));
        // Then Assert
        assertThrows(ConflictException.class, () -> { 
            controller.createCar(requestDTO, "passwd");
        });
    }

    @Test
    void createCarWithWrongPassword() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(repository.findByLicensePlate(anyString())).thenReturn(Optional.empty());
        // Then Assert
        assertThrows(BadRequestException.class, () -> {
            controller.createCar(requestDTO, "wrongPassword");
        });
    }

    @Test
    void createCarWithNoOwner() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(repository.findByLicensePlate(anyString())).thenReturn(Optional.empty());
        // Then Assert
        assertThrows(NotFoundException.class, () -> {
            controller.createCar(requestDTO, "passwd");
        });
    }

    @Test
    void findCar() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(repository.findByLicensePlate(anyString())).thenReturn(Optional.of(car));
        // Then
        ResponseEntity<CarResponseDTO> result = controller.findCar("licenseplate");
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void findAll() throws NotFoundException {

        // When
        when(repository.findAll()).thenReturn(List.of(car));
        // Then
        ResponseEntity<List<CarResponseDTO>> result = controller.findAll(null);
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void findAllByOwner() throws NotFoundException {

        // When
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(repository.findAllByOwnerUsername(anyString())).thenReturn(List.of(car));
        // Then
        ResponseEntity<List<CarResponseDTO>> result = controller.findAll("username");
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void editCar() throws BadRequestException, ConflictException, NotFoundException {

        // When
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(user));
        when(repository.findByLicensePlate(anyString())).thenReturn(Optional.of(car));
        // Then
        ResponseEntity<String> result = controller.editCar(requestDTO, "licenseplate", "passwd");
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test 
    void deleteCar() throws NotFoundException, BadRequestException {

        // When
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(user));
        when(repository.findByLicensePlate(anyString())).thenReturn(Optional.of(car));
        // Then
        ResponseEntity<String> result = controller.deleteCar("licenseplate", "passwd");
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
    
}
