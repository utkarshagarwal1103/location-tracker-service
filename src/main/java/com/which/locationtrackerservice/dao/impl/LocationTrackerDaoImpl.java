package com.which.locationtrackerservice.dao.impl;

import com.which.locationtrackerservice.constants.Constants;
import com.which.locationtrackerservice.dao.LocationTrackerDao;
import com.which.locationtrackerservice.domain.Coordinates;
import com.which.locationtrackerservice.domain.GetDirectionList;
import com.which.locationtrackerservice.domain.Message;
import com.which.locationtrackerservice.exception.ServiceException;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.util.concurrent.TimeUnit;



@Repository
@Configuration
public class LocationTrackerDaoImpl implements LocationTrackerDao {

    private final WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger(LocationTrackerDaoImpl.class);

    public static final int TIMEOUT = 5000;

    public LocationTrackerDaoImpl(@Value("${com.which.url}") String url ){

        if(url.isEmpty()|| url==null){
            logger.error("No URL found in properties files");
            throw new ServiceException("LTS101 - Application Error");
        }

        HttpClient client = HttpClient.create().doOnConnected(conn -> conn
                .addHandler(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                .addHandler(new WriteTimeoutHandler(TIMEOUT)));

        this.webClient=WebClient.builder()
                .baseUrl(url)
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }

    public GetDirectionList retrieveDirection(String emailId) throws ServiceException {

       try {
           WebClient.ResponseSpec resp = this.webClient
                   .get()
                   .uri(Constants.SLASH
                           + Constants.API
                           + Constants.SLASH
                           + emailId
                           + Constants.SLASH
                           + Constants.DIRECTIONS)
                   .retrieve();

           return resp.onStatus(HttpStatus::isError, response -> {
               response.bodyToMono(String.class)
                       .subscribe(body -> logger.error("Response body: {}", body));
               logger.error(response.statusCode().toString());
               throw new ServiceException("LTS109 - Application Error");
           }).bodyToMono(GetDirectionList.class).block();

       }catch (ReadTimeoutException e){
           logger.error(e.getMessage());
           throw new ServiceException("LTS111 - Application Error");
       }
    }

    public Message processCoordinates(String emailId, Coordinates coordinates) throws ServiceException{
        try{
            WebClient.ResponseSpec resp = this.webClient
                    .get()
                    .uri(Constants.SLASH
                            +Constants.API
                            +Constants.SLASH
                            +emailId
                            +Constants.SLASH
                            +Constants.LOCATION
                            +Constants.SLASH
                            +coordinates.getX()
                            +Constants.SLASH
                            +coordinates.getY())
                    .retrieve();
            return  resp.onStatus(HttpStatus::isError, response -> {
                response.bodyToMono(String.class)
                        .subscribe(body -> logger.error("Response body: {}", body));
                logger.error(response.statusCode().toString());
                throw new ServiceException("LTS108 - Application Error");
            }).bodyToMono(Message.class).block();
        }catch (ReadTimeoutException e){
            logger.error(e.getMessage());
            throw new ServiceException("LTS110 - Application Error");
        }


    }


    /*private WebClient prepareWebClient() throws ReadTimeoutException {
        HttpClient client = HttpClient.create().doOnConnected(conn -> conn
                .addHandler(new ReadTimeoutHandler(TIMEOUT, TimeUnit.MILLISECONDS))
                .addHandler(new WriteTimeoutHandler(TIMEOUT)));

        WebClient.builder()
                .baseUrl(BASE_URL)
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }*/

}
