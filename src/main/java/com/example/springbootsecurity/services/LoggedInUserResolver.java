package com.example.springbootsecurity.services;

import com.example.springbootsecurity.user.User;
import com.example.springbootsecurity.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggedInUserResolver {

    private final UserRepository userRepository;

    public String getLoggedInUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        throw new IllegalArgumentException("User Not Known");
    }

    public User getLoggedInUser() {
        return userRepository.findByEmail(getLoggedInUserName())
                .orElseThrow(() -> new IllegalArgumentException("User Not Known"));
    }

}
