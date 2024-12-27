CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY,
    profile_id UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    expires_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT fk_profiles FOREIGN KEY (profile_id) REFERENCES profiles (id) ON DELETE CASCADE
);

CREATE INDEX idx_refresh_token_profile_id ON refresh_tokens (profile_id);