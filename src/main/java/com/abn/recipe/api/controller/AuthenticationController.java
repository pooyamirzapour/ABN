package com.abn.recipe.api.controller;

import com.abn.recipe.api.model.security.JwtRequestDTO;
import com.abn.recipe.api.model.security.JwtResponseDTO;
import com.abn.recipe.api.model.security.UserDTO;
import com.abn.recipe.common.transformer.UserConverter;
import com.abn.recipe.repository.user.UserEntity;
import com.abn.recipe.security.service.JwtUserDetailsService;
import com.abn.recipe.security.utility.CryptoToken;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest Controller class to authenticate and register users.
 */

@RestController
@RequestMapping("/authenticate")
@AllArgsConstructor
@Slf4j
public class AuthenticationController {
    private JwtUserDetailsService userDetailsService;

    /**
     * @param user - User
     * @return JwtResponse
     */
    @PutMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Register.", nickname = "Register user", notes = "Get a token")
    public JwtResponseDTO register(@RequestBody @Valid UserDTO user) {
        log.info("Get a token");
        UserEntity userEntity = userDetailsService.save(UserConverter.INSTANCE.dtoToEntity(user));
        return new JwtResponseDTO(CryptoToken.generateToken(userEntity));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ApiOperation(value = "Login.", nickname = "Login", notes = "Get Token Based on Registered User")
    public JwtResponseDTO login(@RequestParam JwtRequestDTO authenticationRequest) {
        //TODO: should be implemented
        return null;
    }

}