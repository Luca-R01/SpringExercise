package com.project.usermanager.model.deleted;

import org.springframework.data.mongodb.core.mapping.Document;

import com.project.usermanager.model.UserEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@Document(collection = "deleted_users")
public class UserEntityDeleted {

    @SuppressWarnings("unused")
    private static final String DELETED = "true";

    private UserEntity userEntity;
    
}
