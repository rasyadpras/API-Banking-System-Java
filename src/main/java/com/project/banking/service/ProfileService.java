package com.project.banking.service;

import com.project.banking.dto.request.RegisterRequest;
import com.project.banking.dto.request.UpdateProfileRequest;
import com.project.banking.dto.response.profile.ProfileResponse;
import com.project.banking.entity.Profile;

import java.util.List;

public interface ProfileService {
    List<ProfileResponse> getAll(String city);
    ProfileResponse getById(String id);
    ProfileResponse update(UpdateProfileRequest request, String id);

    Profile create(RegisterRequest request);
    Profile findId(String id);
}
