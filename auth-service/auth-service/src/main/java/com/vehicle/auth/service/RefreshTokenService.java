package com.vehicle.auth.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vehicle.auth.entity.RefreshToken;
import com.vehicle.auth.repository.RefreshTokenRepository;

@Service
@Transactional
public class RefreshTokenService {

    private static final long REFRESH_EXPIRY = 7L * 24 * 60 * 60;

    private final RefreshTokenRepository repo;

    public RefreshTokenService(RefreshTokenRepository repo) {
        this.repo = repo;
    }

    public RefreshToken createRefreshToken(String username) {

        repo.deleteByUsername(username);

        RefreshToken refreshToken = new RefreshToken();
refreshToken.setToken(UUID.randomUUID().toString());
refreshToken.setUsername(username);
refreshToken.setExpiryDate(
        Instant.now().plusSeconds(REFRESH_EXPIRY)
);


        return repo.save(refreshToken);
    }

    public RefreshToken verifyToken(String token) {

        RefreshToken refreshToken = repo.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        return refreshToken;
    }

    public void revokeToken(String token) {
        repo.deleteByToken(token);
    }
}
