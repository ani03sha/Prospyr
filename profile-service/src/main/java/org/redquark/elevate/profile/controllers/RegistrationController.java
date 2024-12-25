package org.redquark.elevate.profile.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.dtos.RegistrationRequestDto;
import org.redquark.elevate.profile.domains.dtos.RegistrationResponseDto;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.redquark.elevate.profile.mappers.ProfileRegistrationMapper;
import org.redquark.elevate.profile.services.ProfileRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/profiles/auth")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final ProfileRegistrationService profileRegistrationService;
    private final ProfileRegistrationMapper profileRegistrationMapper;

    @PostMapping(value = "/register")
    public ResponseEntity<RegistrationResponseDto> registerProfile(@Valid @RequestBody final RegistrationRequestDto registrationRequestDto) {
        log.info("Received request to create a new profile with username: {} and email: {}", registrationRequestDto.username(), registrationRequestDto.email());
        final Profile registeredProfile = profileRegistrationService.registerProfile(profileRegistrationMapper.toEntity(registrationRequestDto));
        return ResponseEntity.ok(profileRegistrationMapper.toRegistrationResponseDto(registeredProfile));
    }
}
