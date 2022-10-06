package com.city.online.api.controller;

import com.city.online.api.dto.request.LoginRequest;
import com.city.online.api.dto.request.UserCreateRequestDto;
import com.city.online.api.dto.response.JwtResponse;
import com.city.online.api.model.User;
import com.city.online.api.model.pojo.Address;
import com.city.online.api.service.UserDetailsImpl;
import com.city.online.api.service.UserService;
import com.city.online.api.util.JwtUtils;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @ApiResponse
    @PostMapping(value = "", consumes = "application/json")
    @Parameter(in = ParameterIn.HEADER, name = "Authorization", required = true)
    public ResponseEntity<Object> createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        User user = userService.createUser(userCreateRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(user, headers , HttpStatus.CREATED);
    }

    @GetMapping(value = "/username")
    public ResponseEntity<User> getUserByUsername(@RequestParam(name = "username") String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getRole()));
    }

    /* This API is used to add/update the address for a user*/
    @PutMapping(value = "/address", consumes = "application/json")
    public ResponseEntity<Object> addAddress(@RequestBody Address address) {
        User user = userService.addAddress(address);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
