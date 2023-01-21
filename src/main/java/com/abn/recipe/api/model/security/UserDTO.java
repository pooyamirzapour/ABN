package com.abn.recipe.api.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User data transfer object.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    @NotNull(message = "username not sent")
    @NotBlank(message = "username not sent")
    private String username;
    @NotNull(message = "password not sent")
    @NotBlank(message = "password not sent")
    private String password;

}