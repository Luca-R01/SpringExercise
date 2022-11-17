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

import com.project.usermanager.controller.CarController;
import com.project.usermanager.controller.impl.CarControllerImpl;
import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
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

@ExtendWith(MockitoExtension.class)
class CarTest {

    @Mock
    private CarRepository repository;

    @Mock
    private UserRepository userRepository;

    private CarController controller;

    private CarRequestDTOPost requestDTOPost;
    private CarRequestDTOPut requestDTOPut;
    private Optional<CarEntity> optionalCar;
    private Optional<CarEntity> emptyOptionalCar;
    private Optional<UserEntity> ownerOptional;

    @BeforeEach
    void setup() throws BadRequestException {

        CarMapper mapper = new CarMapper();
        CarService service = new CarServiceImpl(repository, userRepository, mapper);
        controller = new CarControllerImpl(service);

        // Inzialaze Data
        requestDTOPost = CarRequestDTOPost.builder()
            .brand("brand")
            .licensePlate("licenseplate")
            .model("mode")
            .ownerFiscalCode("fiscalcode")
        .build();

        requestDTOPut = CarRequestDTOPut.builder()
            .brand("brand")
        .build();

        CarEntity car = CarEntity.builder()
            .brand("brand")
            .licensePlate("licenseplate")
            .model("mode")
            .ownerFiscalCode("fiscalcode")
            .id(new ObjectId())
        .build();

        optionalCar = Optional.of(car);

        emptyOptionalCar = Optional.empty();

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

        ownerOptional = Optional.of(user);

    }

    @Test
    void createCar() throws BadRequestException, ConflictException, NotFoundException {

        when(userRepository.findByFiscalCodeAndPassword(anyString(), anyString())).thenReturn(ownerOptional);
        when(repository.findByLicensePlate(anyString())).thenReturn(emptyOptionalCar);
        when(repository.save(any(CarEntity.class))).thenReturn(optionalCar.get());
        ResponseEntity<CarResponseDTO> result = controller.createCar(requestDTOPost, "passwd");
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void findCar() throws BadRequestException, ConflictException, NotFoundException {

        when(repository.findByLicensePlate(anyString())).thenReturn(optionalCar);
        ResponseEntity<CarResponseDTO> result = controller.findCar("licenseplate");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void editCar() throws BadRequestException, ConflictException, NotFoundException {

        when(userRepository.findByFiscalCodeAndPassword(anyString(), anyString())).thenReturn(ownerOptional);
        when(repository.findByLicensePlate(anyString())).thenReturn(optionalCar);
        ResponseEntity<String> result = controller.editCar(requestDTOPut, "licenseplate", "passwd");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }

    @Test 
    void deleteCar() throws NotFoundException {

        when(userRepository.findByFiscalCodeAndPassword(anyString(), anyString())).thenReturn(ownerOptional);
        when(repository.findByLicensePlate(anyString())).thenReturn(optionalCar);
        ResponseEntity<String> result = controller.deleteCar("licenseplate", "passwd");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
    
}
