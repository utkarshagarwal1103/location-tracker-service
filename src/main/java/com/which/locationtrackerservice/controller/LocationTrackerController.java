package com.which.locationtrackerservice.controller;


import com.which.locationtrackerservice.domain.*;
import com.which.locationtrackerservice.exception.ServiceException;
import com.which.locationtrackerservice.service.LocationTrackerService;
import com.which.locationtrackerservice.utility.ValidateEmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class LocationTrackerController {

    @Autowired
    private LocationTrackerService locationTrackerService;

    @Autowired
    private ValidateEmailUtil validateEmailUtil;

    private static final Logger logger = LoggerFactory.getLogger(LocationTrackerController.class);

    @PostMapping(value="/coordinates",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessDirectionResponse> processDirection
            (@Valid @RequestBody ProcessDirectionRequest processDirectionRequest){

        logger.info(LocationTrackerController.class+"/coordinates api call initiated ");
        Coordinates coordinates=null;
        if(validateEmailUtil.isEmailAddressValid(processDirectionRequest.getEmailId())){
           coordinates
                    = locationTrackerService.retriveCoordinates(processDirectionRequest.getEmailId());
        }else{
            logger.error(LocationTrackerController.class+ "LTS 105 - processDirection: Invalid email ");
            throw new ServiceException("LTS 105 - Application Error ");
        }

        ProcessDirectionResponse processDirectionResponse = new ProcessDirectionResponse();
        processDirectionResponse.setCoordinates(coordinates);

        logger.info(LocationTrackerController.class+"/coordinates api call completed ");
        return new ResponseEntity<>(processDirectionResponse,HttpStatus.OK);

    }


    @PostMapping(value="/validate",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProcessCoordinatesResponse> processCoordinates
            (@Valid @RequestBody ProcessCoordinatesRequest processCoordinatesRequest){

        logger.info(LocationTrackerController.class+"/validate api call initiated ");
        ProcessCoordinatesResponse response=null;
        if(validateEmailUtil.isEmailAddressValid(processCoordinatesRequest.getEmailId())){
            response
                    = locationTrackerService.processCoordinates(processCoordinatesRequest);
        }else{
            logger.error(LocationTrackerController.class+ "LTS 105 - processCoordinates: Invalid email ");
            throw new ServiceException("LTS 106 - Application Error ");
        }
        logger.info(LocationTrackerController.class+"/validate api call completed ");
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

}
