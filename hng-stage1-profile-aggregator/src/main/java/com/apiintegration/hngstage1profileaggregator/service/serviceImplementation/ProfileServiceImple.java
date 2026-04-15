package com.apiintegration.hngstage1profileaggregator.service.serviceImplementation;

import com.apiintegration.hngstage1profileaggregator.data.model.Country;
import com.apiintegration.hngstage1profileaggregator.data.model.Profile;
import com.apiintegration.hngstage1profileaggregator.data.repository.ProfileRepository;
import com.apiintegration.hngstage1profileaggregator.dtos.response.AgeApiResponse;
import com.apiintegration.hngstage1profileaggregator.dtos.response.GenderizeApiResponse;
import com.apiintegration.hngstage1profileaggregator.dtos.response.NationalityApiResponse;
import com.apiintegration.hngstage1profileaggregator.dtos.response.ProfileResponse;
import com.apiintegration.hngstage1profileaggregator.service.serviceinterface.AgeApi;
import com.apiintegration.hngstage1profileaggregator.service.serviceinterface.ProfileService;
import com.apiintegration.hngstage1profileaggregator.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProfileServiceImple implements ProfileService {
    @Autowired
    private AgeApi ageApi;
    @Autowired
    private GeneralizeApi generalizeApi;
    @Autowired
    private NationalizeApi nationalizeApi;

    @Autowired
    private ProfileRepository profileRepository;


    @Override
    public ProfileResponse createProfile(String name) {

        CompletableFuture<AgeApiResponse> ageApiResponseCompletableFuture = CompletableFuture.supplyAsync(()->ageApi.getAge(name));
        CompletableFuture<GenderizeApiResponse> genderizeApiResponseCompletableFuture = CompletableFuture.supplyAsync(()->generalizeApi.getGender(name));
        CompletableFuture<NationalityApiResponse> nationalizeApiCompletableFuture = CompletableFuture.supplyAsync(()->nationalizeApi.getNationality(name));
        CompletableFuture.allOf(ageApiResponseCompletableFuture, genderizeApiResponseCompletableFuture, nationalizeApiCompletableFuture).join();
       Profile profile= Mapper.mapApiResponsesToProfile(ageApiResponseCompletableFuture,genderizeApiResponseCompletableFuture,nationalizeApiCompletableFuture);
       profile.setCountryId(determineCountry(nationalizeApiCompletableFuture.join().getCountry()).getCountryId());
       profile.setCountryProbability(determineCountry(nationalizeApiCompletableFuture.join().getCountry()).getProbability());
       profile.setAgeGroup(DetermineAgeGroup(ageApiResponseCompletableFuture.join().getAge()));
       profile.setAge(ageApiResponseCompletableFuture.join().getAge());
        profileRepository.save(profile);
        return Mapper.mapProfileToProfileResponse(profile);
    }


    private String DetermineAgeGroup(int age) {
        String ageGroup = "";
        if(age >=0 && age <= 12){
            ageGroup= "child";
        } else if (age >=13 && age <= 19 ){
            ageGroup= "teenager";
        } else if (age >=20 && age <= 59) {
            ageGroup= "adult";
        } else if (age>=60) {
            ageGroup= "senior";
        }
    return ageGroup;
    }

    private Country determineCountry(List<Country> countries) {
       Double highestProbability = 0.0;
       Country countryWithHighestProbability=null;
        for (Country country : countries ){
            if (country.getProbability() > highestProbability){
                countryWithHighestProbability = country;
                highestProbability = country.getProbability();
            }
        }
        assert countryWithHighestProbability != null;
        return countryWithHighestProbability;
    }
}
