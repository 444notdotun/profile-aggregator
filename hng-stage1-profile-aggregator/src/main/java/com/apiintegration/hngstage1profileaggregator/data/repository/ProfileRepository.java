package com.apiintegration.hngstage1profileaggregator.data.repository;

import com.apiintegration.hngstage1profileaggregator.data.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile,String>, JpaSpecificationExecutor<Profile> {
    Optional<Profile> findByName(String name);
}
