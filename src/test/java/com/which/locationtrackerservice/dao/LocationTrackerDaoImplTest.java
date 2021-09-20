package com.which.locationtrackerservice.dao;

import com.which.locationtrackerservice.dao.impl.LocationTrackerDaoImpl;
import com.which.locationtrackerservice.domain.GetDirectionList;
import com.which.locationtrackerservice.exception.ServiceException;
import com.which.locationtrackerservice.service.impl.LocationTrackerServiceImpl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class LocationTrackerDaoImplTest {

    LocationTrackerDaoImpl locationTrackerDaoImpl;

    @Before
    public void setup() throws Exception{
        String baseUrl = String.format("http://which-technical-exercise.herokuapp.com");
        locationTrackerDaoImpl=new LocationTrackerDaoImpl(baseUrl);
    }


    @Test
    public void shouldReturnDirectionListWhenValidEmailisProvided() throws Exception{
        GetDirectionList getDirectionList
                = locationTrackerDaoImpl.retrieveDirection("abc@test.com");
        Assert.assertEquals("forward",getDirectionList.getDirections().get(0));

    }

    @Test(expected = ServiceException.class)
    public void shouldthrowExceptionWhenInValidEmailisProvided() throws Exception{
        GetDirectionList getDirectionList
                = locationTrackerDaoImpl.retrieveDirection("tytwyte");

    }

}
