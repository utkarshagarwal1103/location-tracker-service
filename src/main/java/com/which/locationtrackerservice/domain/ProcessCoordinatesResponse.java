package com.which.locationtrackerservice.domain;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ProcessCoordinatesResponse {

    String status;

    String message;

}
