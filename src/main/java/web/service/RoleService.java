package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface RoleService {
    void add(Role roleName);

    void upDateRole(Role roleName);

    void delete(Long id);

    Role getRoleByName(String roleName);

    List<Role> getAllRoles();
}
