package com.project.usermanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.project.usermanager.component.UserFullServiceComponent;
import com.project.usermanager.component.impl.UserFullServiceComponentImpl;
import com.project.usermanager.controller.UserFullController;
import com.project.usermanager.controller.impl.UserFullControllerImpl;
import com.project.usermanager.dto.response.UserFullResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.NotFoundException;
import com.project.usermanager.mapper.CarMapper;
import com.project.usermanager.mapper.UserFullMapper;
import com.project.usermanager.mapper.UserMapper;
import com.project.usermanager.model.CarEntity;
import com.project.usermanager.model.UserEntity;
import com.project.usermanager.repository.CarRepository;
import com.project.usermanager.repository.UserRepository;
import com.project.usermanager.service.CarService;
import com.project.usermanager.service.UserRegistryService;
import com.project.usermanager.service.impl.CarServiceImpl;
import com.project.usermanager.service.impl.UserRegistryServiceImpl;
import com.project.usermanager.util.PasswordUtil;

@ExtendWith(MockitoExtension.class)
class UserFullTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    private UserFullController controller;

    // Data
    private UserEntity user;
    private List<CarEntity> carsList;

    @BeforeEach
    void setup() {

        // Inizialize Beans
        UserMapper userMapper = UserMapper.builder().build();
        CarMapper carMapper = CarMapper.builder().build();

        UserFullMapper mapper = UserFullMapper.builder()
            .carMapper(carMapper)
            .userMapper(userMapper)
        .build();

        CarService carService = CarServiceImpl.builder()
            .repository(carRepository)
            .userRepository(userRepository)
        .build();

        UserRegistryService userRegistryService = UserRegistryServiceImpl.builder()
            .repository(userRepository)
        .build();

        UserFullServiceComponent serviceComponent = UserFullServiceComponentImpl.builder()
            .carService(carService)
            .userRegistryService(userRegistryService)
            .mapper(mapper)
        .build();

        controller = UserFullControllerImpl.builder()
            .serviceComponent(serviceComponent)
        .build();

        // Inizialize Data
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

        CarEntity car = CarEntity.builder()
            .brand("brand")
            .licensePlate("licenseplate")
            .model("mode")
            .ownerUsername("username")
            .id(new ObjectId())
        .build();

        carsList = List.of(car);

    }

    @Test
    void findUser() throws NotFoundException {

        // When
        when(carRepository.findAllByOwnerUsername(anyString())).thenReturn(carsList);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // Then
        ResponseEntity<UserFullResponseDTO> result = controller.findUser("username");
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void findAllUser() throws NotFoundException {

        // When
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(carRepository.findAllByOwnerUsername(anyString())).thenReturn(carsList);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        // Then
        ResponseEntity<List<UserFullResponseDTO>> result = controller.findAllUser();
        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    void deleteUserAndCars() throws NotFoundException, BadRequestException {

        // When
        when(carRepository.findAllByOwnerUsername(anyString())).thenReturn(carsList);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(user));
        // Then
        ResponseEntity<String> result = controller.deleteUserAndCars("username", "passwd");
        // Assert
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    }
    
}
