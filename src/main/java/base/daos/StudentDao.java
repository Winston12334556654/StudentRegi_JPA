package base.daos;

import base.models.Course;
import base.models.Student;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
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

    //get student Id
    public String getLatestStudentId() {
        String latestStudentId = "USR001";

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
