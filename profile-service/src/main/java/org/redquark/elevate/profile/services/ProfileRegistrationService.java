package org.redquark.elevate.profile.services;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.redquark.elevate.profile.domains.requests.RegistrationRequest;
import org.redquark.elevate.profile.repositories.ProfileRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileRegistrationService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Profile registerProfile(RegistrationRequest request) {
        log.info("Checking if the username and password are already in use...");
        if (profileRepository.existsByUsername(request.username()) || profileRepository.existsByEmail(request.email())) {
            log.error("Username or email already in use. Returning...");
            throw new ValidationException("Username or email already exists");
        }
        log.info("Creating a new profile...");
        Profile profile = new Profile();
        profile.setUsername(request.username());
        profile.setEmail(request.email());
        profile.setPassword(passwordEncoder.encode(request.password()));
        log.info("Saving the profile in the database...");
        return profileRepository.save(profile);
    }
}
