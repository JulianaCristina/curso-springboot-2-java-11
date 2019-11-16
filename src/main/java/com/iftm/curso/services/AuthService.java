package com.iftm.curso.services;

import com.iftm.curso.JWTUtil;
import com.iftm.curso.dto.CredentialsDTO;
import com.iftm.curso.dto.TokenDTO;
import com.iftm.curso.services.exceptions.JWTAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Transactional(readOnly = true)
    public TokenDTO authenticate(CredentialsDTO dto){
        try {
            var autToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            authenticationManager.authenticate(autToken);
            String token = jwtUtil.generateToken(dto.getEmail());
            return new TokenDTO(dto.getEmail(), token);
        }catch (AuthenticationException e){
            throw new JWTAuthenticationException("Bad credentials");
        }
    }
}