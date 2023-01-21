package com.abn.recipe.api.model.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Jwt request data transfer object.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Getter
@Setter
@NoArgsConstructor
public class JwtRequestDTO implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;
    @NotBlank(message = "user name can not be blank")
    @NotNull(message = "user name can not be blank")
    private String username;

    @NotBlank(message = "password can not be blank")
    @NotNull(message = "password can not be blank")
    private String password;

}