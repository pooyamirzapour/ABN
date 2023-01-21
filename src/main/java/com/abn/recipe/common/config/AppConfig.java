package com.abn.recipe.common.config;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Config class.
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Component
@AllArgsConstructor
public
class AppConfig {
    private Environment env;


}
