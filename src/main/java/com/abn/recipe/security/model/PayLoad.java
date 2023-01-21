package com.abn.recipe.security.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * PayLoad for creating a part of token
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */

@Getter
@Setter
@NoArgsConstructor
public class PayLoad implements Serializable {
    private long clientId;
    private String username;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime expireDate;

    public PayLoad(Long clientId, String username, LocalDateTime localDateTime) {
        this.clientId = clientId;
        this.username = username;
        this.expireDate = localDateTime;
    }

}