package com.apiintegration.hngstage1profileaggregator.controller;

import com.apiintegration.hngstage1profileaggregator.dtos.request.GetProfilesRequest;
import com.apiintegration.hngstage1profileaggregator.dtos.response.*;
import com.apiintegration.hngstage1profileaggregator.service.serviceinterface.ProfileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@CrossOrigin(origins = "*")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/api/profiles")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(@Valid @RequestBody GetProfilesRequest getProfilesRequest) {
        ServiceResponse<ProfileResponse> profileResponseServiceResponse =profileService.createProfile(getProfilesRequest);
        if(profileResponseServiceResponse.isStatus()){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(profileResponseServiceResponse.getData(), "Profile already exist"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(profileResponseServiceResponse.getData()));
    }


    @GetMapping("/api/profiles/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfileById(@NotNull(message = "id can not be empty") @PathVariable String id) {
        ProfileResponse profileResponse = profileService.getProfileById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(profileResponse));
    }
    @GetMapping("/api/profiles")
    public ResponseEntity<SpecificationApiResponse> getFilteredProfiles(@RequestParam(required = false)
                                                                                         @Pattern(regexp = "^(male|female)$", message="gender can only be male or female")
                                                                                         String gender,
                                                                                     @RequestParam(required = false)
                                                                                     @Pattern(regexp = "^(child|teenager|adult|senior)$", message="ageGroup can only be child, teenager, adult, senior")
                                                                                     String ageGroup,
                                                                                     @RequestParam(required = false)
                                                                                         String countryId) {
        List<Summary> profiles = profileService.getProfiles(gender,ageGroup,countryId);
        return ResponseEntity.status(HttpStatus.OK).body(new SpecificationApiResponse(profiles.size(),profiles));
    }

    @DeleteMapping("/api/profiles/{id}")
    public ResponseEntity<ApiResponse<ProfileResponse>> deleteProfile(@NotNull(message = "id can not be empty") @PathVariable String id) {
        profileService.deleteProfile(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse<>(null));
    }
}