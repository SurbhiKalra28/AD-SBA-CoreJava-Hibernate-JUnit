package sba.sms.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = "students")
@Entity
@Table(name = "course")
public class Course {

    public Course(String name, String instructor) {
        this.name = name;
        this.instructor = instructor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;   //Course unique identifier

    @Column(length = 50, nullable = false)
    private String name;  //Course name

    @Column(length = 50, nullable = false)
    private String instructor;  //Instructor name

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "courses")
    private Set<Student> students = new HashSet<>();   //	Course learners list


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
