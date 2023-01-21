package com.abn.recipe.cucumber;

import com.abn.recipe.config.TestConfig;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class Steps  {


    @Given("^Stub (.*)$")
    public void given(String name) {
        System.out.println("Given");
    }


    @When("Request is processed")
    public void when() {
        System.out.println("When");
    }

    @Then("^Response matches (.*)$")
    public void then(String location) {
        System.out.println("Then");
    }

}
