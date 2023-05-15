package com.example.springbootsecurity.services;

import com.example.springbootsecurity.adapter.rest.dto.AuthenticationResponseDto;
import com.example.springbootsecurity.adapter.rest.dto.LoginRequestDto;
import com.example.springbootsecurity.adapter.rest.dto.RegisterRequestDto;
import com.example.springbootsecurity.adapter.rest.dto.mapper.AuthenticationResponseMapper;
import com.example.springbootsecurity.user.Role;
import com.example.springbootsecurity.user.User;
import com.example.springbootsecurity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationResponseMapper authenticationResponseMapper;

    public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {
        var user = User.builder()
                .firstName(registerRequestDto.getFirstName())
                .id(UUID.randomUUID())
                .email(registerRequestDto.getEmail())
                .lastName(registerRequestDto.getLastName())
                .role(Role.USER)
                .secret(passwordEncoder.encode(registerRequestDto.getSecret()))
                .build();
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return authenticationResponseMapper.apply(user, token);
    }

    public AuthenticationResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getSecret()
                )
        );
        var user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var token = jwtService.generateToken(user);
        return authenticationResponseMapper.apply(user, token);
    }

    @Secured({Role.ADMIN})
    public List<User> listAllUsers(){
        return userRepository.findAll();
    }
}
