package com.which.locationtrackerservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
public class ProcessDirectionRequest implements Serializable {

    @JsonProperty(value="emailId" ,required=true)
    @NotNull @NotEmpty
    @Email
    String emailId;

}
