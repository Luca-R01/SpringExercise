package com.project.usermanager.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.project.usermanager.dto.request.car.CarRequestDTOPost;
import com.project.usermanager.dto.request.car.CarRequestDTOPut;
import com.project.usermanager.dto.response.CarResponseDTO;
import com.project.usermanager.model.CarEntity;

import lombok.Builder;

@Builder
@Component
public class CarMapper {

    private static final Logger logger = LoggerFactory.getLogger(CarMapper.class);

    public CarEntity toEntity(CarRequestDTOPost requestDTO) {

        logger.info("toEntity - IN: {} ", requestDTO.toString());

        CarEntity car = CarEntity.builder()
            .brand(requestDTO.getBrand())
            .licensePlate(requestDTO.getLicensePlate())
            .model(requestDTO.getModel())
            .ownerFiscalCode(requestDTO.getOwnerFiscalCode())
        .build();

        logger.info("toEntity - OUT: {} ", car.toString());
        return car;
    }


    public CarResponseDTO toDTO(CarEntity car) {

        logger.info("toDTO - IN: {} ", car.toString());

        CarResponseDTO responseDTO = CarResponseDTO.builder()
            .brand(car.getBrand())
            .id(car.getId().toString())
            .licensePlate(car.getLicensePlate())
            .model(car.getModel())
            .ownerFiscalCode(car.getOwnerFiscalCode())
        .build();

        logger.info("toDTO - OUT: {} ", responseDTO.toString());
        return responseDTO;
    }

    public List<CarResponseDTO> toDTOList(List<CarEntity> carList) {

        logger.info("toDTOList - IN: {} ", carList.toString());

        List<CarResponseDTO> responseDTOList = new ArrayList<>();
        for (CarEntity car : carList) {
            responseDTOList.add(this.toDTO(car));
        }

        logger.info("toDTOList - OUT: {} ", responseDTOList.toString());
        return responseDTOList;
    }

    public CarEntity editCar(CarRequestDTOPut requestDTO, CarEntity car) {

        logger.info("editCar - IN: {}, {} ", requestDTO.toString(), car.toString());

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
      
        logger.info("editCar - OUT: {} ", car.toString());
        return car;
    }
    
}
