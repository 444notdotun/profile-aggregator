package com.apiintegration.hngstage1profileaggregator.service.serviceinterface;

import com.apiintegration.hngstage1profileaggregator.data.repository.ProfileRepository;
import com.apiintegration.hngstage1profileaggregator.dtos.response.ProfileResponse;
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
        String name = "adedotun";
        ProfileResponse profileResponse = profileService.createProfile(name);
        assertNotNull(profileResponse);
        assertTrue(profileRepository.existsById(profileResponse.getId()));
    }



}