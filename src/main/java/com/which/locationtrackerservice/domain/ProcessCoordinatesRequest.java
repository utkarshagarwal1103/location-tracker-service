package com.which.locationtrackerservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProcessCoordinatesRequest {

    @JsonProperty(value="emailId" ,required=true)
    @NotNull @NotEmpty
    @Email
    String emailId;

    @NotNull
    Coordinates coordinates;

}
