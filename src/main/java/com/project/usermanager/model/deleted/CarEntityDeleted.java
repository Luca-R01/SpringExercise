package com.project.usermanager.model.deleted;

import org.springframework.data.mongodb.core.mapping.Document;

import com.project.usermanager.model.CarEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@Document(collection = "deleted_cars")
public class CarEntityDeleted {

    @SuppressWarnings("unused")
    private static final String DELETED = "TRUE";

    private CarEntity carEntity;
    
}
