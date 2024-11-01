package com.nothing.itsmyproject.controller;

import com.google.protobuf.util.JsonFormat;
import com.nothing.itsmyproject.entity.User;
import com.nothing.itsmyproject.grpc.GrpcClient;
import com.nothing.itsmyproject.security.jwt.models.JwtResponse;
import com.nothing.itsmyproject.service.CustomUserDetailService;
import com.nothing.itsmyproject.security.jwt.utils.JwtUtils;
import com.nothing.itsmyproject.service.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.nothing.itsmyproject.security.jwt.models.AuthenticationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import search.Search;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Autowired
  private JwtUtils jwtUtil;

  @Autowired
  private PasswordEncoder passwordEncoder;

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(
      @Valid @RequestBody AuthenticationRequest loginRequest) {
    try {
      logger.info("Authenticating user: {}", loginRequest.getUsername());

      // Load user details using CustomUserDetailService
      UserDetails userDetails = customUserDetailService.loadUserByUsername(
          loginRequest.getUsername());

      // Authenticate the user
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
              loginRequest.getPassword())
      );

      // Generate JWT tokens
      Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
          userDetails.getAuthorities());
      String jwt = jwtUtil.generateJwtToken(authentication);
      String refreshToken = jwtUtil.generateRefreshToken(authentication);

      // Collect roles
      List<String> roles = userDetails.getAuthorities().stream()
          .map(item -> item.getAuthority())
          .collect(Collectors.toList());

      return ResponseEntity.ok(new JwtResponse(jwt, refreshToken,
          ((UserDetailsImpl) userDetails).getId(),
          userDetails.getUsername(),
          ((UserDetailsImpl) userDetails).getEmail(),
          roles));
    } catch (AuthenticationException e) {
      logger.error("Authentication failed: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody User user) {
    customUserDetailService.saveUser(user);
    return ResponseEntity.ok("User registered successfully");
  }

  @PutMapping("/update")
  public ResponseEntity<?> updateUser(@RequestBody User user) {
    customUserDetailService.updateUser(user);
    return ResponseEntity.ok("User updated successfully");
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    customUserDetailService.deleteUser(id);
    return ResponseEntity.ok("User deleted successfully");
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(@Valid @RequestBody String refreshToken) {
    if (jwtUtil.validateJwtToken(refreshToken)) {
      String username = jwtUtil.getUserNameFromJwtToken(refreshToken);
      UserDetailsImpl userDetails = (UserDetailsImpl) customUserDetailService.loadUserByUsername(
          username);
      String newToken = jwtUtil.generateJwtToken(
          (Authentication) new UsernamePasswordAuthenticationToken(userDetails, null,
              userDetails.getAuthorities()));
      return ResponseEntity.ok(new JwtResponse(newToken, refreshToken,
          userDetails.getId(),
          userDetails.getUsername(),
          userDetails.getEmail(),
          userDetails.getAuthorities().stream().map(item -> item.getAuthority())
              .collect(Collectors.toList())));
    } else {
      return ResponseEntity.badRequest().body("Invalid refresh token");
    }
  }

  @Autowired
  private GrpcClient grpcClient;

  @GetMapping("/searchUsers")
  public ResponseEntity<?> searchUsers(@RequestParam String query) {
    try {
      Search.SearchUserResponse response = grpcClient.grpcSearchUser(query);
      String jsonResponse = JsonFormat.printer().print(response);
      return ResponseEntity.ok(jsonResponse);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
  }
}