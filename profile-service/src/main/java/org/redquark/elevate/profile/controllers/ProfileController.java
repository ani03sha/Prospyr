package org.redquark.elevate.profile.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.dtos.ProfileDto;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.redquark.elevate.profile.mappers.ProfileMapper;
import org.redquark.elevate.profile.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/profiles/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(final Authentication authentication) {
        log.info("Received request to retrieve user profile");
        final Profile profile = profileService.getProfileByUsername(authentication.getName());
        return ResponseEntity.ok(profileMapper.toProfileDto(profile));
    }
}
