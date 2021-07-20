package com.ics.oauth2server.role;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.HelperExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        path = "${api.version}/role/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
// Add Pre-Auth
public class RoleController {

    HelperExtension helperExtension = new HelperExtension();

    @RequestMapping(path = "create", method = RequestMethod.POST)
    public ResponseEntity<APIResponse<Roles>> createRole(){
        return null;
    }


    @RequestMapping(path = {"id/{id}"},method = RequestMethod.GET)
    ResponseEntity<APIResponse<Roles>> getRoles(@PathVariable(name = "userId", required = false) Long userId){
        if(helperExtension.isNullOrEmpty(userId)){
            userId = 0L;
        }

        return null;
    }



}
