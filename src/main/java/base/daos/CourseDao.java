package base.daos;

import base.models.Course;
import base.service.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Component
public class CourseDao {

    //create course
    public int createCourse(Course course){
        int result;
        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(course);
            entityManager.getTransaction().commit();
            result = 1 ;
        } finally {
            assert entityManager != null;
            entityManager.close();
        }
        return result ;
    }

    //get all courses
    public List<Course> getAllCourses(){
        List<Course> courses;
        EntityManager entityManager = null ;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            courses = entityManager.createQuery("SELECT c FROM Course c" , Course.class).getResultList();
            entityManager.getTransaction().commit();
        }finally {
            assert entityManager != null;
            entityManager.close();
        }
        return courses;
    }

    //get course by id
    public Course findCourseById(String id) {
        EntityManager entityManager = null;
        Course course;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            course = entityManager.find(Course.class, id);
            if (course == null) {
                // Handle the case where the user is not found, e.g., throw an exception
                throw new EntityNotFoundException("Course not found with ID: " + id);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return course;
    }

    //update course
    public int updateCourse(Course updatedCourse) {
        EntityManager entityManager = null;
        int updateResult;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            // Find the existing course by ID
            Course existingCourse = entityManager.find(Course.class, updatedCourse.getId());

            if (existingCourse != null) {
                // Update the existing course with the new values
                existingCourse.setName(updatedCourse.getName());

                // Changes will be persisted when the transaction is committed
                entityManager.getTransaction().commit();

                // Indicate that the update was successful
                updateResult = 1;
            } else {
                entityManager.getTransaction().rollback();
                throw new EntityNotFoundException("User not found with ID: " + updatedCourse.getId());
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return updateResult;
    }

    //delete course
    public int deleteCourse(String courseId) {
        EntityManager entityManager = null;
        int deleteResult;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();

            Course existingCourse = entityManager.find(Course.class, courseId);

            if (existingCourse != null) {
                entityManager.remove(existingCourse);

                entityManager.getTransaction().commit();

                // Indicate that the delete was successful
                deleteResult = 1;
            } else {
                // Handle the case where the course with the specified ID is not found
                // You might want to throw an exception or handle this case differently
                entityManager.getTransaction().rollback();
                throw new EntityNotFoundException("User not found with ID: " + courseId);
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return deleteResult;
    }



    //get course Id
    public String getLatestCourseId() {
        String latestCourseId = "COU001";

        EntityManager entityManager = null;
        try {
            entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
            Query query = entityManager.createQuery("SELECT MAX(CAST(SUBSTRING(c.id, 4) AS UNSIGNED)) FROM Course c");
            BigInteger maxCourseNumber = (BigInteger) query.getSingleResult();

            if (maxCourseNumber != null) {
                int newCourseNumber = maxCourseNumber.intValue() + 1;

                if (maxCourseNumber.intValue() < 10) {
                    latestCourseId = "COU00" + newCourseNumber;
                } else if (maxCourseNumber.intValue() < 100) {
                    latestCourseId = "COU0" + newCourseNumber;
                } else {
                    latestCourseId = "COU" + newCourseNumber;
                }
            }
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return latestCourseId;
    }
}
