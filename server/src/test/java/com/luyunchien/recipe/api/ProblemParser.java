package com.luyunchien.recipe.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luyunchien.recipe.client.handler.ApiException;
import com.luyunchien.recipe.client.model.Problem;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProblemParser {

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Problem convertException(ApiException exception) {
        try {
            return OBJECT_MAPPER.readValue(exception.getResponseBody(), Problem.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
