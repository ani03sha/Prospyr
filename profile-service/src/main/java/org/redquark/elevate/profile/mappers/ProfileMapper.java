package org.redquark.elevate.profile.mappers;

import org.redquark.elevate.profile.domains.dtos.ProfileDto;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public ProfileDto toProfileDto(final Profile profile) {
        return new ProfileDto(profile.getEmail(), profile.getUsername());
    }
}
