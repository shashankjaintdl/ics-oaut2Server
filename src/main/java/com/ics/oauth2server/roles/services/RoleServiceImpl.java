package com.ics.oauth2server.roles.services;

import com.ics.oauth2server.common.entities.Permission;
import com.ics.oauth2server.common.entities.Roles;
import com.ics.oauth2server.helper.APIResponse;
import com.ics.oauth2server.helper.HelperExtension;
import com.ics.oauth2server.roles.RoleMapper;
import com.ics.oauth2server.roles.RoleRequest;
import com.ics.oauth2server.roles.repository.PermissionJPARepository;
import com.ics.oauth2server.roles.repository.RoleJPARepository;
import com.ics.oauth2server.roles.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{


    HelperExtension helperExtension = new HelperExtension();
    RoleMapper mapper = new RoleMapper();
    private List<Roles> roles;
    private APIResponse apiResponse;

    private final RoleRepository roleRepository;
    private final RoleJPARepository roleJPARepository;
    private final PermissionJPARepository permissionJPARepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleJPARepository roleJPARepository,PermissionJPARepository permissionJPARepository) {
        this.roleRepository = roleRepository;
        this.roleJPARepository = roleJPARepository;
        this.permissionJPARepository = permissionJPARepository;
    }


    @Override
    public APIResponse<Roles> save(RoleRequest roleRequest, HttpServletRequest httpServletRequest) {
        Roles entity = mapper.map(roleRequest);
        roles = roleRepository.get(entity.getId(), entity.getName());
        if(roles.isEmpty()){
            // add new Roles
            try {
                roleRepository.save(entity);
                roles.add(entity);
                return new APIResponse<>(HttpStatus.CREATED.value(),HttpStatus.CREATED.toString(), "New Role Creates",roles,httpServletRequest);
            }
            catch (Exception e){
                // throw type of exception
            }
        }
        return new APIResponse<>(HttpStatus.CONFLICT.value(),HttpStatus.CONTINUE.toString(), "Role Already Exist",roles,httpServletRequest);
    }

    @Override
    public APIResponse<Roles> update(Long id, String name, RoleRequest request,HttpServletRequest httpServletRequest) {

        roles = roleRepository.get(id,name);
        if(!roles.isEmpty()){
            // Update Roles
            if(!helperExtension.isNullOrEmpty(request.getName())){
                roles.get(0).setName(request.getName());
            }
            roleJPARepository.save(roles.get(0));
            return new APIResponse<>(HttpStatus.CREATED.value(),HttpStatus.CREATED.toString(),"ROle has been updated",roles,httpServletRequest);
        }
        // Roles does not exist to update
        return new APIResponse<>(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.toString(),"Roles not found",roles,httpServletRequest);
    }

    // Have to work more on same below functions
    // Assigning the permission to the roles
    @Override
    public APIResponse<Roles> assignPermissions(Long id, String name, RoleRequest request, HttpServletRequest httpServletRequest) {
        List<Roles> roles = roleRepository.get(id,request.getName());
        if(!roles.isEmpty()){
            List<Permission> permissions = permissionJPARepository.findAllById(request.getPermissions());
            if(permissions.size() == request.getPermissions().size()) {
                roles.get(0).setPermissions(permissions);
                roleRepository.saveOrUpdate(roles.get(0));
                return new APIResponse<>(HttpStatus.OK.value(),HttpStatus.OK.toString(),"Permission has been assigned to the role = "+roles.get(0).getName(),roles,httpServletRequest);
            }
        }
        return new APIResponse<>(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.toString(),"Roles does not exist to assign this permission",roles,httpServletRequest);
    }

}
