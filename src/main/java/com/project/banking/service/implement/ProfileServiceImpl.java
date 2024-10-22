package com.project.banking.service.implement;

import com.project.banking.dto.request.RegisterRequest;
import com.project.banking.dto.request.UpdateProfileRequest;
import com.project.banking.dto.response.profile.ProfileResponse;
import com.project.banking.entity.Profile;
import com.project.banking.mapper.ProfileMapper;
import com.project.banking.repository.ProfileRepository;
import com.project.banking.service.ProfileService;
import com.project.banking.service.UserService;
import com.project.banking.utils.component.ConverterUtil;
import com.project.banking.utils.component.ValidationUtil;
import com.project.banking.utils.constant.Gender;
import com.project.banking.utils.constant.IdentityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepo;
    private final ValidationUtil validation;
    private final ConverterUtil converter;
    private final ProfileMapper mapper;

    private final UserService userService;

    private Gender inputGender(String gender) {
        return switch (gender.toLowerCase()) {
            case "male" -> Gender.MALE;
            case "female" -> Gender.FEMALE;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value of gender must be 'male' or 'female'");
        };
    }

    private IdentityType inputIdentityType(String identityType) {
        return switch (identityType.toLowerCase()) {
            case "id card", "ktp" -> IdentityType.IDENTITY_CARD;
            case "passport" -> IdentityType.PASSPORT;
            case "driver license" -> IdentityType.DRIVER_LICENSE;
            case "student card" -> IdentityType.STUDENT_CARD;
            case "other" -> IdentityType.OTHER;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Valid value is 'id card','ktp','passport','driver license','student card','other' (choose one)");
        };
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProfileResponse> getAll(String city) {
        List<Profile> profiles;
        if (city != null) {
             profiles = profileRepo.findAllByCity(city);
        } else {
            profiles = profileRepo.findAll();
        }
        return profiles.stream().map(mapper::toProfileResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProfileResponse getById(String id) {
        Profile currentUser = userService.getByContext().getProfile();
        Profile profile = findId(id);

        if (!currentUser.getId().equals(profile.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this profile");
        }

        return mapper.toProfileResponse(profile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProfileResponse update(UpdateProfileRequest request, String id) {
        validation.validate(request);
        Profile profile = findId(id);

        profile.setFullName(request.getFullName());
        profile.setGender(inputGender(request.getGender()));
        profile.setBirthDate(converter.convertToLocalDate(request.getBirthDate()));
        profile.setIdentityType(inputIdentityType(request.getIdentityType()));
        profile.setIdentityNumber(request.getIdentityNumber());
        profile.setAddress(request.getAddress());
        profile.setCity(request.getCity());
        profile.setProvince(request.getProvince());
        profile.setCountry(request.getCountry());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setUpdatedAt(LocalDateTime.now());
        profileRepo.saveAndFlush(profile);

        return mapper.toProfileResponse(profile);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Profile create(RegisterRequest request) {
        validation.validate(request);
        Profile profile = Profile.builder()
                .fullName(request.getFullName())
                .gender(inputGender(request.getGender()))
                .birthDate(converter.convertToLocalDate(request.getBirthDate()))
                .identityType(inputIdentityType(request.getIdentityType()))
                .identityNumber(request.getIdentityNumber())
                .address(request.getAddress())
                .city(request.getCity())
                .province(request.getProvince())
                .country(request.getCountry())
                .phoneNumber(request.getPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();
        return profileRepo.saveAndFlush(profile);
    }

    @Transactional(readOnly = true)
    @Override
    public Profile findId(String id) {
        return profileRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found")
        );
    }
}
