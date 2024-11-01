package com.nothing.itsmyproject.service;

import com.nothing.itsmyproject.controller.AuthController;
import com.nothing.itsmyproject.entity.IRole;
import com.nothing.itsmyproject.entity.Role;
import com.nothing.itsmyproject.entity.User;
import com.nothing.itsmyproject.repository.RoleRepository;
import com.nothing.itsmyproject.repository.UserRepository;
import com.nothing.itsmyproject.service.impl.UserDetailsImpl;
import com.nothing.itsmyproject.service.kafka.KafkaProducerService;
import com.nothing.itsmyproject.service.kafka.UserLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  KafkaProducerService kafkaProducerService;

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  // Setter
  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setRoleRepository(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Autowired
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
//  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("User not found with username: " + username));
    logger.info("User found: {}", user.getUsername());
    return UserDetailsImpl.build(user);
  }

  // ? Save user to the database
  public void saveUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    // Assign default role to the user
    Set<Role> roles = new HashSet<>();
    Role userRole = roleRepository.findByName(IRole.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(userRole);
    user.setRoles(roles);

    userRepository.save(user);
    kafkaProducerService.sendUserLog("user-log-topic",
        new UserLog("CREATE", user.getId(), user.getUsername(), user.getEmail(),
            user.getRoles().toString()));
  }

  public void updateUser(User user) {
    userRepository.save(user);
    kafkaProducerService.sendUserLog("user-log-topic",
        new UserLog("UPDATE", user.getId(), user.getUsername(),
            user.getEmail(), user.getRoles().toString()));
  }

  public void deleteUser(Long userId) {
    userRepository.deleteById(userId);
    kafkaProducerService.sendUserLog("user-log-topic",
        new UserLog("DELETE", userId, "", "", ""));
  }
}