package com.clinked.article_api.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private long expiresIn;

}
