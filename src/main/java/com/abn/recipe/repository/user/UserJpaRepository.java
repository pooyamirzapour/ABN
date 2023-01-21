package com.abn.recipe.repository.user;

import io.micrometer.core.annotation.Timed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import static com.abn.recipe.common.config.MetricConstant.FIND_BY_USERNAME;

/**
 * Repository for User
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Repository
public interface UserJpaRepository extends CrudRepository<UserEntity, Long> {
    @Timed(value = FIND_BY_USERNAME, extraTags = { "column", "username" })
    UserEntity findByUsername(String username);
}