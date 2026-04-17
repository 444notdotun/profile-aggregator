package com.apiintegration.hngstage1profileaggregator.service.serviceinterface;

import com.apiintegration.hngstage1profileaggregator.data.repository.ProfileRepository;
import com.apiintegration.hngstage1profileaggregator.dtos.request.GetProfilesRequest;
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

    GetProfilesRequest getProfilesRequest = new GetProfilesRequest();

    @Test
    void testThatGetProfileCanSave(){
        getProfilesRequest.setName("Adedotun");
        ServiceResponse<ProfileResponse> profileResponse = profileService.createProfile(getProfilesRequest);
        assertNotNull(profileResponse);
        assertTrue(profileRepository.existsById(profileResponse.getData().getId()));
        System.out.println(profileResponse);
    }
    @Test
    void testThatGetProfileReturnsTrueIfProfileExists(){
        getProfilesRequest.setName("tochi");
        profileService.createProfile(getProfilesRequest);
       ServiceResponse< ProfileResponse> profileResponse = profileService.createProfile(getProfilesRequest);
        assertNotNull(profileResponse);
        assertTrue(profileRepository.existsById(profileResponse.getData().getId()));
        ServiceResponse< ProfileResponse> profileResponse1= profileService.createProfile(getProfilesRequest);
        assertTrue(profileResponse1.isStatus());
    }

    @Test
    void testThatProfileCanBeGottenById(){
        getProfilesRequest.setName("Adedotun");
        profileService.createProfile(getProfilesRequest);
        ServiceResponse< ProfileResponse> profileResponse = profileService.createProfile(getProfilesRequest);
        assertNotNull(profileResponse);
        assertTrue(profileRepository.existsById(profileResponse.getData().getId()));
        ProfileResponse profileResponse1 = profileService.getProfileById(profileResponse.getData().getId());
        assertNotNull(profileResponse1);
        assertEquals(profileResponse.getData(),profileResponse1);
    }

    @Test
    void testThatProfileReturnsExceptionIfProfileDoesNotExist(){
        assertThrows(ProfileExistException.class,()->profileService.getProfileById("123456789"));
    }



}