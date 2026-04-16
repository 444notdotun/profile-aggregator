package com.apiintegration.hngstage1profileaggregator.service.serviceinterface;

import com.apiintegration.hngstage1profileaggregator.data.repository.ProfileRepository;
import com.apiintegration.hngstage1profileaggregator.dtos.response.ProfileResponse;
import com.apiintegration.hngstage1profileaggregator.dtos.response.ServiceResponse;
import com.apiintegration.hngstage1profileaggregator.exception.ProfileExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProfileServiceTest {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void testThatGetProfileCanSave(){
        String name = "Adedotun";
        ServiceResponse<ProfileResponse> profileResponse = profileService.createProfile(name);
        assertNotNull(profileResponse);
        assertTrue(profileRepository.existsById(profileResponse.getData().getId()));
        System.out.println(profileResponse);
    }
    @Test
    void testThatGetProfileReturnsTrueIfProfileExists(){
        String name = "tochi";
        profileService.createProfile(name);
       ServiceResponse< ProfileResponse> profileResponse = profileService.createProfile(name);
        assertNull(profileResponse);
        assertTrue(profileRepository.existsById(profileResponse.getData().getId()));
        ServiceResponse< ProfileResponse> profileResponse1= profileService.createProfile(name);
        assertTrue(profileResponse1.isStatus());
    }



}