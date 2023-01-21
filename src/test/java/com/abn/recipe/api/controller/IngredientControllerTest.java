package com.abn.recipe.api.controller;

import com.abn.recipe.api.model.ingredient.IngredientDTO;
import com.abn.recipe.api.model.recipe.CreatedResponseDTO;
import com.abn.recipe.repository.ingredient.IngredientEntity;
import com.abn.recipe.repository.ingredient.IngredientJpaRepository;
import com.abn.recipe.repository.user.UserEntity;
import com.abn.recipe.security.service.JwtUserDetailsService;
import com.abn.recipe.security.utility.CryptoToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
class IngredientControllerTest {

    @LocalServerPort
    private int localPort;

    @Autowired
    public TestRestTemplate rest;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private IngredientJpaRepository ingredientJpaRepository;

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private String token;

    @BeforeAll
    void getToken() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123456");
        userEntity.setUsername("pooya1");
        userEntity = userDetailsService.save(userEntity);
        token = CryptoToken.generateToken(userEntity);
    }

    @Test
    void given_validUser_when_callPost_then_createIngredient() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(AUTHORIZATION, BEARER + token);
        //given:
        IngredientDTO ingredient = new IngredientDTO("salt");
        HttpEntity<?> entity = new HttpEntity<>(ingredient, headers);
        String uri = String.format("http://localhost:%s/ingredient", localPort);

        //when:
        ResponseEntity<CreatedResponseDTO> response =
                rest.exchange(uri, HttpMethod.POST, entity, CreatedResponseDTO.class);

        //then:
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void given_validUser_when_callDelete_then_deleteIngredient() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(AUTHORIZATION, BEARER + token);
        //given:
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setIngredient("salt");

        IngredientEntity save = ingredientJpaRepository.save(ingredient);
        Integer id = save.getId();
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        String uri = String.format("http://localhost:%s/ingredient/" + id, localPort);

        //when:
        ResponseEntity<Void> response =rest.exchange(uri, HttpMethod.DELETE, entity, void.class);


        //then:
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void given_validUser_when_callGetById_then_getIngredient() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(AUTHORIZATION, BEARER + token);
        //given:
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setIngredient("salt");

        IngredientEntity save = ingredientJpaRepository.save(ingredient);
        Integer id = save.getId();
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        String uri = String.format("http://localhost:%s/ingredient/" + id, localPort);

        //when:
        ResponseEntity<IngredientDTO> response =rest.exchange(uri, HttpMethod.GET, entity, IngredientDTO.class);


        //then:
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}