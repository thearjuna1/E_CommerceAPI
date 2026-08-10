package com.e_commerceApi.E_CommerceAPI.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
}
