package com.abn.recipe.security.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Header for creating a part of token.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Setter
@Getter
public class Header implements Serializable {
    private String alg;
    private String type;
    public Header(String alg, String type) {
        this.alg = alg;
        this.type = type;
    }

    public Header() {
    }

}
