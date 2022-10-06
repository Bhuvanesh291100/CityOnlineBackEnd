package com.city.online.api.service;

import com.city.online.api.model.pojo.Address;
import com.city.online.api.repository.UserRepository;
import com.city.online.api.dto.request.UserCreateRequestDto;
import com.city.online.api.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    /*
     * Create the user object int the database
     * @param userRequestDto
     * @return user
     * */
    public User createUser(UserCreateRequestDto userCreateRequestDto) {
        User user = new User();

        try {
            log.info("Creating the user with the user Name : {} ", userCreateRequestDto.getUsername());
            user.setFirstName(userCreateRequestDto.getFirstName());
            user.setLastName(userCreateRequestDto.getLastName());
            user.setEmailId(userCreateRequestDto.getEmailId());
            user.setPassword(userCreateRequestDto.getPassword());
            user.setDateOfBirth(userCreateRequestDto.getDateOfBirth());
            user.setRole(userCreateRequestDto.getRole());
            //If user is not present (Not Signed up) then throw the exception
            Optional<User> u = userRepository.findByUsername(userCreateRequestDto.getUsername());
            if (null == u.get()) {
                throw new UsernameNotFoundException("User not found with the Username : "+ userCreateRequestDto.getUsername());
            }
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("There is an exception while saving the user {}", e);
            throw e;
        }

    }

    public User addAddress(Address address) {
        User user = userRepository.findUserByUsername(address.getUsername());
        if(Objects.nonNull(user)) {
            user.getAddressList().add(address);
            return userRepository.save(user);
        }

        return null;
    }

    public User findUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return user;
    }

}