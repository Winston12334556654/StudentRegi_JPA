package base.daos;

import base.models.Course;
import base.models.Student;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
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
