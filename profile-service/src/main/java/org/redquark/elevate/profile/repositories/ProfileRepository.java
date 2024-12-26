package org.redquark.elevate.profile.repositories;

import org.redquark.elevate.profile.domains.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
