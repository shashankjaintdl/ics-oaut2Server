package com.ics.oauth2server.user;

import com.ics.oauth2server.common.entities.User;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.DatabaseHelper;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "${api.version}/users/",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {


    private final UserService userService;
    HelperExtension helperExtension = new HelperExtension();



    private APIResponse<User> apiResponse;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = {"id/{id}","username/{username}","phone-no/{phoneNo}","all"},method = RequestMethod.GET)
    ResponseEntity<APIResponse<User>> getUsers(@PathVariable(name = "id", required = false) Long id,
                                               @PathVariable(name = "username", required = false) String username,
                                               @PathVariable(name = "phoneNo", required = false) String phoneNo,
                                               @RequestParam(value = "sortBy", defaultValue = "", required = false) String sortBy,
                                               @RequestParam(value = "search", defaultValue = "", required = false) String search,
                                               @RequestParam(value = "sortOrder", defaultValue = "", required = false) String sortOrder,
                                               @RequestParam(value = "currentPage", defaultValue = "0", required = false) int currentPage,
                                               @RequestParam(value = "itemPerPage", defaultValue = "0", required = false) int itemPerPage,
                                               HttpServletRequest httpServletRequest){
        if(helperExtension.isNullOrEmpty(id)){
            id = 0L;
        }
        DatabaseHelper databaseHelper = new DatabaseHelper(search,currentPage,itemPerPage,sortBy,sortOrder);
        apiResponse = userService.get(id,username,phoneNo,databaseHelper,httpServletRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @RequestMapping(path = "create",method = RequestMethod.POST)
    ResponseEntity<APIResponse<User>> createNewUser(@RequestBody @Valid UserRequest userRequest){
        apiResponse = userService.save(userRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @RequestMapping(path = {"id/{id}","username/{username}"},method = RequestMethod.PUT)
    ResponseEntity<APIResponse<User>> updateUser(@PathVariable(name = "id",required = false) Long id,
                                                 @PathVariable(name = "username",required = false) String username,
                                                 @RequestBody UserRequest userRequest){
        if(helperExtension.isNullOrEmpty(id)){
            id = 0L;
        }
        apiResponse = userService.update(id,username,userRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @RequestMapping(path = {"id/{id}","username/{username}"},method = RequestMethod.DELETE)
    ResponseEntity<APIResponse<User>> deleteUser(@PathVariable(name = "id",required = false) Long id,
                                                 @PathVariable(name = "username", required = false) String username){
        if(helperExtension.isNullOrEmpty(id)){
            id = 0L;
        }
        apiResponse = userService.delete(id,username);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


}
