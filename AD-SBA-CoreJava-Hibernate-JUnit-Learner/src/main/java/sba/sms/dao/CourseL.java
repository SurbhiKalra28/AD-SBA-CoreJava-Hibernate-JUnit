package sba.sms.dao;

import sba.sms.models.Course;

import java.util.List;

public interface CourseL {
    void createCourse(Course course);

    Course getCourseById(int courseId);

    List<Course> getAllCourses();

}
