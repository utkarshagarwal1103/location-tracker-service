package com.which.locationtrackerservice.dao;

import com.which.locationtrackerservice.domain.Coordinates;
import com.which.locationtrackerservice.domain.GetDirectionList;
import com.which.locationtrackerservice.domain.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTrackerDao {

    public GetDirectionList retrieveDirection(String emailId);

    public Message processCoordinates(String emailId, Coordinates coordinates);

}
