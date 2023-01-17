package com.kodilla.prices.external.prices;

import com.ctc.wstx.shaded.msv_core.util.Uri;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class UserClient {
    private final Logger logger = LoggerFactory.getLogger(UserClient.class);
    private final ObjectMapper objectMapper;

    @Value("${offers.client.url}")
    private String userUrl;

    public UserClient(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public void addUser(UserDto userDto){
        send(
                baseUrl(),
                "POST",
                jsonBodyPublisher(userDto),
                HttpResponse.BodyHandlers.discarding(),
                userDto
        );

    }

    private HttpRequest.BodyPublisher jsonBodyPublisher(Object dto){
        try{
            return HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto));
        }catch (JsonProcessingException e){
            throw new RestClientException("Error performing json conversion of " + dto, e);
        }
    }

    private Void send(UriComponentsBuilder uriComponentsBuilder, String method, UserDto userDto){
        return send(uriComponentsBuilder, method, HttpRequest.BodyPublishers.noBody(),HttpResponse.BodyHandlers.discarding(), userDto);
    }
    private <T> T send(UriComponentsBuilder uriComponentsBuilder, String method, HttpRequest.BodyPublisher bodyPublisher, HttpResponse.BodyHandler<T> bodyHandler, UserDto userDto){
        URI uri = uriComponentsBuilder.build().toUri();
        try{
            T responseBody =  HttpClient.newHttpClient()
                    .send(HttpRequest.newBuilder(uri)
                            .method(method, HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(userDto)))
                            .build(),
                            bodyHandler)
                    .body();
            logger.info("Sending request to {}, ended with {}", uri,responseBody);
            return responseBody;
        }catch (Exception e){
            throw new RestClientException("Error performing request", e);
        }
    }

    private UriComponentsBuilder baseUrl(){
        return UriComponentsBuilder.fromHttpUrl(userUrl).path("/api/v1/users");
    }
}
