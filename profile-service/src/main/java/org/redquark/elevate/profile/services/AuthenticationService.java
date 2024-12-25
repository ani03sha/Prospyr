package org.redquark.elevate.profile.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.dtos.AuthenticationRequestDto;
import org.redquark.elevate.profile.domains.dtos.AuthenticationResponseDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationResponseDto authenticate(final AuthenticationRequestDto request) {
        log.info("Trying to authenticated the user...");
        final UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password());
        final Authentication authentication = authenticationManager.authenticate(authToken);
        final String token = jwtService.generateToken(request.username());
        log.info("Authentication token generated successfully!!!");
        return new AuthenticationResponseDto(token);
    }
}
