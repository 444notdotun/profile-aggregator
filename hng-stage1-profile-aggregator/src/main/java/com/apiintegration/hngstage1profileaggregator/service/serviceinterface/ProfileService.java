package com.apiintegration.hngstage1profileaggregator.service.serviceinterface;

import com.apiintegration.hngstage1profileaggregator.dtos.response.ProfileResponse;

public interface ProfileService {
    ProfileResponse createProfile(String name);
}
