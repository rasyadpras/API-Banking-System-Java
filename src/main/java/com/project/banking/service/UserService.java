package com.project.banking.service;

import com.project.banking.entity.User;

public interface UserService {
    User findId(String id);
    User getByContext();
}
