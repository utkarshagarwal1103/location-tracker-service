package com.which.locationtrackerservice.controller;

import com.which.locationtrackerservice.domain.*;
import com.which.locationtrackerservice.exception.ServiceException;
import com.which.locationtrackerservice.service.LocationTrackerService;
import com.which.locationtrackerservice.utility.ValidateEmailUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Assert;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class LocationTrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    LocationTrackerService locationTrackerService;

    @Mock
    ValidateEmailUtil validateEmailUtil;

    @InjectMocks
    LocationTrackerController locationTrackerController;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(locationTrackerController).build();
    }

    @Test
    public void shouldReturnSuccessResponseWhenValidEmailisSent() throws Exception{

        Mockito.when(locationTrackerService.retriveCoordinates(Mockito.any(String.class))).thenReturn(
                new Coordinates(3,5)
        );

        ProcessDirectionRequest processDirectionRequest = createProcessDirectionRequest();

        Mockito.when(validateEmailUtil.isEmailAddressValid(processDirectionRequest.getEmailId())).thenReturn(true);

        ResponseEntity<ProcessDirectionResponse> responseEntity = locationTrackerController.processDirection(processDirectionRequest);
        Assert.assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        Assert.assertEquals(5,responseEntity.getBody().getCoordinates().getY());

    }

    @Test(expected= ServiceException.class)
    public void shouldthrowexceptioneWhenInValidEmailisSent() throws Exception{

        Mockito.when(locationTrackerService.retriveCoordinates(Mockito.any(String.class))).thenReturn(
                new Coordinates(3,5)
        );
        ProcessDirectionRequest processDirectionRequest = createProcessDirectionRequest();

        Mockito.when(validateEmailUtil.isEmailAddressValid(processDirectionRequest.getEmailId())).thenReturn(false);

        ResponseEntity<ProcessDirectionResponse> responseEntity = locationTrackerController.processDirection(processDirectionRequest);

    }


    @Test
    public void shouldReturnSuccessStatusWhenValidEmailandCoordinatesisSent() throws Exception{

        Mockito.when(locationTrackerService.processCoordinates(Mockito.any(ProcessCoordinatesRequest.class))).thenReturn(
                new ProcessCoordinatesResponse("SUCCESS","Message 124")
        );

        ProcessCoordinatesRequest processCoordinatesRequest = createProcessCoordinatesRequest();

        Mockito.when(validateEmailUtil.isEmailAddressValid(processCoordinatesRequest.getEmailId())).thenReturn(true);

        ResponseEntity<ProcessCoordinatesResponse> responseEntity = locationTrackerController.processCoordinates(processCoordinatesRequest);
        Assert.assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        Assert.assertEquals("SUCCESS",responseEntity.getBody().getStatus());

    }


    private ProcessDirectionRequest createProcessDirectionRequest(){
        ProcessDirectionRequest processDirectionRequest = new ProcessDirectionRequest();
        processDirectionRequest.setEmailId("test@gmail.com");
        return processDirectionRequest;
    }

    private ProcessCoordinatesRequest createProcessCoordinatesRequest(){
        ProcessCoordinatesRequest processCoordinatesRequest = new ProcessCoordinatesRequest();
        Coordinates coordinates = new Coordinates();
        coordinates.setX(5);
        coordinates.setY(2);
        processCoordinatesRequest.setCoordinates(coordinates);
        processCoordinatesRequest.setEmailId("abc@test.com");
        return  processCoordinatesRequest;

    }
}
