package com.uca.parcialfinalncapas.service;

import com.uca.parcialfinalncapas.dto.request.LoginRequest;
import com.uca.parcialfinalncapas.dto.request.UserCreateRequest;
import com.uca.parcialfinalncapas.entities.User;
import com.uca.parcialfinalncapas.exceptions.UserNotFoundException;
import com.uca.parcialfinalncapas.repository.UserRepository;
import com.uca.parcialfinalncapas.security.JwtService;
import com.uca.parcialfinalncapas.utils.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AuthService(
            JwtService jwtService, AuthenticationManager authManager, UserRepository repository) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder(12);

    }

    public User register(UserCreateRequest request) {
        User user = UserMapper.toEntityCreate(request);
        user.setPassword(encoder.encode(user.getPassword()));

        user.setNombreRol("USER");

        repository.save(user);
        return user;
    }

    public String verify(LoginRequest login) {
        String token;
        Authentication authentication;
        User usuario = repository.findByCorreo(
                login.getEmail()).orElseThrow(()-> new UserNotFoundException("Usuario no encontrado")
        );
        try {
            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.getEmail(), login.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
        if (authentication.isAuthenticated()) {
            token = jwtService.generateToken(login.getEmail());
            System.out.println(token);
            return token;
        } else {
            return "error";
        }
    }
}
