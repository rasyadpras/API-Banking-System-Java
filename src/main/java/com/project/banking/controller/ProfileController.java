package com.project.banking.controller;

import com.project.banking.dto.request.UpdateProfileRequest;
import com.project.banking.dto.response.format.SuccessResponse;
import com.project.banking.dto.response.profile.ProfileResponse;
import com.project.banking.service.ProfileService;
import com.project.banking.utils.constant.APIUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.PROFILE_API)
public class ProfileController {
    private final ProfileService profileService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<List<ProfileResponse>>> getAllProfiles(
            @RequestParam(name = "city", required = false) String city
    ) {
        List<ProfileResponse> list = profileService.getAll(city);
        SuccessResponse<List<ProfileResponse>> response = SuccessResponse.<List<ProfileResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @GetMapping(
            path = APIUrl.PATH_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<ProfileResponse>> getProfileById(@PathVariable String id) {
        ProfileResponse profile = profileService.getById(id);
        SuccessResponse<ProfileResponse> response = SuccessResponse.<ProfileResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(profile)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<ProfileResponse>> updateProfile(@RequestBody UpdateProfileRequest request) {
        ProfileResponse profile = profileService.update(request);
        SuccessResponse<ProfileResponse> response = SuccessResponse.<ProfileResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(profile)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
