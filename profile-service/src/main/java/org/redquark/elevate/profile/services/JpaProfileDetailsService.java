package org.redquark.elevate.profile.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redquark.elevate.profile.repositories.ProfileRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaProfileDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.info("Loading profile by username from the repository...");
        return profileRepository.findByUsername(username)
                .map(user -> User
                        .builder()
                        .username(username)
                        .password(user.getPassword())
                        .build()).orElseThrow(() -> new UsernameNotFoundException(
                        "User with username [%s] not found".formatted(username)
                ));

    }
}
