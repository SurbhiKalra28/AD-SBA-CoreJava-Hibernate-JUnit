package sba.sms.services;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.CommandLine;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;


public class StudentServiceTest {

    private StudentService studentService;
    private SessionFactory sessionFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        studentService = new StudentService();
    }

    @AfterEach
    public void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testGetAllStudents() {
        List<Student> studentList = studentService.getAllStudents();
        assertNotNull("No students returned", studentList);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Surbhi28Kalra@gmail.com", "Tanu28Kalra@gmail.com"})
    public void testCreateStudent(String email) {
        Student student = new Student();
        student.setName("Surbhi Kalra");
        student.setEmail(email);
        student.setPassword("Papa!143");
        studentService.createStudent(student);
        assertNotNull(student.getEmail());
    }

    @Test
    public void testGetStudentByEmail() {
        Student student = studentService.getStudentByEmail("Surbhi28Kalra@gmail.com");
        assertNotNull(student);
    }

    @Test
    public void testValidateStudent() {

        Student student = studentService.getStudentByEmail("Surbhi28Kalra@gmail.com");
        assertNotNull(student);
        assertEquals(student.getPassword(), "Papa!143");

    }

    @Test
    public void testRegisterStudentToCourse() {
        studentService.registerStudentToCourse("Surbhi28Kalra@gmail.com", 101);
        Course c = new CourseService().getCourseById(101);
        assertTrue(studentService.getStudentCourses("Surbhi28Kalra@gmail.com").contains(c));
    }

    @Test
    public void testGetStudentCourses() {
        List<Course> courseList = studentService.getStudentCourses("Surbhi28Kalra@gmail.com");
        assertNotNull(courseList);
    }
}