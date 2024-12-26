package org.redquark.elevate.profile.mappers;

import org.redquark.elevate.profile.domains.dtos.RegistrationRequestDto;
import org.redquark.elevate.profile.domains.dtos.RegistrationResponseDto;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class ProfileRegistrationMapper {

    public Profile toEntity(RegistrationRequestDto registrationRequestDto) {
        final Profile profile = new Profile();
        profile.setUsername(registrationRequestDto.username());
        profile.setEmail(registrationRequestDto.email());
        profile.setPassword(registrationRequestDto.password());
        return profile;
    }

    public RegistrationResponseDto toRegistrationResponseDto(final Profile profile) {
        return new RegistrationResponseDto(profile.getUsername(), profile.getEmail());
    }
}
