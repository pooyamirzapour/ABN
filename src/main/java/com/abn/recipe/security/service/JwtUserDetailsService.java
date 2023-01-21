package com.abn.recipe.security.service;

import com.abn.recipe.repository.user.UserEntity;
import com.abn.recipe.common.exception.ABNServiceException;
import com.abn.recipe.common.exception.ErrorCode;
import com.abn.recipe.repository.user.UserJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Jwt user details service class
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    public static final int USER_DAYS_VALIDITY = 1;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get user by username
     *
     * @param username - User name
     * @return UserDetails - User Detail
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userJpaRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    /**
     * Get user from db by username
     *
     * @param username - User name
     * @return UserEntity - User Entity
     */
    public UserEntity loadUserEntityByUsername(String username) {
        UserEntity user = userJpaRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    /**
     * Save user into db
     *
     * @param newUser - New user
     * @return UserEntity - User Entity
     */
    public UserEntity save(UserEntity newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setTokenExpireDate(LocalDateTime.now().plusDays(USER_DAYS_VALIDITY));
        try {
            newUser = userJpaRepository.save(newUser);
        } catch (DataIntegrityViolationException exception) {
            throw new ABNServiceException("User registered before", ErrorCode.USER_REGISTER_BEFORE,
                    HttpStatus.BAD_REQUEST);
        }
        return newUser;
    }
}