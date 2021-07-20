package com.ics.oauth2server.useraccount.services;

import com.ics.oauth2server.common.entities.SecureOTP;
import com.ics.oauth2server.common.entities.SecureToken;
import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.ConstantExtension;
import com.ics.oauth2server.helper.DatabaseHelper;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.otp.OTPService;
import com.ics.oauth2server.otp.repository.OTPRepository;
import com.ics.oauth2server.security.email.EmailServices;
import com.ics.oauth2server.security.email.context.ForgetPasswordEmailContext;
import com.ics.oauth2server.security.models.CustomPrincipal;
import com.ics.oauth2server.security.response.ForgotPasswordResponse;
import com.ics.oauth2server.security.response.OTPVerificationResponse;
import com.ics.oauth2server.security.token.SecureTokenService;
import com.ics.oauth2server.security.token.repository.SecureTokenRepository;
import com.ics.oauth2server.security.twilio.request.SMSRequest;
import com.ics.oauth2server.security.twilio.service.TwilioService;
import com.ics.oauth2server.useraccount.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService{

    HelperExtension helperExtension = new HelperExtension();
    private List<UserAccount> userAccounts;
    private List<ForgotPasswordResponse> forgotPasswordResponses;
    private List<OTPVerificationResponse> otpVerificationResponses;

    @Value("${secure.otp.length}")
    private int otpLength;

    private final UserAccountRepository userAccountRepository;
    private final OTPService OTPService;
    private final OTPRepository smsRepository;
    private final EmailServices emailServices;
    private final TwilioService twilioService;
    private final SecureTokenService secureTokenService;
    private final SecureTokenRepository secureTokenRepository;

    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository,
                                  SecureTokenRepository secureTokenRepository,
                                  SecureTokenService secureTokenService,
                                  OTPService OTPService,
                                  OTPRepository smsRepository,
                                  EmailServices emailServices,
                                  TwilioService twilioService) {
        this.userAccountRepository = userAccountRepository;
        this.OTPService = OTPService;
        this.secureTokenService = secureTokenService;
        this.secureTokenRepository = secureTokenRepository;
        this.smsRepository = smsRepository;
        this.emailServices = emailServices;
        this.twilioService = twilioService;
    }

    @Override
    public APIResponse<ForgotPasswordResponse> forgotPassword(Long id, 
                                                              String username, 
                                                              String verificationType, 
                                                              CustomPrincipal principal) {
        forgotPasswordResponses = new ArrayList<>();
        userAccounts = userAccountRepository.get(id,username,null,null,null);
        if (!userAccounts.isEmpty()){
            if(userAccounts.get(0).isFlag()){
                // Throw User does not exist
            }
            if(userAccounts.get(0).isAccountVerified()){
                // send verification email to verify his account
            }
            ForgotPasswordResponse response = new ForgotPasswordResponse();
            response.setUsername(userAccounts.get(0).getUsername());
            response.setId(userAccounts.get(0).getId());
            forgotPasswordResponses.add(response);
            if(verificationType.equalsIgnoreCase("email")){
                sendEmailOfGeneratedOTP(otpLength,userAccounts.get(0));
            }
            else if(verificationType.equalsIgnoreCase("phone")){
                sendSMSOfGeneratedOTP(otpLength,userAccounts.get(0));
            }
            else{
                throw new IllegalStateException("Select Correct Option");
            }
            return new APIResponse<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), ConstantExtension.MAIL_SENT_FOR_PASSWORD_GENERATION,forgotPasswordResponses);
        }
        return new APIResponse<>(HttpStatus.OK.value(), HttpStatus.OK.toString(),"User does not exist!",forgotPasswordResponses);
    }

    @Override
    public APIResponse<OTPVerificationResponse> verifyOtp(String otp) {
        otpVerificationResponses = new ArrayList<>();
        if(helperExtension.isNullOrEmpty(otp)){
            throw new IllegalStateException(ConstantExtension.USER_WRONG_OTP);
        }
        Optional<SecureOTP> secureOTP = smsRepository.findByOtp(otp);
        // Not empty
        if(secureOTP.isPresent()){
            // Checking token expiry
            if(secureOTP.get().isExpired()){
                return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),ConstantExtension.WRONG_OTP,otpVerificationResponses);
            }
            UserAccount userAccount = userAccountRepository.findById(secureOTP.get().getUserAccount().getId());
            if(!helperExtension.isNullOrEmpty(userAccount)){
                // Checking if user already deleted
                if (!userAccount.isFlag()){
                    throw new IllegalStateException("User does not exist! contact to support team");
                }
                userAccount.setAccountNonLocked(false);
                OTPService.removeOTP(secureOTP.get());
                userAccountRepository.save(userAccount);
                // Generating unique secure token to reset the password for every user
                SecureToken secureToken= secureTokenService.createSecureToken();
                secureToken.setUserAccount(userAccount);

                secureTokenRepository.save(secureToken);

                otpVerificationResponses.add(getOtpVerificationResponse(userAccount, secureToken));

                return new APIResponse<>(HttpStatus.OK.value(), HttpStatus.OK.toString(),ConstantExtension.SUCCESS,otpVerificationResponses);
            }
            return new APIResponse<>(HttpStatus.OK.value(),HttpStatus.OK.toString(),ConstantExtension.USER_NOT_EXIST,otpVerificationResponses);
        }
        return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(),ConstantExtension.WRONG_OTP,otpVerificationResponses);
    }



    @Override
    public APIResponse<UserAccount> updatePermissions(Long id, String username, List<String> rolesList, DatabaseHelper databaseHelper) {
        return null;
    }


    @Override
    public void sendSMSOfGeneratedOTP(final int lengthOfOTP, UserAccount userAccount){
        SecureOTP secureOTP = OTPService.generateOTP(lengthOfOTP);
        secureOTP.setUserAccount(userAccount);
        smsRepository.save(secureOTP);
        ForgetPasswordEmailContext emailContext = new ForgetPasswordEmailContext();
        emailContext.init(userAccount);
        emailContext.setOTP(secureOTP.getOtp());
        try {
            twilioService.sendSMS(
                    new SMSRequest(
                            userAccount.getPhoneNo(),
                            "verification OTP is "+secureOTP.getOtp()
                    )
            );
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void sendEmailOfGeneratedOTP(final int lengthOfOTP,UserAccount userAccount){
        SecureOTP secureOTP = OTPService.generateOTP(lengthOfOTP);
        secureOTP.setUserAccount(userAccount);
        smsRepository.save(secureOTP);
        ForgetPasswordEmailContext emailContext = new ForgetPasswordEmailContext();
        emailContext.init(userAccount);
        emailContext.setOTP(secureOTP.getOtp());
        try {
            emailServices.sendEmail(emailContext);
        }
        catch (MessagingException e){
            e.printStackTrace();
        }
    }

    private OTPVerificationResponse getOtpVerificationResponse(UserAccount userAccount, SecureToken secureToken) {
        OTPVerificationResponse verificationResponse = new OTPVerificationResponse();
        verificationResponse.setId(userAccount.getId());
        verificationResponse.setUsername(userAccount.getUsername());
        verificationResponse.setEmailId(userAccount.getEmail());
        verificationResponse.setToken(secureToken.getToken());
        verificationResponse.setIsEnabled(userAccount.isEnabled());
        return verificationResponse;
    }
    
}
