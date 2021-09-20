package com.which.locationtrackerservice.dao;

import com.which.locationtrackerservice.dao.impl.LocationTrackerDaoImpl;
import com.which.locationtrackerservice.domain.GetDirectionList;
import com.which.locationtrackerservice.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

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
