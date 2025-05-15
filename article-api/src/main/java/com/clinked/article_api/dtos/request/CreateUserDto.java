package com.clinked.article_api.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    @JsonProperty("isAdmin")
    private boolean isAdmin;

}
