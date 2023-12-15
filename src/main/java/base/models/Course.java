package base.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    private String id ;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    public Course() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}