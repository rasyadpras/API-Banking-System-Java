package com.project.banking.service;

import com.project.banking.entity.Role;
import com.project.banking.utils.constant.Roles;

public interface RoleService {
    Role getOrSave(Roles role);
}
