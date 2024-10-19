package com.project.banking.service.implement;

import com.project.banking.dto.request.*;
import com.project.banking.dto.response.auth.LoginResponse;
import com.project.banking.dto.response.auth.RegisterResponse;
import com.project.banking.dto.response.useracc.UserResponse;
import com.project.banking.entity.Role;
import com.project.banking.entity.User;
import com.project.banking.mapper.LoginMapper;
import com.project.banking.mapper.RegisterMapper;
import com.project.banking.mapper.UserMapper;
import com.project.banking.repository.UserRepository;
import com.project.banking.service.*;
import com.project.banking.utils.component.ValidationUtil;
import com.project.banking.utils.constant.AccountUserStatus;
import com.project.banking.utils.constant.Roles;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepo;
    private final ValidationUtil validation;
    private final RegisterMapper registerMapper;
    private final LoginMapper loginMapper;
    private final UserMapper userMapper;

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${banking.super-admin.email}")
    private String superAdminEmail;
    @Value("${banking.super-admin.password}")
    private String superAdminPassword;

    @PostConstruct
    public void initSuperAdmin() {
        Optional<User> currentSuperAdmin = userRepo.findByEmail(superAdminEmail);
        if (currentSuperAdmin.isPresent()) return;

        Role admin = roleService.getOrSave(Roles.ROLE_ADMINISTRATOR);
        Role staff = roleService.getOrSave(Roles.ROLE_OFFICER);
        Role customer = roleService.getOrSave(Roles.ROLE_CUSTOMER);

        User user = User.builder()
                .email(superAdminEmail)
                .password(passwordEncoder.encode(superAdminPassword))
                .roles(List.of(admin, staff, customer))
                .status(AccountUserStatus.ACTIVE)
                .attempt(0)
                .isUnlocked(true)
                .isVerified(true)
                .createdAt(LocalDateTime.now())
                .build();
        userRepo.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(RegisterRequest request) {
        validation.validate(request);
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(roleService.getOrSave(Roles.ROLE_CUSTOMER)))
                .status(AccountUserStatus.NOT_VERIFIED)
                .attempt(0)
                .isUnlocked(true)
                .isVerified(false)
                .createdAt(LocalDateTime.now())
                .profile(profileService.create(request))
                .build();
        userRepo.saveAndFlush(user);
        return registerMapper.toRegisterResponse(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginResponse login(LoginRequest request) throws DataIntegrityViolationException {
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not registered")
        );

        if (!user.getEmail().equals(superAdminEmail) && user.getAttempt() >= 5) {
            user.setUnlocked(false);
            userRepo.save(user);
            throw new ResponseStatusException(HttpStatus.LOCKED, "Account is locked due to too many failed login attempts");
        }

        try {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            );
            Authentication authenticated = authenticationManager.authenticate(authentication);
            User authenticatedUser = (User) authenticated.getPrincipal();
            String token = jwtService.generateToken(authenticatedUser);

            user.setAttempt(0);
            userRepo.saveAndFlush(user);
            return loginMapper.toLoginResponse(authenticatedUser, token);
        } catch (BadCredentialsException e) {
            if (!user.getEmail().equals(superAdminEmail)) {
                user.setAttempt(user.getAttempt() + 1);
                userRepo.save(user);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse addRole(UpdateUserAddRoleRequest request, String id) {
        validation.validate(request);
        User user = userRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        List<Role> roles = user.getRoles();
        switch (request.getRole().toLowerCase()) {
            case "admin" -> roles.add(roleService.getOrSave(Roles.ROLE_ADMINISTRATOR));
            case "staff" -> roles.add(roleService.getOrSave(Roles.ROLE_OFFICER));
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value of role must be 'admin' or 'staff'");
        }
        user.setRoles(roles);

        user.setUpdatedAt(LocalDateTime.now());
        userRepo.saveAndFlush(user);
        return userMapper.toUserResponse(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void forgotPassword(ForgotUserPasswordRequest request, String id) {
        validation.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepo.findByEmail(authentication.getPrincipal().toString()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found")
        );

        User user = userRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        if (!currentUser.getEmail().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access deny");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resetPassword(ResetUserPasswordRequest request, String id) {
        validation.validate(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepo.findByEmail(authentication.getPrincipal().toString()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found")
        );

        User user = userRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        if (!currentUser.getEmail().equals(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access deny");
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Old password not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepo.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void verify(String id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        if (user.getStatus().equals(AccountUserStatus.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your account is already active");
        }
        if (user.getStatus().equals(AccountUserStatus.LOCKED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your account has been locked");
        }
        user.setStatus(AccountUserStatus.ACTIVE);
        user.setUpdatedAt(LocalDateTime.now());
        user.setVerified(true);
        userRepo.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unlock(String id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );

        if (user.isUnlocked()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your account is already active");
        }
        user.setStatus(AccountUserStatus.ACTIVE);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUnlocked(true);
        userRepo.save(user);
    }
}
