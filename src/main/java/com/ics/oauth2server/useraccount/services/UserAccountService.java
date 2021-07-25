package com.ics.oauth2server.useraccount.services;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.DatabaseHelper;
import com.ics.oauth2server.security.models.CustomPrincipal;
import com.ics.oauth2server.useraccount.request.ChangePasswordRequest;
import com.ics.oauth2server.useraccount.request.ResetPasswordRequest;
import com.ics.oauth2server.useraccount.response.ChangePasswordResponse;
import com.ics.oauth2server.useraccount.response.ForgotPasswordResponse;
import com.ics.oauth2server.useraccount.response.OTPVerificationResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserAccountService {
    APIResponse<ForgotPasswordResponse> forgotPassword(Long id, String Username, String verificationType, CustomPrincipal principal, HttpServletRequest httpServletRequest);
    APIResponse<UserAccount> updatePermissions(Long id, String username, List<String> rolesList, DatabaseHelper databaseHelper);
    APIResponse<OTPVerificationResponse> verifyOtp(String otp);
    APIResponse<ChangePasswordResponse> resetPassword(String token, ResetPasswordRequest request, HttpServletRequest httpServletRequest);
    APIResponse<ChangePasswordResponse> changePassword(Long id, String username, ChangePasswordRequest request, HttpServletRequest httpServletRequest);
    void sendSMSOfGeneratedOTP(final int lengthOfOTP, UserAccount userAccount);
    void sendEmailOfGeneratedOTP(final int lengthOfOTP,UserAccount userAccount);

    }
