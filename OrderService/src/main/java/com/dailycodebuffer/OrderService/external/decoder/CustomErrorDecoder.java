package com.dailycodebuffer.OrderService.external.decoder;

import com.dailycodebuffer.OrderService.Exception.CustomException;
import com.dailycodebuffer.OrderService.external.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper = new ObjectMapper();

        log.info("::{}", response.request().url());
        log.info("::{}", response.request().headers());

        try {
            ErrorResponse errorResponse
                    = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);
            return new CustomException(errorResponse.getErrorCode(), response.status(), errorResponse.getErrorMessage());
        }
        catch (IOException io){
            throw new CustomException("Internal_server_error", 500, "Internal_server_error");
        }
    }
}
