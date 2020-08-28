package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS users" +
                " (user_id BIGINT NOT NULL AUTO_INCREMENT," +
                " user_firstName VARCHAR(30)," +
                " user_lastName VARCHAR(30)," +
                " user_age TINYINT(125) UNSIGNED NULL," +
                " PRIMARY KEY (user_id));";

        try (Session session = new Util().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE_USERS).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        final String DROP_TABLE_USERS = "DROP TABLE IF EXISTS java_mentor.users";

        try (Session session = new Util().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            session.createSQLQuery(DROP_TABLE_USERS).executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        try (Session session = new Util().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.save(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = new Util().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> result = null;

        try (Session session = new Util().getSessionFactory().openSession()){
            Transaction transaction = session.beginTransaction();

            result = session.createQuery("From User").list();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
         return result;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = new Util().getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
