package com.abn.recipe.security.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class User {
    private long id;
    private String username;
    private String password;
    private LocalDateTime tokenExpireDate;

}
