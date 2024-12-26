package org.redquark.elevate.profile.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final RedisTemplate<String, String> redisTemplate;

    public String generateAndStoreOtp(final UUID id) {
        final String otp = generateOtp();
        final String cacheKey = getCacheKey(id);
        redisTemplate.opsForValue().set(cacheKey, otp, Duration.ofMinutes(5));
        return otp;
    }

    public boolean isOtpValid(final UUID id, final String otp) {
        final String cacheKey = getCacheKey(id);
        return Objects.equals(redisTemplate.opsForValue().get(cacheKey), otp);
    }

    public void deleteOtp(final UUID id) {
        final String cacheKey = getCacheKey(id);
        redisTemplate.delete(cacheKey);
    }

    private String getCacheKey(UUID id) {
        return "otp:%s".formatted(id);
    }

    private String generateOtp() {
        final StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            final int index = SECURE_RANDOM.nextInt("ABCDEFG123456789".length());
            otp.append("ABCDEFG123456789".charAt(index));
        }
        return otp.toString();
    }
}
