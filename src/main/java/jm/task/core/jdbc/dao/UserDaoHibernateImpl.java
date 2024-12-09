package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT," +
                " name VARCHAR(40) NOT NULL, lastName VARCHAR(100) NOT NULL, age TINYINT NOT NULL)").executeUpdate();
        session.getTransaction().commit();

    }

    @Override
    public void dropUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        String sql = "Truncate table Users";
        session.createSQLQuery(sql).executeUpdate();
        tx1.commit();
        session.close();

    }
}
