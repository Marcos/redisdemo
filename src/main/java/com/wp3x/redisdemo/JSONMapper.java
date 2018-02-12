package com.wp3x.redisdemo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j;

import java.io.IOException;

/**
 * Created by marcos.ferreira on 02.08.17.
 */
@Log4j
public class JSONMapper {

    private ObjectMapper mapper;

    public <T> String toJson(T t) {
        try {
            return getMapper().writeValueAsString( t );
        } catch (IOException e) {
            throw new RuntimeException( e );
        }
    }

    private ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            getMapper().setSerializationInclusion( JsonInclude.Include.ALWAYS );
        }
        return mapper;
    }

}
