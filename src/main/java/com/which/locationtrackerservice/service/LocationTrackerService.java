package com.which.locationtrackerservice.service;

import com.which.locationtrackerservice.domain.Coordinates;
import com.which.locationtrackerservice.domain.ProcessCoordinatesRequest;
import com.which.locationtrackerservice.domain.ProcessCoordinatesResponse;

public interface LocationTrackerService {

    public Coordinates retriveCoordinates(String emailId);

    public ProcessCoordinatesResponse processCoordinates(ProcessCoordinatesRequest processCoordinatesRequest);
}
