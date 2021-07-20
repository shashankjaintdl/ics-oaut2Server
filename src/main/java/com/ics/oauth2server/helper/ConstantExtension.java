package com.ics.oauth2server.helper;


public class ConstantExtension {

    // User Account Messages
    public static String ACCOUNT_CREATION_FAILED = "Account creation failed";
    public static String ACCOUNT_ALREADY_EXIST = "Account already exist";
    public static String ACCOUNT_ID_ALREADY_EXIST = "Id already exist";
    public static String ACCOUNT_SUCCESS_CREATED = "Account has been create successfully";
    public static String ACCOUNT_UPDATED_SUCCESS = "Account has been updated successfully";
    public static String ACCOUNT_UPDATE_FAILED = "Unable to update the account";
    public static String ACCOUNT_DELETION_FAILED = "Unable to delete the account";
    public static String ACCOUNT_SUCCESS_DELETED = "Account has been deleted successfully";
    public static String ACCOUNT_DISABLED = "Account has been disabled";
    public static String ACCOUNT_LOCKED = "Your account is locked after multiple attempts";
    public static String ACCOUNT_PASSWORD_EXPIRED = "Account password has been expired";


    /**
     *
     * User Messages
     *
     * */

    public static String USER_EMPTY_LIST = "There are no users";
    public static String USER_ACCOUNT_NOT_EXIST = "User does not exist!";

    /**
     *
     * Verification Notification
     *
     * */

    public static String EMAIL_VERIFICATION_FAILED = "Email verification failed";
    public static String EMAIL_VERIFICATION_SENT = "Verification email sent";
    public static String EMAIL_OTP_SENT = "OTP sent successfully to your registered e-mail id";
    public static String PHONE_OTP_SENT = "OTP sent successfully to your registered mobile number";
    public static String WRONG_OTP = "Either OTP is invalid or has been expired";



    // Common Messages
    public static String API_VERSION = "api/v3/";
    public static String SUCCESS = "Success";
    public static String SUCCESS_ADDED = "Successfully Added";
    public static String SUCCESS_UPDATED = "Successfully Updated";
    public static String SUCCESS_RECEIVE = "Successfully Fetched";
    public static String SUCCESS_DELETED = "Successfully Deleted";
    public static String SUCCESS_STATUS = "Status Successfully Updated";
    public static String SUCCESS_DOWNLOADED = "Successfully Downloaded";


    public static String NOT_AUTHORIZED = "Not Authorized to do this operation";
    public static String ERROR = "Internal Server Error";
    public static String MSG_PHONE_NUMBER_MISSING = "Phone Number is missing";
    public static String MESSAGE_LOGOUT = "Logout Successfully";
    public static String DELETION_ERROR = "Not Deleted!!";
    public static String EMAIL_ID_ALREADY_EXIST = "The email address already exists";
    public static String PHONE_NO_ALREADY_EXIST = "The phone number already exists";
    public static String MOBILE_NO_ALREADY_EXIST = "The Mobile no already exists";
    public static String NAME_ALREADY_EXIST = "Name already exists";
    public static String FAILURE = "Failure";
    public static String USER_WRONG_OTP = "Verification code is not Correct";
    public static String SESSION_EXPIRED = "Verification link is expired. Try again";
    public static String COMPANY_REGISTERED = "Company Registered Successfully";
    public static String WRONG_COMPANY_ID = "Company Id is not Correct";
    public static String STATUS_CHANGED = "Status Changed Successfully";
    public static String NULL_FIELDS = "Some fields are Null";
    public static String NULL_VALU = "Please Enter The Same Text";
    public static String SECTOR_EVAL_EMPTY = "This sector evaluation attribute are not available";
    public static String APP_FORM_EMPTY = "There is no approved form of selected Sector and Sub Unit respectively";

    public static String FORM_SAVED = "Form Successfully Saved";
    public static String FORM_SUBMITTED = "Form Successfully Submitted and Moved To Submitted";
    public static String FORM_APPROVED = "Form Successfully Approved and Moved To Evaluation Sheet";
    public static String FORM_RETURNED = "Form Successfully Sent Back For Correction";
    public static String FORM_DRAFT = "Form Successfully Moved to Draft";
    public static String FORM_REJECTED = "Form Successfully Rejected and Moved To Rejected";

    /** Status Ends **/

    /******* Paths Start **************/

    public static String DatabasePathEval = "data/eval/";
    /******* Paths End **************/

    /************** ACL Starts *********************/

    public static final String ROLE_SAD = "ROLE_SAD";

    public static final String ROLE_AD = "ROLE_AD";
    public static final String ROLE_SUP = "ROLE_SUP";
    public static final String ROLE_DEV = "ROLE_DEV";
    public static final String ROLE_CUS = "ROLE_CUS";
    public static final String ROLE_VEN = "ROLE_VEN";
    public static final String ROLE_DEL = "ROLE_DEL";
    public static final String ROLE_SDEV = "ROLE_SDEV";
    public static final String ROLE_DEV_EMP = "ROLE_DEV_EMP";
    public static final String ROLE_INS = "ROLE_INS";
    public static final String ROLE_STU = "ROLE_STU";
;


    /*************** state management Starts *******/
    public static final String STATE_REQ = "REQUESTED";
    public static final String STATE_APPRVD = "APPROVED";
    public static final String STATE_DISSPRVD = "DISSAPPROVED";

    public static final String REQUESTED = "REQUEST SENT SUCCESSFULLY";
    public static final String APPROVED = "REQUEST APPROVED SUCCESSFULLY";
    public static final String DISSAPROVED = "REQUEST DISSAPROVED SUCCESSFULLY";
    public static final String REFERRAL_ERROR = "REFERRAL CODE NOT AVAILABEL";
    public static final String MAX_LIMIT_REACHED = "MAXIMUM EPLOYEE CREATION LIMIT REACHED";

    public static final String VENDORE_CREATE_FAILED = "CAN NOT CREATE MORE VENDORE ON THIS PACKAGE";

    public static final String NO_ENTITY_FOUND = "NO ENTITY FOUND";

    public static final String APP_RELEASE_NOT_FOUND = "App Release data not found";

    public static final String CLIENT_SECRET_NOT_FOUND = "Client Secret not found";

    public static final String NOT_EXIST = "Entity not exist";

    public static final String INCOMPLETE_DATE = "Please fill-in required details";

    //Activity Logs
    public static String Activity_Profile_Edited = "Profile Changed";
    public static String Activity_Review_Added = "Review Added";

    /**
     * UserDetails Starts
     **/
    public static String ROLE_NOT_EXIST = "Some Roles are missing.You can Save form in draft only.";
    public static String USER_ADDED = "User Successfully Added";
    public static String USER_UPDATED = "User Successfully Updated";
    public static String USER_DUPLICATE = "Duplicate Entry for Phone Number";
    public static String USER_DISABLE_FAIL = "User can't be disabled because its last user as sysadmin";
    public static String USER_PASSWORD_CHANGED = "Password Changed Successfully";
    public static String USER_WRONG_FIVE_PASSWORD = "Your Password Can Not Same As Last Five password";
    public static String USER_WRONG_PASSWORD = "Password is not Correct";
    public static String MAIL_SENT_FOR_PASSWORD_GENERATION = "Password generation link has been sent on your email id";
    public static String USER_WRONG_USERNAME_PASSWORD = "User Name or Password is not Correct";
    public static String USER_NAME_ALREADY_EXIST = "User Name already exists";

    public static String USER_ALREADY_EXIST = "You Are already Registered Password generation link has been sent on your email id";
    // User Login
    public static String USER_LOGIN_SUCCESS = "Welcome User";
    public static String USER_LOGIN_AUTH_FAILED = "You typed wrong Mobile number or Password";
    public static String USER_ = "Your account is Deactivated by admin";
    public static String USER_NOT_EXIST = "User or mailId doesn't exist";
    //	public static String NAME_ALREADY_EXIST = "Sector already exists";
    public static String USER_WRONG_VERIFCODE = "You typed wrong Verification code";
    public static String NOT_VERIFIDE = "User doesn't Verified please verify your email first";
    /** UserDetails Ends **/

    /**
     * Role Starts
     **/
    public static String ROLE_ADDED = "Role Successfully Added";
    public static String ROLE_UPDATED = "Role Successfully Updated";
    public static String ROLE_NAME_DUPLICATE = "Same Role Name already exist to this user";
    public static String ROLE_TYPE_UNIT_DUPLICATE = "Same User can not have active role in more than 1 unit";
    public static String ROLE_DISABLE_FAIL = "Role can't be disabled because its last role of sysadmin";
    public static String ROLE_DUPLICATE_SYS_ADMIN = "System admin already exist so misc data can't be inserted";
    public static String ROLE_USER_WRONG_UNIT = "User doesn't exist in selected unit";

    public static String ROLE_UNIT_ADMIN_EXIST = "Unit Admin already exist";
    public static String ROLE_VERIFIER1_EXIST = "Verifier already exist";
    public static String ROLE_VERIFIER2_EXIST = "Approver already exist";
    public static String ROLE_APPROVER_EXIST = "Approver Already exist";
    public static String ROLE_VERIFIER1_DOES_NOT_EXIST = "Verifier does not exist";
    public static String ROLE_VERIFIER2_DOES_NOT_EXIST = "Approver does not exist";
    public static String ROLE_APPROVER_DOES_NOT_EXIST = "Approver does not exist";

    /** Role Ends **/

    /************** ACL Ends *********************/


    public static String ALREADY_PRESENT = "ALREADY PRESENT";

    // Admin
    public static String ADMIN_SIZE = "You cannot create more than two admin at a time.";
    public static String ADMIN_DELETE_SIZE = "You cannot delete the admin because at a time atleast one admin should be present.";

    public static String MODULE_EXIST = "Please add atleast one Module";
    public static String OWNER_FREE = "Shop Owner Free";
    public static String OWNER_NOT_FREE = "Shop Owner Not Free";

    public static String CHANGE_ACTIVE_STATUS_SUCCESS = " is active now.";

    public static String CHANGE_DEACTIVE_STATUS_SUCCESS = " is deactive now.";

    public static String QTY_EXEED = "Quantity exedded.";

    public static String DUPLICATE_SERVICE_NUMBER = "You cannot use this service number because this service number is already associated to another user .";

}

