package sba.sms.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import sba.sms.dao.CourseL;
import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

import java.util.List;

public class CourseService implements CourseL {

    private SessionFactory sessionFactory;

    public CourseService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public void createCourse(Course course) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(course);
            tx.commit();
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            return session.get(Course.class, courseId);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            return session.createQuery("from Course", Course.class).list();
        }
    }
}
