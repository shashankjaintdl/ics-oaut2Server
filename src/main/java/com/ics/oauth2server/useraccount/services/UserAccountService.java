package com.ics.oauth2server.useraccount.services;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.DatabaseHelper;
import com.ics.oauth2server.security.models.CustomPrincipal;
import com.ics.oauth2server.security.response.ForgotPasswordResponse;
import com.ics.oauth2server.security.response.OTPVerificationResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserAccountService {
    APIResponse<ForgotPasswordResponse> forgotPassword(Long id, String Username, String verificationType, CustomPrincipal principal, HttpServletRequest httpServletRequest);
    APIResponse<UserAccount> updatePermissions(Long id, String username, List<String> rolesList, DatabaseHelper databaseHelper);
    APIResponse<OTPVerificationResponse> verifyOtp(String otp);
    void sendSMSOfGeneratedOTP(final int lengthOfOTP, UserAccount userAccount);
    void sendEmailOfGeneratedOTP(final int lengthOfOTP,UserAccount userAccount);

    }
