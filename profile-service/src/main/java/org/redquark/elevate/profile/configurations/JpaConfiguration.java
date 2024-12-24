package org.redquark.elevate.profile.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA Auditing is used to set createdAt and updatedAt automatically
 * when an entity is saved or updated
 */
@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
    // This configuration enables JPA auditing for automatic timestamping
}
