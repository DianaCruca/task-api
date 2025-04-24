package com.cruca.task_api.service;

import com.cruca.task_api.dto.LoginDtoRequest;
import com.cruca.task_api.dto.LoginDtoResponse;
import com.cruca.task_api.dto.UserDtoRequest;
import com.cruca.task_api.dto.UserDtoResponse;
import com.cruca.task_api.enums.Role;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.exception.DuplicateResourceException;
import com.cruca.task_api.exception.ResourceNotFoundException;
import com.cruca.task_api.jwt.JwtService;
import com.cruca.task_api.mapper.UserTransformerObject;
import com.cruca.task_api.model.User;
import com.cruca.task_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserTransformerObject userTransformerObject;


    /// Public functionality
    /// Role: none
    @Override
    public UserDtoResponse createUserPublic(UserDtoRequest userDtoRequest) {
        ///Validate that the user does not exist
        if (userRepository.existsByEmail(userDtoRequest.getEmail())) {
            throw new DuplicateResourceException("El Email ya se encuentra registrado");
        }

        ///Convert DtoRequest to Entity
        User user = userTransformerObject.dtoToEntity(userDtoRequest);
        ///Assign "A" Status by default
        user.setStatus(Status.A);
        ///Assign the "ADMIN" role by default if no users exist
        if (userRepository.findAll().isEmpty()) {
            user.setRole(Role.ADMIN);
        } else {
            /// Assign the "USER" role if there are already users in the system
            user.setRole(Role.USER);
        }
        ///Encrypt password
        user.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));

        ///Convert Entity to DtoRequest and save to the database
        return userTransformerObject.entityToDto(userRepository.save(user));
    }

    /// Public functionality
    /// Role: none
    @Override
    public LoginDtoResponse login(LoginDtoRequest dtoRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dtoRequest.getEmail(), dtoRequest.getPassword()));
        User user = userRepository.findByEmailAndStatus(dtoRequest.getEmail(), Status.A).orElseThrow();
        UserDetails userDetails = user;
        String token = jwtService.getToken(userDetails);

        return LoginDtoResponse.builder()
                .usuario(userTransformerObject.entityToDto(user))
                .token(token)
                .build();
    }

    /// Private functionality
    /// Role: admin
    @Override
    public UserDtoResponse createUserPrivate(UserDtoRequest userDtoRequest) {
        ///Validate that the user does not exist
        if (userRepository.existsByEmail(userDtoRequest.getEmail())) {
            throw new DuplicateResourceException("El Email ya se encuentra registrado");
        }

        ///Assign "A" Status by default
        userDtoRequest.setStatus(Status.A);

        ///Convert DtoRequest to Entity
        User user = userTransformerObject.dtoToEntity(userDtoRequest);
        ///Encrypt password
        user.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));

        ///Convert Entity to DtoRequest and save to the database
        return userTransformerObject.entityToDto(userRepository.save(user));
    }

    /// Private functionality
    /// Role: Any
    @Override
    public UserDtoResponse readUserById(Long id) {
        ///Validate that the user does exist
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con id: " + id));

        ///Convert Entity to DtoRequest
        return userTransformerObject.entityToDto(user);
    }

    /// Private functionality
    /// Role: Admin
    @Override
    public List<UserDtoResponse> readUsersByStatus(Status status) {
        List<User> users = userRepository.findByStatus(status);

        ///Convert Entity to DtoRequest
        return userTransformerObject.entityListToDtoList(users);
    }

    /// Private functionality
    /// Role: any
    @Override
    public UserDtoResponse updateUser(UserDtoRequest userDtoRequest) {
        ///Validate that the user does exist
        User user = userRepository.findById(userDtoRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado, con id: " + userDtoRequest.getId()));

        ///Here are the roles that have authorization to modify sensitive data
        List<String> authorizedRoles = List.of("ROLE_ADMIN");

        ///Verify that the user has authorization to update sensitive data
        if (authorizationService.identifyIsAuthorized(authorizedRoles)) {
            ///Allow updating of sensitive data
            user = updateUserWithAuthorization(user, userDtoRequest);
        }

        ///Allow updating of general data
        user.setName(userDtoRequest.getName());
        user.setLastname(userDtoRequest.getLastname());

        return userTransformerObject.entityToDto(userRepository.save(user));
    }

    private User updateUserWithAuthorization(User user, UserDtoRequest userDtoRequest) {
        user.setRole(userDtoRequest.getRole());
        user.setStatus(userDtoRequest.getStatus());
        return user;
    }

}
