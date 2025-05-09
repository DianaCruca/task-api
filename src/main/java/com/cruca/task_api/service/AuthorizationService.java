package com.cruca.task_api.service;

import java.util.List;

public interface AuthorizationService {

    boolean identifyIsAuthorized(List<String> authorizedRoles);

    String getEmail();

}
