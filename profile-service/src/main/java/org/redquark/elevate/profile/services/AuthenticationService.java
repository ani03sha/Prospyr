package org.redquark.elevate.profile.services;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.dtos.AuthenticationRequestDto;
import org.redquark.elevate.profile.domains.dtos.AuthenticationResponseDto;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.redquark.elevate.profile.domains.entities.RefreshToken;
import org.redquark.elevate.profile.repositories.ProfileRepository;
import org.redquark.elevate.profile.repositories.RefreshTokenRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ProfileRepository profileRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationResponseDto authenticate(final AuthenticationRequestDto request) {
        log.info("Trying to authenticated the user...");
        // Authenticate the user
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        log.info("Is user authenticated: {}", authentication.isAuthenticated() ? "true" : "false");
        // Generate JWT access token
        final String accessToken = jwtService.generateToken(request.username());
        // Fetch profile and create refresh token
        final Profile profile = profileRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED));
        final RefreshToken refreshToken = new RefreshToken();
        refreshToken.setProfile(profile);
        refreshToken.setExpiresAt(Instant.now().plusSeconds(900));
        refreshTokenRepository.save(refreshToken);
        return new AuthenticationResponseDto(accessToken, refreshToken.getId());
    }

    public AuthenticationResponseDto refreshToken(UUID refreshToken) {
        final Optional<RefreshToken> refreshTokenEntity = Optional.ofNullable(refreshTokenRepository.findByIdAndExpiresAtAfter(refreshToken, Instant.now())
                .orElseThrow(() -> new ValidationException("Invalid or expired refresh token")));
        final String newAccessToken = jwtService.generateToken(refreshTokenEntity.get().getProfile().getUsername());
        return new AuthenticationResponseDto(newAccessToken, refreshToken);
    }

    public void revokeRefreshToken(UUID refreshToken) {
        refreshTokenRepository.deleteById(refreshToken);
        log.info("Refresh token deleted");
    }
}
