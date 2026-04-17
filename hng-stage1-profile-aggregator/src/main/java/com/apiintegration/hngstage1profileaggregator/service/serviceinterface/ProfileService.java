package com.apiintegration.hngstage1profileaggregator.service.serviceinterface;

import com.apiintegration.hngstage1profileaggregator.data.model.Profile;
import com.apiintegration.hngstage1profileaggregator.dtos.request.GetProfilesRequest;
import com.apiintegration.hngstage1profileaggregator.dtos.response.ProfileResponse;
import com.apiintegration.hngstage1profileaggregator.dtos.response.ServiceResponse;
import com.apiintegration.hngstage1profileaggregator.dtos.response.Summary;

import java.util.List;

public interface ProfileService {
    ServiceResponse<ProfileResponse> createProfile(GetProfilesRequest getProfilesRequest);

    ProfileResponse getProfileById(String id);

    List<Summary> getProfiles(String gender,String ageGroup,String countryId);
    void deleteProfile(String id);
}
