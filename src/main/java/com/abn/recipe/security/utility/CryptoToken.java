package com.abn.recipe.security.utility;

import com.abn.recipe.repository.user.UserEntity;
import com.abn.recipe.common.exception.ABNServiceException;
import com.abn.recipe.common.exception.ErrorCode;
import com.abn.recipe.security.model.Header;
import com.abn.recipe.security.model.PayLoad;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.util.Base64;

/**
 * CryptoToken for generate token and get username from token
 *
 * @author Pooya Mirzapour (pooyamirzapour@gmail.com)
 */
@Slf4j
public class CryptoToken {

    public static final int BEGIN_INDEX = 7;
    public static final String HS_256 = "HS256";
    public static final String JWT = "JWT";

    /**
     * @param user - User
     * @return a string token
     */
    public static String generateToken(UserEntity user) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Header header = new Header(HS_256, JWT);

        PayLoad
                payLoad = new PayLoad(user.getId(), user.getUsername(), user.getTokenExpireDate());
        String headerString;
        String payLoadString;
        try {
            headerString = Base64.getEncoder().encodeToString(mapper.writeValueAsString(header).getBytes());
            payLoadString = Base64.getEncoder().encodeToString(mapper.writeValueAsString(payLoad).getBytes());

        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage() + ",username: %s", user.getUsername());
            throw new ABNServiceException("Internal server error", ErrorCode.INTERNAL_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return headerString + "." + payLoadString;
    }

    /**
     * @param token - Token
     * @return a string token
     */
    public static String getUsername(String token) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String[] tokenParts = token.split("\\.");
        String payloadString = new String(Base64.getDecoder().decode(tokenParts[1].getBytes()));

        String username;
        try {
            username = mapper.readValue(payloadString, PayLoad.class).getUsername();
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage() + ",token: %s", token);
            throw new ABNServiceException("Internal server error", ErrorCode.INTERNAL_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return username;
    }

    public static String getTokenUsername(String authorization) {
        String token = authorization.substring(BEGIN_INDEX);
        return getUsername(token);
    }

}
