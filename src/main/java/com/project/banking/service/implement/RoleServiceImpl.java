package com.project.banking.service.implement;

import com.project.banking.entity.Role;
import com.project.banking.repository.RoleRepository;
import com.project.banking.service.RoleService;
import com.project.banking.utils.constant.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepo;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role getOrSave(Roles role) {
        return roleRepo.findByRole(role.name()).orElseGet(
                () -> roleRepo.saveAndFlush(Role.builder().role(role).build())
        );
    }
}
