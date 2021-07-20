package com.ics.oauth2server.otp;

import com.ics.oauth2server.common.entities.SecureOTP;

public interface OTPService {
    SecureOTP generateOTP(final int lengthOfOTP);
    Boolean isOTPExpired();
    void saveSecureOTP(final SecureOTP otp);
    SecureOTP findByOTP(final String token);
    void removeOTP(final SecureOTP token);
}
