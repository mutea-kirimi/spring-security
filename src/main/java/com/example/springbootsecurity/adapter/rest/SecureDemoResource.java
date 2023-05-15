package com.example.springbootsecurity.adapter.rest;

import com.example.springbootsecurity.services.AuthService;
import com.example.springbootsecurity.services.LoggedInUserResolver;
import com.example.springbootsecurity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class SecureDemoResource {

    private final AuthService authService;
    private final LoggedInUserResolver loggedInUserResolver;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        System.out.println(loggedInUserResolver.getLoggedInUserName());
        System.out.println(loggedInUserResolver.getLoggedInUser());
        return ResponseEntity.ok("Hello from Secured Endpoint");
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(authService.listAllUsers());
    }
}
