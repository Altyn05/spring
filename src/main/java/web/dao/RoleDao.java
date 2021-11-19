package web.dao;

import web.model.Role;

import java.util.List;

public interface RoleDao {
    void add(Role roleName);

    void delete(long id);

//    void getRoleById(long id);

    Role getRoleByName(String roleName);

    void upDateRole(Role roleName);

    List<Role> getAllRoles();

}
