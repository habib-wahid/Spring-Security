package com.example.spring_security.service;

import com.example.spring_security.config.JwtProvider;
import com.example.spring_security.dto.JwtAuthResponse;
import com.example.spring_security.dto.LoginDto;
import com.example.spring_security.dto.UserDto;
import com.example.spring_security.entity.Role;
import com.example.spring_security.entity.User;
import com.example.spring_security.enums.RoleName;
import com.example.spring_security.exception.UserFoundException;
import com.example.spring_security.repository.RoleRepository;
import com.example.spring_security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public void save(UserDto userDto) {
        if (userRepository.findByUsernameOrEmail(userDto.username(), userDto.email()) == null) {
            User user = User.builder()
                    .username(userDto.username())
                    .password(passwordEncoder.encode(userDto.password()))
                    .email(userDto.email())
                    .firstName(userDto.firstName())
                    .lastName(userDto.lastName())
                    .build();
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleName.ROLE_USER));
            user.setRoles(roles);
            userRepository.save(user);
            return;
        }
        throw new UserFoundException("User with this username or email already exists");
    }

    public JwtAuthResponse login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new JwtAuthResponse(
                    jwtProvider.generateToken(authentication),
                    "Bearer"
            );
        } catch (BadCredentialsException e) {
            log.error(e.getMessage());
            throw new BadCredentialsException("Invalid username or password");
        }

    }
}
