package org.redquark.elevate.profile.domains.dtos;

import java.util.UUID;

public record AuthenticationResponseDto(String token, UUID refreshToken) {
}
