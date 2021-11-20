package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void add(Role roleName) {
        entityManager.persist(roleName);
    }

    @Override
    public void delete(long id) {
        entityManager.createQuery("DELETE FROM Role WHERE id= :id").setParameter("id", id).executeUpdate();
    }

    @Override
    public Role getRoleById(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String roleName) {
        return entityManager.createQuery("SELECT u FROM Role u WHERE u.roleName= :roleName", Role.class)
                .setParameter("roleName", roleName).getSingleResult();
    }

    @Override
    public void upDateRole(Role roleName) {
        entityManager.merge(roleName);
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    public boolean existsByName(String name) {
        try {
            return getRoleByName(name).getRoleName().equals(name);
        } catch (Exception e){
            return false;
        }
    }


}
