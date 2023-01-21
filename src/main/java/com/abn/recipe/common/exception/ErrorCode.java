package com.abn.recipe.common.exception;

/**
 * Enum for all possible error codes.
 */
public enum ErrorCode {

    ID_NOT_SENT("Id not sent", 1001),
    RECIPE_NOT_FOUND("Recipe not found", 1002),
    USER_REGISTER_BEFORE("Already registered", 1003),
    UNKNOWN_ERROR("Unknown error", 200),
    INTERNAL_ERROR("Internal error", 201),
    INGREDIENT_NOT_FOUND("Ingredients not found", 1004),
    DATABASE_EXCEPTION("Data is not valid", 1005);

    private int value;
    private String message;

    ErrorCode(String message, int value) {
        this.value = value;
        this.message = message;

    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }
}
