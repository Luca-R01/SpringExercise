package com.project.usermanager.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CarMapper.class);

    public CarEntity toEntity(CarRequestDTOPost requestDTO) throws BadRequestException {

        logger.info("toEntity - IN: {} ", requestDTO.toString());

        CarEntity car = new CarEntity();
        car.setBrand(requestDTO.getBrand());
        car.setLicensePlate(requestDTO.getLicensePlate());
        car.setModel(requestDTO.getModel());
        car.setOwnerFiscalCode(requestDTO.getOwnerFiscalCode());

        logger.info("toEntity - OUT: {} ", car.toString());
        return car;
    }


    public CarResponseDTO toDTO(CarEntity car) {

        logger.info("toDTO - IN: {} ", car.toString());

        CarResponseDTO responseDTO = new CarResponseDTO();
        responseDTO.setBrand(car.getBrand());
        responseDTO.setId(car.getId().toString());
        responseDTO.setLicensePlate(car.getLicensePlate());
        responseDTO.setModel(car.getModel());
        responseDTO.setOwnerFiscalCode(car.getOwnerFiscalCode());

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

    public CarEntity editCar(CarRequestDTOPut requestDTO, CarEntity car) throws BadRequestException {

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
