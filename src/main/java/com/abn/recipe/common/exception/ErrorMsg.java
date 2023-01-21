package com.abn.recipe.common.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Error message class.
 */

@Getter
@Setter
public class ErrorMsg implements Serializable {
    private int errorCode;
    private String errorMessage;

    public ErrorMsg(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
