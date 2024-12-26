package org.redquark.elevate.profile.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.dtos.ProfileDto;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.redquark.elevate.profile.mappers.ProfileMapper;
import org.redquark.elevate.profile.services.EmailVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/profiles/auth/email")
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;
    private final ProfileMapper profileMapper;

    @PostMapping(value = "/resend-verification")
    public ResponseEntity<Void> resendVerificationLink(@RequestParam String email) {
        emailVerificationService.resendVerificationToken(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<ProfileDto> verifyEmail(@RequestParam("uid") String uid, @RequestParam("t") String token) {
        final Profile verifiedProfile = emailVerificationService.verifyEmail(UUID.fromString(uid), token);
        return ResponseEntity.ok(profileMapper.toProfileDto(verifiedProfile));
    }
}
