package com.apiintegration.hngstage1profileaggregator.data.repository;

import com.apiintegration.hngstage1profileaggregator.data.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,String> {
}
