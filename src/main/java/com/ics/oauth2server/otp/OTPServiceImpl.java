package com.ics.oauth2server.otp;


import com.ics.oauth2server.common.entities.SecureOTP;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.otp.repository.OTPRepository;
import com.ics.oauth2server.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class OTPServiceImpl implements OTPService {

    HelperExtension helperExtension=new HelperExtension();
    @Value("${secure.otp.expiredAfter}")
    private Long otpExpireAfterSeconds;

    private final OTPRepository smsRepository;
    private final UserRepository userRepository;

    @Autowired
    public OTPServiceImpl( OTPRepository smsRepository, UserRepository userRepository) {
        this.smsRepository = smsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SecureOTP generateOTP(int lengthOfOTP) {
        StringBuilder generatedOTP = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        SecureOTP secureOTP = new SecureOTP();
        try {
            secureRandom = SecureRandom.getInstance(secureRandom.getAlgorithm());
            for (int i = 0; i <lengthOfOTP ; i++) {
                generatedOTP.append(secureRandom.nextInt(9));
            }
            secureOTP.setOtp(generatedOTP.toString());
            secureOTP.setExpireAt(LocalDateTime.now().plusSeconds(otpExpireAfterSeconds));
            this.saveSecureOTP(secureOTP);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return secureOTP;
    }

    @Override
    public Boolean isOTPExpired() {
        return null;
    }

    @Override
    public void saveSecureOTP(SecureOTP secureOTP) {
        smsRepository.save(secureOTP);
    }

    @Override
    public SecureOTP findByOTP(String otp) {
        SecureOTP secureOTP = smsRepository.findByOtp(otp).orElse(null);
        if(helperExtension.isNullOrEmpty(secureOTP)){
            throw new IllegalStateException("Either OTP is invalid or has been expired");
        }
        return secureOTP;
    }

    @Override
    public void removeOTP(SecureOTP secureOTP) {
        smsRepository.delete(secureOTP);
    }

}
