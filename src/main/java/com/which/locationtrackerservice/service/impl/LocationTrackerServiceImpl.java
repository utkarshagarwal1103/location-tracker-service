package com.which.locationtrackerservice.service.impl;

import com.which.locationtrackerservice.constants.Constants;
import com.which.locationtrackerservice.dao.LocationTrackerDao;
import com.which.locationtrackerservice.domain.*;
import com.which.locationtrackerservice.exception.ServiceException;
import com.which.locationtrackerservice.service.LocationTrackerService;
import com.which.locationtrackerservice.utility.DirectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationTrackerServiceImpl implements LocationTrackerService {

    @Autowired
    private LocationTrackerDao locationTrackerDao;


    private static final Logger logger = LoggerFactory.getLogger(LocationTrackerServiceImpl.class);

    public Coordinates retriveCoordinates(String emailId){

        GetDirectionList getDirectionDetails= locationTrackerDao.retrieveDirection(emailId);

        if(getDirectionDetails !=null) {
            List<String> directionInstructions = getDirectionDetails.getDirections();
            DirectionUtil directionUtil = new DirectionUtil();
            return directionUtil.retrieveCoordinates(directionInstructions);
        }
        else{
            logger.error(LocationTrackerServiceImpl.class+ "LTS 102 - getDirectionDetails is Empty ");
            throw new ServiceException("LTS 102 - Application Error ");
        }

    }

    public ProcessCoordinatesResponse processCoordinates(ProcessCoordinatesRequest processCoordinatesRequest){

        String email= processCoordinatesRequest.getEmailId();
        Coordinates coordinates = processCoordinatesRequest.getCoordinates();

        Message message = locationTrackerDao.processCoordinates(email,coordinates);

        if(message !=null) {
            return prepareResponse(message.getMessage());
        }
        else{
            logger.error(LocationTrackerServiceImpl.class+ "LTS 103 - processCoordinates: message is Empty ");
            throw new ServiceException("LTS 103 - Application Error ");
        }
    }


    public ProcessCoordinatesResponse prepareResponse(String message){
        ProcessCoordinatesResponse processCoordinatesResponse = new ProcessCoordinatesResponse();
        if(message.contains(Constants.SUCCESS_MESSAGE_ONE)||
                message.contains(Constants.SUCCESS_MESSAGE_TWO)){
            processCoordinatesResponse.setStatus(Constants.SUCCESS);

        }else{
            processCoordinatesResponse.setStatus(Constants.FAILURE);
            logger.info(LocationTrackerServiceImpl.class+ "LTS 104 - prepareResponse: Failure Message received");
        }

        processCoordinatesResponse.setMessage(message);

        return processCoordinatesResponse;
    }

}
