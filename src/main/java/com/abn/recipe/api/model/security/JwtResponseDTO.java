package com.abn.recipe.api.model.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Jwt response data transfer object.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDTO implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private String jwtToken;

}