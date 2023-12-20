package base.daos;

import base.models.User;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {

    //login user validating with typed email and password
    public boolean login(User user) {
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

            // Use a JPQL query to find a user by email and password
            Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password");
            query.setParameter("email", user.getEmail());
            query.setParameter("password", user.getPassword());

            // Execute the query and get the result
            User userResult = (User) query.getSingleResult();

            // If the query execution reaches this point, authentication is successful
            return true;

        } catch (NoResultException e) {
            // Authentication failed, return false
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }


    //user create method
    public int createUser(User user){
        int i;
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            i = 1 ;
        } finally {
            assert entityManager != null;
            entityManager.close();
        }
        return i ;
    }

    //user view method
    public List<User> getAllUsers(){
        List<User> users;
        EntityManager entityManager = null ;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            users = entityManager.createQuery("SELECT u FROM User u" , User.class).getResultList();
            entityManager.getTransaction().commit();
        }finally {
            assert entityManager != null;
            entityManager.close();
        }
        return users;
    }

    //getUserById
    public User findUserById(String userId) {
        EntityManager entityManager = null;
        User user = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            user = entityManager.find(User.class, userId);
            if (user == null) {
                // Handle the case where the user is not found, e.g., throw an exception
                throw new EntityNotFoundException("User not found with ID: " + userId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return user;
    }

    //update user
    public int updateUser(User updatedUser) {
        EntityManager entityManager = null;
        int updateResult;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Find the existing user by ID
            User existingUser = entityManager.find(User.class, updatedUser.getId());

            if (existingUser != null) {
                // Update the existing user with the new values
                existingUser.setName(updatedUser.getName());
                existingUser.setEmail(updatedUser.getEmail());
                // ... other attributes

                // Changes will be persisted when the transaction is committed
                entityManager.getTransaction().commit();

                // Indicate that the update was successful
                updateResult = 1;
            } else {
                // Handle the case where the user with the specified ID is not found
                // You might want to throw an exception or handle this case differently
                entityManager.getTransaction().rollback();
                throw new EntityNotFoundException("User not found with ID: " + updatedUser.getId());
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return updateResult;
    }


    //user delete
    public int deleteUser(String userId) {
        EntityManager entityManager = null;
        int deleteResult;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Find the existing user by ID
            User existingUser = entityManager.find(User.class, userId);

            if (existingUser != null) {
                // Remove the existing user from the database
                entityManager.remove(existingUser);

                // Changes will be persisted when the transaction is committed
                entityManager.getTransaction().commit();

                // Indicate that the delete was successful
                deleteResult = 1;
            } else {
                // Handle the case where the user with the specified ID is not found
                // You might want to throw an exception or handle this case differently
                entityManager.getTransaction().rollback();
                throw new EntityNotFoundException("User not found with ID: " + userId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return deleteResult;
    }


    //search user by name
    public List<User> searchUsersByName(String name) {
        List<User> users = new ArrayList<>();
        EntityManager entityManager = null;
        try {
            if (name == null) {
                throw new IllegalArgumentException("Name parameter cannot be null");
            }
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            try {
                // Using JPA query to select users by name
                Query query = entityManager.createQuery("SELECT u FROM User u WHERE LOWER(u.name) LIKE :name");
                query.setParameter("name", "%" + name.toLowerCase() + "%");

                // Execute the query and get the result list
                List<User> resultList = query.getResultList();

                // Add the result entities to the list
                users.addAll(resultList);
            } finally {
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            System.err.println("Searching user by name failed: " + e.getMessage());
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return users;
    }

    //search user by id
    public List<User> searchUserById(String userId) {
        List<User> users = new ArrayList<>();
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            User user = entityManager.find(User.class, userId);

            if (user != null) {
                users.add(user);
            } else {
                // Handle the case where the user is not found, e.g., throw an exception
                throw new EntityNotFoundException("User not found with ID: " + userId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }

        return users;
    }


    public User getUserByEmail(String email) {
        User user = null;

        try (EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager()) {
            // Using JPA query to select a user by email
            Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
            query.setParameter("email", email);

            // Execute the query and get the single result
            user = (User) query.getSingleResult();
        } catch (Exception e) {
            System.out.println("Getting user by email failed: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }



    //get user Id
    public String getLatestUserId() {
        String latestUserId = "USR001";

        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            Query query = entityManager.createQuery("SELECT MAX(CAST(SUBSTRING(u.id, 4) AS UNSIGNED)) FROM User u");
            BigInteger maxUserNumber = (BigInteger) query.getSingleResult();

            if (maxUserNumber != null) {
                int newUserNumber = maxUserNumber.intValue() + 1;

                if (maxUserNumber.intValue() < 10) {
                    latestUserId = "USR00" + newUserNumber;
                } else if (maxUserNumber.intValue() < 100) {
                    latestUserId = "USR0" + newUserNumber;
                } else {
                    latestUserId = "USR" + newUserNumber;
                }
            }

        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
            return latestUserId;
        }

    // Get role by email
    public String getRoleByEmail(String email) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            Query query = entityManager.createQuery("SELECT u.role FROM User u WHERE u.email = :email");
            query.setParameter("email", email);

            Object role = query.getSingleResult();

            if (role != null) {
                return role.toString();
            } else {
                throw new EntityNotFoundException("Role not found for email: " + email);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    // Get name by email
    public String getNameByEmail(String email) {
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            Query query = entityManager.createQuery("SELECT u.name FROM User u WHERE u.email = :email");
            query.setParameter("email", email);

            Object name = query.getSingleResult();

            if (name != null) {
                return name.toString();
            } else {
                throw new EntityNotFoundException("Name not found for email: " + email);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
    public String getUserIdByEmail(String email) {
        String userId = null;
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Using JPA query to select user ID by email
            Query query = entityManager.createQuery("SELECT u.id FROM User u WHERE u.email = :email");
            query.setParameter("email", email);

            // Execute the query and get the result
            List<String> userIdList = query.getResultList();

            if (!userIdList.isEmpty()) {
                userId = userIdList.get(0);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Getting user ID by email failed: " + e.getMessage());
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return userId;
    }



}
