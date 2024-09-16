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
@ToString(exclude = "courses")
@Entity
@Table(name = "student")
public class Student {

    public Student(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    @Id
    @Column(name = "email", length = 50)
    private String email;  //Student’s unique identifier

    @Column(name = "name", length = 50, nullable = false)
    private String name; //Student’s name

    @Column(name = "password", length = 50, nullable = false)
    private String password;  //Student’s password

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id")

    )
    private Set<Course> courses = new HashSet<>();  //Student courses list

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return email == student.email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
