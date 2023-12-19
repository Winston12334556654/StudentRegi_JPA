package base.daos;

import base.models.Role;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleDao {

    //getRoleByName
    public Role findRoleByName(String name) {
        EntityManager entityManager = null;
        Role role = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

            // Using JPA query to find the role by name
            Query query = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name");
            query.setParameter("name", name);

            // Execute the query and get the result (single result expected)
            role = (Role) query.getSingleResult();

            if (role == null) {
                throw new EntityNotFoundException("Role not found with name: " + name);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return role;
    }

}
