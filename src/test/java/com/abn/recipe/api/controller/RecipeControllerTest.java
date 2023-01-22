package com.abn.recipe.api.controller;

import com.abn.recipe.api.model.recipe.CreatedResponseDTO;
import com.abn.recipe.api.model.recipe.RecipeRequestDTO;
import com.abn.recipe.api.model.recipe.RecipeTypeEnum;
import com.abn.recipe.domain.model.SearchResponseDTO;
import com.abn.recipe.repository.ingredient.IngredientEntity;
import com.abn.recipe.repository.ingredient.IngredientJpaRepository;
import com.abn.recipe.repository.recipe.RecipeEntity;
import com.abn.recipe.repository.recipe.RecipeJpaRepository;
import com.abn.recipe.repository.user.UserEntity;
import com.abn.recipe.security.service.JwtUserDetailsService;
import com.abn.recipe.security.utility.CryptoToken;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecipeControllerTest {

    @LocalServerPort
    private int localPort;
    @Autowired
    public TestRestTemplate rest;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private IngredientJpaRepository ingredientJpaRepository;
    @Autowired
    private RecipeJpaRepository recipeJpaRepository;
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    private String token;
    HttpHeaders headers;

    @BeforeAll
    void getToken() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123456");
        userEntity.setUsername("Mark");
        userEntity = userDetailsService.save(userEntity);
        token = CryptoToken.generateToken(userEntity);
        headers = prepareHeader();
    }

    @AfterAll
    void afterAll() {
       recipeJpaRepository.deleteAll();
       ingredientJpaRepository.deleteAll();
    }

    private HttpHeaders prepareHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(AUTHORIZATION, BEARER + token);
        return headers;
    }

    private RecipeEntity saveRecipe(Set<IngredientEntity> ingredients, String name) {
        RecipeEntity recipe =
                new RecipeEntity();
        recipe.setIngredients(ingredients);
        recipe.setName(name);
        recipe.setNumberOfServings(1);
        recipe.setType(RecipeTypeEnum.VEGETARIAN.name());
        recipe.setInstruction("Oven");
        return recipeJpaRepository.save(recipe);
    }

    private IngredientEntity saveIngredient(String name) {
        IngredientEntity ingredient = new IngredientEntity();
        ingredient.setIngredient(name);
        return ingredientJpaRepository.save(ingredient);
    }

    @Test
    void given_validUser_when_callPost_then_createRecipe() {

        IngredientEntity save = saveIngredient("water");
        Integer id = save.getId();

        Set<Integer> ingredients = new HashSet<>();
        ingredients.add(id);

        RecipeRequestDTO recipeRequestDTO =
                new RecipeRequestDTO("Pizza", 1, ingredients, "oven", RecipeTypeEnum.VEGETARIAN);

        HttpEntity<?> entity = new HttpEntity<>(recipeRequestDTO, headers);
        String uri = String.format("http://localhost:%s/recipes", localPort);

        //when:
        ResponseEntity<CreatedResponseDTO> response =
                rest.exchange(uri, HttpMethod.POST, entity, CreatedResponseDTO.class);

        //then:
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void given_validUser_when_callDelete_then_deleteRecipe() {

        IngredientEntity save = saveIngredient("pepper");

        Set<IngredientEntity> ingredients = new HashSet<>();
        ingredients.add(save);

        RecipeEntity recipeEntity = saveRecipe(ingredients, "Hamburger");

        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        String uri = String.format("http://localhost:%s/recipes/" + recipeEntity.getId(), localPort);

        //when:
        ResponseEntity<CreatedResponseDTO> response =
                rest.exchange(uri, HttpMethod.DELETE, entity, CreatedResponseDTO.class);

        //then:
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }



    @Test
    void given_validUser_when_callGetById_then_getRecipe() {

        IngredientEntity save = saveIngredient("salt");

        Set<IngredientEntity> ingredients = new HashSet<>();
        ingredients.add(save);

        RecipeEntity recipe = saveRecipe(ingredients, "Chicken");
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        String uri = String.format("http://localhost:%s/recipes/" + recipe.getId(), localPort);

        //when:
        ResponseEntity<CreatedResponseDTO> response =
                rest.exchange(uri, HttpMethod.GET, entity, CreatedResponseDTO.class);

        //then:
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    void given_validUser_when_callUpdate_then_returnRecipe() {

        IngredientEntity save = saveIngredient("bread");

        Set<IngredientEntity> ingredients = new HashSet<>();
        ingredients.add(save);

        RecipeEntity recipe = saveRecipe(ingredients, "Pasta");
        Set<Integer> collect =
                recipe.getIngredients().stream().map(IngredientEntity::getId).collect(Collectors.toSet());

        RecipeRequestDTO recipeRequestDTO =
                new RecipeRequestDTO(recipe.getId(), recipe.getName(), recipe.getNumberOfServings(), collect,
                        recipe.getInstruction(),
                        RecipeTypeEnum.VEGAN);

        HttpEntity<?> entity = new HttpEntity<>(recipeRequestDTO, headers);
        String uri = String.format("http://localhost:%s/recipes/", localPort);

        //when:
        ResponseEntity<CreatedResponseDTO> response =
                rest.exchange(uri, HttpMethod.PATCH, entity, CreatedResponseDTO.class);

        //then:
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void given_validUser_when_callSearch_then_returnResult() {

        IngredientEntity ingredient1 = saveIngredient("Cucumber2");
        IngredientEntity ingredient2 = saveIngredient("Milk");
         saveIngredient("Butter");

        Set<IngredientEntity> ingredients = new HashSet<>();
        ingredients.add(ingredient1);
        ingredients.add(ingredient2);

         saveRecipe(ingredients, "Sushi");


        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        String uri = String.format("http://localhost:%s/recipes?page=0&size=1&includes=Cucumber&excludes=Butter&noserving=1&instruction=oven&type=VEGETARIAN", localPort);

        //when:
        ResponseEntity<SearchResponseDTO> response =
                rest.exchange(uri, HttpMethod.GET, entity, SearchResponseDTO.class);

        //then:
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        //TODO: add more assertions
        }

}