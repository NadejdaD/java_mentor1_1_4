package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private SessionFactory sessionFactory;
    private Connection connection;

    public Util() {
    }

    public Connection getConnection(String url, String username, String password) throws SQLException {
        if(connection != null) {
            return connection;
        }
        connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/java_mentor?useSSL=false&serverTimezone=UTC");
                config.setProperty("hibernate.connection.username", "root");
                config.setProperty("hibernate.connection.password", "root");
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                config.setProperty("current_session_context_class", "thread");
                config.setProperty("show_sql", "true");
                config.setProperty("hibernate.enable_lazy_load_no_trans", "true");
                config.setProperty("logging.level.org.hibernate.SQL", "debug");

                config.addAnnotatedClass(User.class);

                sessionFactory = config.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
