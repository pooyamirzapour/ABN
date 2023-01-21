package com.abn.recipe.repository.user;

import com.abn.recipe.common.transformer.UserConverter;
import com.abn.recipe.security.model.User;
import com.abn.recipe.security.service.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findByUsername(String username) {
        UserEntity entity = userJpaRepository.findByUsername(username);
        return UserConverter.INSTANCE.entityToUser(entity);
    }
}
