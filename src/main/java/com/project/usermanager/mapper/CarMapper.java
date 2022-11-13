package com.project.usermanager.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.exception.BadRequestException;
import com.project.usermanager.model.CarEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CarMapper {

    public CarEntity toEntity(CarRequestDTOPost requestDTO) throws BadRequestException {

        CarEntity car = new CarEntity();
        car.setBrand(requestDTO.getBrand());
        car.setLicensePlate(requestDTO.getLicensePlate());
        car.setModel(requestDTO.getModel());
        car.setOwnerFiscalCode(requestDTO.getOwnerFiscalCode());

        return car;
    }


    public CarResponseDTO toDTO(CarEntity car) {

        CarResponseDTO responseDTO = new CarResponseDTO();
        responseDTO.setBrand(car.getBrand());
        responseDTO.setId(car.getId().toString());
        responseDTO.setLicensePlate(car.getLicensePlate());
        responseDTO.setModel(car.getModel());
        responseDTO.setOwnerFiscalCode(car.getOwnerFiscalCode());

        return responseDTO;
    }

    public List<CarResponseDTO> toDTOList(List<CarEntity> carList) {

        List<CarResponseDTO> responseDTOList = new ArrayList<>();
        for (CarEntity car : carList) {
            responseDTOList.add(this.toDTO(car));
        }

        return responseDTOList;
    }

    public CarEntity editCar(CarRequestDTOPut requestDTO, CarEntity car) throws BadRequestException {

        if (requestDTO.getBrand() != null) {
            car.setBrand(requestDTO.getBrand());
        }
        if (requestDTO.getLicensePlate() != null) {
            car.setLicensePlate(requestDTO.getLicensePlate());
        }
        if (requestDTO.getModel() != null){
            car.setModel(requestDTO.getModel());
        }
        if (requestDTO.getOwnerFiscalCode() != null) {
            car.setOwnerFiscalCode(requestDTO.getOwnerFiscalCode());
        }
      
        return car;
    }
    
}
