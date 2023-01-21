package com.abn.recipe.security.service;

import com.abn.recipe.security.model.User;

public interface UserRepository {

    User findByUsername(String username);
}
