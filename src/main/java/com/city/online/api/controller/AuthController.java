package com.city.online.api.controller;

import com.city.online.api.constant.AppConstants;
import com.city.online.api.dto.request.LoginRequest;
import com.city.online.api.dto.request.SignUpRequest;
import com.city.online.api.dto.response.JwtResponse;
import com.city.online.api.dto.response.MessageResponse;
import com.city.online.api.model.User;
import com.city.online.api.repository.UserRepository;
import com.city.online.api.service.UserDetailsImpl;
import com.city.online.api.util.JwtUtils;
import com.city.online.api.validation.validator.common.CityOnlineValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CityOnlineValidator cityOnlineValidator;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@NotNull @Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        /*List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());*/

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getRole()));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, Errors errors) {
        //AuthValidator
        cityOnlineValidator.validate(signUpRequest, AppConstants.AUTH_VALIDATOR, errors);

        // Create new user's account
        User user = User.builder()
                    .username(signUpRequest.getUsername())
                    .emailId(signUpRequest.getEmail())
                    .password(encoder.encode(signUpRequest.getPassword())).build();

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    // TODO - write the reset pasword API
    // When user want to reset then new password will be set in the back end and same will be shared via email

}
