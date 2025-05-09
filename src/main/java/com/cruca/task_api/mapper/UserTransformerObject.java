package com.cruca.task_api.mapper;

import com.cruca.task_api.dto.UserContactDtoResponse;
import com.cruca.task_api.dto.UserDtoRequest;
import com.cruca.task_api.dto.UserDtoResponse;
import com.cruca.task_api.enums.Status;
import com.cruca.task_api.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserTransformerObject {

    public User dtoToEntity(UserDtoRequest userDtoRequest){
        User user = new User();
        user.setName(userDtoRequest.getName());
        user.setLastname(userDtoRequest.getLastname());
        user.setEmail(userDtoRequest.getEmail());
        user.setStatus(Status.I);
        user.setRole(userDtoRequest.getRole());
        return user;
    }

    public UserDtoResponse entityToDto(User user){
        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(user.getId());
        userDtoResponse.setName(user.getName());
        userDtoResponse.setLastname(user.getLastname());
        userDtoResponse.setEmail(user.getEmail());
        userDtoResponse.setRole(user.getRole());
        userDtoResponse.setStatus(user.getStatus());
        return userDtoResponse;
    }

    public List<UserDtoResponse> entityListToDtoList(List<User> users){
        List<UserDtoResponse> userDtoResponseList = new ArrayList<>();
        for(User user : users){
            userDtoResponseList.add(entityToDto(user));
        }
        return userDtoResponseList;
    }

    public UserContactDtoResponse entityToContactDto(User user){
        UserContactDtoResponse response = new UserContactDtoResponse();
        response.setIdUser(user.getId());
        response.setNameUser(user.getName());
        response.setLastnameUser(user.getLastname());
        return response;
    }

    public List<UserContactDtoResponse> entityListToContactDtoList(List<User> users){
        List<UserContactDtoResponse> response = new ArrayList<>();
        for(User user : users){
            response.add(entityToContactDto(user));
        }
        return response;
    }

}
