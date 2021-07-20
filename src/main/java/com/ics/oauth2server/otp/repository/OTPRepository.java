package com.ics.oauth2server.otp.repository;

import com.ics.oauth2server.common.entities.SecureOTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<SecureOTP,Long> {
    Optional<SecureOTP> findByOtp(String otp);
}
