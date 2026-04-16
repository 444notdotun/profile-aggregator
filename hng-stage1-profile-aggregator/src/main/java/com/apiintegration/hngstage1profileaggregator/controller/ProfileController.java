package com.apiintegration.hngstage1profileaggregator.controller;

import com.apiintegration.hngstage1profileaggregator.dtos.response.ApiResponse;
import com.apiintegration.hngstage1profileaggregator.dtos.response.ProfileResponse;
import com.apiintegration.hngstage1profileaggregator.dtos.response.ServiceResponse;
import com.apiintegration.hngstage1profileaggregator.service.serviceinterface.ProfileService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@CrossOrigin(origins = "*")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/api/profiles")
    public ResponseEntity<ApiResponse<ServiceResponse<ProfileResponse>>> getProfile(@Pattern (regexp = "[a-zA-Z]+",message = "String is only allowed") @NotNull(message = "request can not be empty") @RequestParam String name) {
        ServiceResponse<ProfileResponse> profileResponseServiceResponse =profileService.createProfile(name);
        if(profileResponseServiceResponse.isStatus()){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(profileResponseServiceResponse, "Profile already exist"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(profileResponseServiceResponse));
    }
}