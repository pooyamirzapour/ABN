package com.abn.recipe.api.controller;

import com.abn.recipe.api.model.security.JwtResponseDTO;
import com.abn.recipe.api.model.security.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationControllerTest  {

    @LocalServerPort
    private int localPort;

    @Autowired
    public TestRestTemplate rest;

    @Test
    void given_validUser_when_callApi_then_createToken() {
        //given:
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("123456");
        userDTO.setUsername("Pooya");
        HttpEntity<?> entity = new HttpEntity<>(userDTO, headers);
        String uri = String.format("http://localhost:%s/authenticate/register", localPort);

        //when:
        ResponseEntity<JwtResponseDTO> response = rest.exchange(uri, HttpMethod.PUT, entity, JwtResponseDTO.class);

        //then:
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


}