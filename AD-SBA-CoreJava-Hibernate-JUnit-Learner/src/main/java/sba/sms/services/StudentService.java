package sba.sms.services;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import sba.sms.dao.StudentL;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

import java.util.List;
import java.util.Set;

public class StudentService implements StudentL {

    private SessionFactory sessionFactory;

    public StudentService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public List<Student> getAllStudents() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            return session.createQuery("from Student", Student.class).list();

        }

    }

    @Override
    public void createStudent(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            return session.get(Student.class, email);
        }
    }

    @Override
    public boolean validateStudent(String email, String password) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            String hql = "Select count(*) from Student where email = :email and password = :password";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            Long numStudents = query.getSingleResult();
            return numStudents > 0;
        }
    }

    @Override
    public void registerStudentToCourse(String email, int courseId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Student student = getStudentByEmail(email);
            Set<Course> course = student.getCourses();
            if (!course.contains(courseId)) {
                Course c = session.get(Course.class, courseId);
                getStudentCourses(email).add(c);

            }
        }
    }

    @Override
    public List<Course> getStudentCourses(String email) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Student student = session.get(Student.class, email);
            if (student != null) {
                String hql = "Select s.courses from Student s where s.email = :email";
                Query query = session.createQuery(hql, Set.class);
                query.setParameter("email", email);
                return query.getResultList();
            }
            return null;
        }
    }
}
