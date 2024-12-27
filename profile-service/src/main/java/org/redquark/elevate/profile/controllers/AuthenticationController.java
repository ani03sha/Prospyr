package org.redquark.elevate.profile.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.dtos.AuthenticationRequestDto;
import org.redquark.elevate.profile.domains.dtos.AuthenticationResponseDto;
import org.redquark.elevate.profile.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/profiles/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody final AuthenticationRequestDto request) {
        log.info("Received request to authenticate");
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestParam UUID refreshToken) {
        final AuthenticationResponseDto authenticationResponseDto = authenticationService.refreshToken(refreshToken);
        return ResponseEntity.ok(authenticationResponseDto);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(@RequestParam UUID refreshToken) {
        authenticationService.revokeRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
