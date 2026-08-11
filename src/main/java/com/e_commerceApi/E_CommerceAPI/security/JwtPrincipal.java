package com.e_commerceApi.E_CommerceAPI.security;

import com.e_commerceApi.E_CommerceAPI.enums.Role;

public record JwtPrincipal(
        Long userId,
        Role role)
{
}
