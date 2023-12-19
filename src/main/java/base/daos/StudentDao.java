package base.daos;

import base.models.Course;
import base.models.Student;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class StudentDao {

    public int createStudent(Student student) {
        int result;
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Merge the courses to ensure they are managed
            List<Course> courses = student.getCourses();
            if (courses != null) {
                for (Course course : courses) {
                    entityManager.merge(course);
                }
            }
            // Persist the student
            entityManager.persist(student);

            entityManager.getTransaction().commit();
            result = 1;
        } finally {
            assert entityManager != null;
            entityManager.close();
        }
        return result;
    }

    public List<Student> getAllStudent(){
        List<Student> students;
        EntityManager entityManager = null ;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            students = entityManager.createQuery("SELECT s FROM Student s" , Student.class).getResultList();
            entityManager.getTransaction().commit();
        }finally {
            assert entityManager != null;
            entityManager.close();
        }

        return students;
    }

    public Student findStudentById(String studId) {
        EntityManager entityManager = null;
        Student student = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            student = entityManager.find(Student.class, studId);
            if (student == null) {
                // Handle the case where the user is not found, e.g., throw an exception
                throw new EntityNotFoundException("Student not found with ID: " + studId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return student;
    }

    public int updateStudent(Student updatedStudent) {
        EntityManager entityManager = null;
        int updateResult;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Find the existing user by ID
            Student existingStudent = entityManager.find(Student.class, updatedStudent.getId());

            if (existingStudent != null) {
                // Update the existing user with the new values
                existingStudent.setName(updatedStudent.getName());
                existingStudent.setDob(updatedStudent.getDob());
                existingStudent.setEducation(updatedStudent.getEducation());
                existingStudent.setPhoto(updatedStudent.getPhoto());
                existingStudent.setGender(updatedStudent.getGender());
                existingStudent.setCourses(updatedStudent.getCourses());

                // Changes will be persisted when the transaction is committed
                entityManager.getTransaction().commit();

                // Indicate that the update was successful
                updateResult = 1;
            } else {
                // Handle the case where the user with the specified ID is not found
                // You might want to throw an exception or handle this case differently
                entityManager.getTransaction().rollback();
                throw new EntityNotFoundException("User not found with ID: " + updatedStudent.getId());
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return updateResult;
    }

    //student delete
    public int deleteStudent(String studId) {
        EntityManager entityManager = null;
        int deleteResult;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Find the existing user by ID
            Student existingStudent = entityManager.find(Student.class, studId);

            if (existingStudent != null) {
                // Remove the existing user from the database
                entityManager.remove(existingStudent);

                // Changes will be persisted when the transaction is committed
                entityManager.getTransaction().commit();

                // Indicate that the delete was successful
                deleteResult = 1;
            } else {
                // Handle the case where the user with the specified ID is not found
                // You might want to throw an exception or handle this case differently
                entityManager.getTransaction().rollback();
                throw new EntityNotFoundException("User not found with ID: " + studId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return deleteResult;
    }

    //search student by name
    public List<Student> searchStudentsByName(String name) {
        List<Student> students = new ArrayList<>();
        EntityManager entityManager = null;
        try {
            if (name == null) {
                throw new IllegalArgumentException("Name parameter cannot be null");
            }
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            try {
                // Using JPA query to select users by name
                Query query = entityManager.createQuery("SELECT s FROM Student s WHERE LOWER(s.name) LIKE :name");
                query.setParameter("name", "%" + name.toLowerCase() + "%");

                // Execute the query and get the result list
                List<Student> resultList = query.getResultList();

                // Add the result entities to the list
                students.addAll(resultList);
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
        return students;
    }

    //search student by id
    public List<Student> searchStudentById(String studId) {
        List<Student> students = new ArrayList<>();
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            Student student = entityManager.find(Student.class, studId);

            if (student != null) {
                students.add(student);
            } else {
                // Handle the case where the user is not found, e.g., throw an exception
                throw new EntityNotFoundException("User not found with ID: " + studId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return students;
    }

    /// search student by course name
    public List<Student> searchStudentsByCourse(String course) {
        List<Student> students = new ArrayList<>();
        EntityManager entityManager = null;

        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Using JPA query to select students by course
            TypedQuery<Student> query = entityManager.createQuery(
                    "SELECT s FROM Student s JOIN s.courses c WHERE LOWER(c.name) LIKE :courseName", Student.class);
            query.setParameter("courseName", "%" + course.toLowerCase() + "%");

            // Execute the query and get the result list
            List<Student> resultList = query.getResultList();

            // Add the result entities to the list
            students.addAll(resultList);

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Searching students by course failed: " + e.getMessage());
            e.printStackTrace();
            if (entityManager != null && entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return students;
    }






    //get student Id
    public String getLatestStudentId() {
        String latestStudentId = "STU001";

        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            Query query = entityManager.createQuery("SELECT MAX(CAST(SUBSTRING(s.id, 4) AS UNSIGNED)) FROM Student s");
            BigInteger maxStudentNumber = (BigInteger) query.getSingleResult();

            if (maxStudentNumber != null) {
                int newCourseNumber = maxStudentNumber.intValue() + 1;

                if (maxStudentNumber.intValue() < 10) {
                    latestStudentId = "STU00" + newCourseNumber;
                } else if (maxStudentNumber.intValue() < 100) {
                    latestStudentId = "STU0" + newCourseNumber;
                } else {
                    latestStudentId = "STU" + newCourseNumber;
                }
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return latestStudentId;
    }


}
