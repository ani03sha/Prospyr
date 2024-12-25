package org.redquark.elevate.profile.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.dtos.ProfileDto;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.redquark.elevate.profile.repositories.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.GONE;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile getProfileByUsername(String username) {
        log.info("Retrieving the profile of the user with username: {}", username);
        return profileRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(GONE, "The profile has been deleted or inactivated"));
    }
}
