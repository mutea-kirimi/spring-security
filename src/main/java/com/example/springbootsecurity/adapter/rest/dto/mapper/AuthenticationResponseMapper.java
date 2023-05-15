package com.example.springbootsecurity.adapter.rest.dto.mapper;

import com.example.springbootsecurity.adapter.rest.dto.AuthenticationResponseDto;
import com.example.springbootsecurity.user.User;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class AuthenticationResponseMapper implements BiFunction<User, String, AuthenticationResponseDto> {
    @Override
    public AuthenticationResponseDto apply(User user, String token) {
        return AuthenticationResponseDto.builder()
                .token(token)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getId())
                .role(user.getRole())
                .build();
    }
}
