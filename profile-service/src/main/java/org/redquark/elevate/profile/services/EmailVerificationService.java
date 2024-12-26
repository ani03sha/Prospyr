package org.redquark.elevate.profile.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.domains.entities.Profile;
import org.redquark.elevate.profile.repositories.ProfileRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.GONE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationService {

    private final OtpService otpService;
    private final ProfileRepository profileRepository;
    private final JavaMailSender javaMailSender;

    @Async
    public void sendVerificationToken(UUID profileId, String email) {
        final String token = otpService.generateAndStoreOtp(profileId);
        final String emailVerificationUrl = "http://localhost:8080/api/v1/profiles/auth/email/verify?uid=%s&t=%s".formatted(profileId, token);
        final String emailText = """
                Click the link to verify your email:
                """ + emailVerificationUrl;
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Email verification");
        simpleMailMessage.setFrom("System");
        simpleMailMessage.setText(emailText);
        javaMailSender.send(simpleMailMessage);
    }

    public void resendVerificationToken(String email) {
        final Profile profile = profileRepository.findByEmail(email)
                .filter(p -> !p.isEmailVerified())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Email not found or already verified"));
        sendVerificationToken(profile.getId(), email);

    }

    @Transactional
    public Profile verifyEmail(UUID id, String token) {
        if (!otpService.isOtpValid(id, token)) {
            throw new ResponseStatusException(BAD_REQUEST, "Token invalid or expired");
        }
        otpService.deleteOtp(id);
        final Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(GONE, "User account has been deleted or deactivated"));
        if (profile.isEmailVerified()) {
            throw new ResponseStatusException(BAD_REQUEST, "Email is already verified");
        }
        profile.setEmailVerified(true);
        return profile;
    }
}
