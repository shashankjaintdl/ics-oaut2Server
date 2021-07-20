package com.ics.oauth2server.user.services;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.common.entities.SecureToken;
import com.ics.oauth2server.common.entities.User;
import com.ics.oauth2server.common.entities.UserAccount;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.ConstantExtension;
import com.ics.oauth2server.helper.DatabaseHelper;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.security.email.EmailServices;
import com.ics.oauth2server.security.email.context.AccountVerificationEmailContext;
import com.ics.oauth2server.security.repository.UserAccountRepository;
import com.ics.oauth2server.security.token.SecureTokenService;
import com.ics.oauth2server.security.token.repository.SecureTokenRepository;
import com.ics.oauth2server.user.UserMapper;
import com.ics.oauth2server.user.UserRequest;
import com.ics.oauth2server.user.exception.UserAlreadyExistException;
import com.ics.oauth2server.user.repository.UserJPARepository;
import com.ics.oauth2server.user.repository.UserRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    Date currentDate = DateTime.now().toDate();
    UserMapper mapper = new UserMapper();
    HelperExtension helperExtension = new HelperExtension();
    List<User> list = new ArrayList<>();

    @Value("${site.base.url.http}")
    private String baseURL;

    @Value("${api.version}")
    private String apiVersion;

    private final UserRepository userRepository;
    private final UserJPARepository userJPARepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureTokenService secureTokenService;
    private final SecureTokenRepository secureTokenRepository;
    private final EmailServices emailServices;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserAccountRepository userAccountRepository,
                           PasswordEncoder passwordEncoder,
                           UserJPARepository userJPARepository,
                           SecureTokenRepository secureTokenRepository,
                           SecureTokenService secureTokenService,
                           EmailServices emailServices) {
        this.userRepository = userRepository;
        this.userJPARepository = userJPARepository;
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.secureTokenRepository = secureTokenRepository;
        this.secureTokenService  = secureTokenService;
        this.emailServices =  emailServices;
    }

    @Override
    public APIResponse<User> save(UserRequest userRequest) {
        list = new ArrayList<>();
        User user = mapper.map(userRequest);
        String message = userRepository.exist(user);
        if(!helperExtension.isNullOrEmpty(message)){
            throw new UserAlreadyExistException(message);
        }
        try {
            user.setCreatedDate(currentDate);
            user.setUpdatedDate(currentDate);
            user.setIsFlag(true);
            userRepository.save(user);
            assignRolesAndPermissions(user,userRequest.getPassword());
            list.add(user);
        }
        catch (Exception e){
            LOGGER.error("Error while creating the user account with username: [ " + user.getUsername() + " ]");
            e.printStackTrace();
            LOGGER.error("Rollback and delete the user from User with username: [ " + user.getUsername() + " ]");
            userRepository.deleteById(User.class,user.getId());
            throw new IllegalStateException(ConstantExtension.ACCOUNT_CREATION_FAILED);
        }
        LOGGER.info("User Account with username: [ "+ user.getUsername() + " ] has been created successfully!");
        return new APIResponse(HttpStatus.CREATED.value(), HttpStatus.CREATED.toString(), ConstantExtension.ACCOUNT_SUCCESS_CREATED, list);
    }

    private void assignRolesAndPermissions(User user,String password){
        LOGGER.info("Creating and assigning the role of user in User Account");
        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(user.getUsername());
        userAccount.setEmail(user.getEmailId());
        userAccount.setPhoneNo(user.getPhoneNo());
        userAccount.setPassword(passwordEncoder.encode(password));
        List<Roles> rolesList = new ArrayList<>();
        Roles roles = new Roles();
        roles.setId(2);
        rolesList.add(roles);
        userAccount.setUserId(user.getId());
        userAccount.setRoles(rolesList);
        userAccount.setFlag(true);
        userAccountRepository.save(userAccount);
        sendRegistrationConfirmationEmail(userAccount);
    }

    @Override
    public void sendRegistrationConfirmationEmail(UserAccount user) {
        LOGGER.info("sending the confirmation Email to verify the account");
        SecureToken secureToken= secureTokenService.createSecureToken();
        secureToken.setUserAccount(user);
        secureTokenRepository.save(secureToken);
        AccountVerificationEmailContext emailContext = new AccountVerificationEmailContext();
        emailContext.init(user);
        emailContext.setToken(secureToken.getToken());
        emailContext.buildVerificationUrl(baseURL + apiVersion, secureToken.getToken());
        try {
            emailServices.sendEmail(emailContext);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public APIResponse<User> delete(Long id, String username) {
        list = new ArrayList<>();
        list = userRepository.get(id,username,null,true,null);
        if(list.size()>0){
            Boolean userDeleted = userRepository.deleteById(User.class,list.get(0).getId());
            if(userDeleted) {
                return new APIResponse<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), ConstantExtension.ACCOUNT_SUCCESS_DELETED, list);
            }
        }
        return new APIResponse<>(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.toString(),ConstantExtension.ACCOUNT_DELETION_FAILED,list);
    }

    @Override
    public APIResponse<User> get(Long id, String username, String phoneNo, DatabaseHelper databaseHelper, HttpServletRequest httpServletRequest) {
        list = new ArrayList<>();
        list = userRepository.get(id,username,phoneNo,true,databaseHelper);
        if(!list.isEmpty()){
            int listSize = userRepository.get(id,username,phoneNo,true,null).size();
            return new APIResponse<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.toString(), ConstantExtension.SUCCESS_RECEIVE, list, listSize,httpServletRequest);
        }
        return new APIResponse<>(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.toString(),ConstantExtension.USER_EMPTY_LIST ,list, httpServletRequest);
    }

    @Override
    public APIResponse<User> update(Long id, String username, UserRequest userRequest) {
        list = new ArrayList<>();
        list = userRepository.get(id,username,null,true,null);
        if(!list.isEmpty()){
            if (!helperExtension.isNullOrEmpty(userRequest.getFirstName())){
                list.get(0).setFirstName(userRequest.getFirstName());
            }
            if(!helperExtension.isNullOrEmpty(userRequest.getLastName())){
                list.get(0).setLastName(userRequest.getLastName());
            }
            if(!helperExtension.isNullOrEmpty(userRequest.getPhoneNo())){
                list.get(0).setPhoneNo(userRequest.getPhoneNo());
            }
            list.get(0).setUpdatedDate(currentDate);
            userRepository.saveOrUpdate(list.get(0));
            LOGGER.info("User with username: [ " + list.get(0).getUsername()+ " ] is successfully updated..");
            return new APIResponse<>(HttpStatus.OK.value(),HttpStatus.OK.toString(),ConstantExtension.ACCOUNT_UPDATED_SUCCESS,list);
        }
        return new APIResponse<>(HttpStatus.OK.value(),HttpStatus.OK.toString(),ConstantExtension.ACCOUNT_UPDATE_FAILED,list);
    }


}
