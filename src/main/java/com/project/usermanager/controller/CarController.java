package com.project.usermanager.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.exception.ConflictException;
import com.project.usermanager.exception.NotFoundException;

public interface CarController {

    @PostMapping("/api/car/owner/{password}")
    @ResponseStatus(code = HttpStatus.CREATED, value = HttpStatus.CREATED)
    ResponseEntity<CarResponseDTO> createCar (
        @RequestBody @Valid CarRequestDTOPost requestDTO,
        @PathVariable String password
    ) throws BadRequestException, ConflictException, NotFoundException;

    @GetMapping("/api/car/{licensePlate}")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    ResponseEntity<CarResponseDTO> findCar (
        @PathVariable String licensePlate
    ) throws NotFoundException;

    @GetMapping("/api/car")
    @ResponseStatus(code = HttpStatus.OK, value = HttpStatus.OK)
    ResponseEntity<List<CarResponseDTO>> findAll (
        @RequestParam(required = false) String ownerFiscalCode
    ) throws NotFoundException;

    @PutMapping("/api/car/{licensePlate}/owner/{password}")
    @ResponseStatus(code = HttpStatus.CONTINUE, value = HttpStatus.CONTINUE)
    ResponseEntity<String> editCar (
        @RequestBody @Valid CarRequestDTOPut requestDTO, 
        @PathVariable String licensePlate,
        @PathVariable String password
    ) throws BadRequestException, NotFoundException;

    @DeleteMapping("/api/car/{licensePlate}/owner/{password}")
    @ResponseStatus(code = HttpStatus.CONTINUE, value = HttpStatus.CONTINUE)
    ResponseEntity<String> deleteCar (
        @PathVariable String licensePlate,
        @PathVariable String password
    ) throws NotFoundException, BadRequestException;
    
}
