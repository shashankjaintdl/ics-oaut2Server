package com.ics.oauth2server.roles;

import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.roles.repository.PermissionJPARepository;
import com.ics.oauth2server.roles.repository.RoleJPARepository;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.roles.repository.RoleRepository;
import com.ics.oauth2server.roles.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(
        path = "${api.version}/role/",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
// Add Pre-Auth
public class RoleController {

    HelperExtension helperExtension = new HelperExtension();


    @Autowired
    private RoleRepository repository;
    @Autowired
    private PermissionJPARepository permissionJPARepository;
    @Autowired
    private RoleJPARepository roleJPARepository;
    private APIResponse<Roles> apiResponse;
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Admin or Super Admin can have authority to create new roles;

    @RequestMapping(path = "create", method = RequestMethod.POST)
    public ResponseEntity<APIResponse<Roles>> createRole(@RequestBody RoleRequest roleRequest,
                                                         HttpServletRequest httpServletRequest){
        apiResponse = roleService.save(roleRequest,httpServletRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


    @RequestMapping(path = {"id/{id}","name/{name}"}, method = RequestMethod.PUT)
    public ResponseEntity<APIResponse<Roles>> updateRole(@PathVariable(name = "id",required = false) Long id,
                                                         @PathVariable(name = "name", required = false) String name,
                                                         @RequestBody RoleRequest roleRequest,
                                                         HttpServletRequest httpServletRequest){
        if(helperExtension.isNullOrEmpty(id)){
            id = 0L;
        }
        apiResponse = roleService.update(id,name,roleRequest,httpServletRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


    @RequestMapping(path = {"assign-permission/id/{id}"},method = RequestMethod.PUT)
    public ResponseEntity<APIResponse<Roles>> updatePermissions(@PathVariable(name = "id") Long id,
                                                                @RequestBody  RoleRequest request,
                                                         HttpServletRequest httpServletRequest){
        apiResponse = roleService.assignPermissions(id,null,request,httpServletRequest);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }





}
