package base.daos;

import base.models.Course;
import base.models.Student;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDao {

    public int createStudent(Student student){
        int result;
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Set the courses associated with the student
            List<Course> courses = student.getCourses();
            if (courses != null) {
                for (Course course : courses) {
                    // This step is important to ensure JPA knows about the course
                    entityManager.merge(course);
                }
            }

            // Now, persist the student
            entityManager.persist(student);
            entityManager.getTransaction().commit();
            result = 1;
        } finally {
            assert entityManager != null;
            entityManager.close();
        }
        return result;
    }

}
