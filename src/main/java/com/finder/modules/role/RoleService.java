package com.finder.modules.role;

import com.finder.exceptions.ErrorHolder;
import com.finder.modules.generics.GenericEntity;
import com.finder.modules.generics.GenericService;
import com.finder.modules.user.UserService;
import com.finder.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends GenericService<Role> {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    public RoleService(RoleRepository roleRepository) {
        super(roleRepository, "Role");
    }

    @Override
    public List<Role> findAll() {
        if (CommonUtil.isAdminUser()) {
            return super.findAll();
        }
        return roleRepository.findByUser(userService.getCurrentUser());
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id, userService.getCurrentUser());
    }

    @Override
    public <T extends GenericEntity> void postValidate(T newEntity, ErrorHolder errorHolder) throws ErrorHolder {
        Role role = (Role) newEntity;
        if (isNewObject(role)) {
            if (roleRepository.findByName(role.getName()) != null)
                errorHolder.addError("rolename", role.getName() + " already exists!", true);
        }
    }
}
