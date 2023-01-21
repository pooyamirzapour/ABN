package com.abn.recipe.common.transformer;

import com.abn.recipe.api.model.security.UserDTO;
import com.abn.recipe.repository.user.UserEntity;
import com.abn.recipe.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * A transformer responsible for conversion of user
 */
@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    User entityToUser(UserEntity user);

    UserEntity dtoToEntity(UserDTO userDTO);

}
