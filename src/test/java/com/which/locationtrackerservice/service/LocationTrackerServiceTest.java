package com.which.locationtrackerservice.service;

import com.which.locationtrackerservice.dao.LocationTrackerDao;
import com.which.locationtrackerservice.domain.Coordinates;
import com.which.locationtrackerservice.domain.GetDirectionList;
import com.which.locationtrackerservice.exception.ServiceException;
import com.which.locationtrackerservice.service.impl.LocationTrackerServiceImpl;
import com.which.locationtrackerservice.utility.DirectionUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class LocationTrackerServiceTest {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    DirectionUtil directionUtil;

    @Mock
    LocationTrackerDao locationTrackerDao;
    @InjectMocks
    LocationTrackerServiceImpl locationTrackerServiceImpl;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(locationTrackerServiceImpl).build();
    }


    @Test
    public void shouldReturnCoordinatesWhenValidEmailIsPassed(){

        GetDirectionList getDirectionList = prepareDirectionList();

        Mockito.when(locationTrackerDao.retrieveDirection(Mockito.any(String.class))).thenReturn(getDirectionList);

        Coordinates coordinates = locationTrackerServiceImpl.retriveCoordinates("abc@test.com");

        Assert.assertEquals(3,coordinates.getX());
        Assert.assertEquals(2,coordinates.getY());
    }

    @Test(expected = ServiceException.class)
    public void shouldThrowExceptionwhenDAOReturnnull(){
        Mockito.when(locationTrackerDao.retrieveDirection(Mockito.any(String.class))).thenReturn(null);

        Coordinates coordinates = locationTrackerServiceImpl.retriveCoordinates("abc@test.com");
    }



    private GetDirectionList prepareDirectionList(){

        List<String> str= new ArrayList<>();
        str.add("forward");
        str.add("right");
        str.add("forward");
        str.add("forward");
        str.add("forward");
        str.add("left");
        str.add("forward");
        GetDirectionList getDirectionList = new GetDirectionList();
        getDirectionList.setDirections(str);
        return getDirectionList;
    }
}
