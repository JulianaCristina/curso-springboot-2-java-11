package com.iftm.curso.services;

import com.iftm.curso.entities.User;
import com.iftm.curso.repositories.UserRepository;
import com.iftm.curso.security.JWTUtil;
import com.iftm.curso.dto.CredentialsDTO;
import com.iftm.curso.dto.TokenDTO;
import com.iftm.curso.services.exceptions.JWTAuthenticationException;
import com.iftm.curso.services.exceptions.JWTAuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

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

    public User authenticated() {
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userRepository.findByEmail(userDetails.getUsername());
        }catch (Exception e){
            throw new JWTAuthorizationException("Access denied");
        }
    }

    public void validateSelfOrAdmin(Long userId){
        User user = authenticated();
        if (user == null || (!user.getId().equals(userId)) && !user.hasRoles("ROLE_ADMIN")){
            throw  new JWTAuthorizationException("Access denied");
        }
    }
}
