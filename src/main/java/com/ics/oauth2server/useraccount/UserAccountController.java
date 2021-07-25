package com.ics.oauth2server.useraccount;

import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.security.models.CustomPrincipal;
import com.ics.oauth2server.useraccount.request.ChangePasswordRequest;
import com.ics.oauth2server.useraccount.request.ResetPasswordRequest;
import com.ics.oauth2server.useraccount.response.ChangePasswordResponse;
import com.ics.oauth2server.useraccount.response.OTPVerificationResponse;
import com.ics.oauth2server.useraccount.response.ForgotPasswordResponse;
import com.ics.oauth2server.useraccount.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(
        path = "${api.version}/user-account/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
)
public class UserAccountController {

    HelperExtension helperExtension = new HelperExtension();

    private APIResponse apiResponse;

    private final UserAccountService userAccountService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @RequestMapping(
            path = {
                    "forgot-password/user/id/{userId}",
                    "/forgot-password/user/username/{username}"
            },
            method = RequestMethod.POST
    )
    public ResponseEntity<APIResponse<ForgotPasswordResponse>> forgotPassword(@PathVariable(name = "userId",required = false) Long userId,
                                                                       @PathVariable(name = "username",required = false) String username,
                                                                       @RequestParam(name = "verificationType", required = true) String verificationType,
                                                                       CustomPrincipal principal,
                                                                       HttpServletRequest httpServletRequest){
        if (helperExtension.isNullOrEmpty(userId)){
            userId = 0L;
        }
        apiResponse = userAccountService.forgotPassword(userId,username,verificationType,principal,httpServletRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @RequestMapping(path = "verify/otp/{otp}",method = RequestMethod.POST)
    public ResponseEntity<APIResponse<OTPVerificationResponse>> verifyOTP(@PathVariable(name = "otp") String otp){
        apiResponse = userAccountService.verifyOtp(otp);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @RequestMapping(
            path = {
                    "reset-password"
            },
            method = RequestMethod.PUT)
    public ResponseEntity<APIResponse<ChangePasswordResponse>> resetPassword(@RequestBody ResetPasswordRequest request,
                                                                             @RequestParam(name = "token",required = true) String token,
                                                                             HttpServletRequest httpServletRequest){
        apiResponse = userAccountService.resetPassword(token,request,httpServletRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @RequestMapping(
            path = {
                    "change-password/user/id/{id}",
                    "change-password/user/username/{username}"
            },
            method = RequestMethod.PUT)
    public ResponseEntity<APIResponse<ChangePasswordResponse>> changePassword(@PathVariable(name = "id", required = false) Long id,
                                                                           @PathVariable(name = "username", required = false) String username,
                                                                           @RequestBody ChangePasswordRequest request,
                                                                           HttpServletRequest httpServletRequest){
        apiResponse = userAccountService.changePassword(id,username,request,httpServletRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

}
