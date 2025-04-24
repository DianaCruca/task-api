package com.cruca.task_api.service;

import com.cruca.task_api.dto.LoginDtoRequest;
import com.cruca.task_api.dto.LoginDtoResponse;
import com.cruca.task_api.dto.UserDtoRequest;
import com.cruca.task_api.dto.UserDtoResponse;
import com.cruca.task_api.enums.Status;

import java.util.List;

public interface UserService {

    UserDtoResponse createUserPublic(UserDtoRequest userDtoRequest);

    UserDtoResponse createUserPrivate(UserDtoRequest userDtoRequest);

    LoginDtoResponse login(LoginDtoRequest dtoRequest);

    UserDtoResponse readUserById(Long id);

    List<UserDtoResponse> readUsersByStatus(Status status);

    UserDtoResponse updateUser(UserDtoRequest userDtoRequest);

}
