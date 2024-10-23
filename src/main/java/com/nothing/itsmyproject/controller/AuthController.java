package com.nothing.itsmyproject.controller;

import com.nothing.itsmyproject.entity.User;
import com.nothing.itsmyproject.security.jwt.models.AuthenticationRequest;
import com.nothing.itsmyproject.security.jwt.models.AuthenticationResponse;
import com.nothing.itsmyproject.service.CustomUserDetailService;
import com.nothing.itsmyproject.security.jwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Autowired
  private JwtUtils jwtUtil;

  @PostMapping("/signin")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
      );
    } catch (AuthenticationException e) {
      return ResponseEntity.status(401).body("Incorrect username or password");
    }

    final UserDetails userDetails = customUserDetailService.loadUserByUsername(authenticationRequest.getUsername());
    final String jwt = jwtUtil.generateToken(userDetails.getUsername());

    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    customUserDetailService.saveUser(user);
    return ResponseEntity.ok("User registered successfully");
  }
}