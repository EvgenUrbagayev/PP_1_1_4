package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/database_1.1.3";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Ruslan13072011";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("ERROR");
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

                System.out.println("Соединение установлено!");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Ошибка. Соединение не установлено.");
            }

        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {
        Properties properties = new Properties();
        properties.put(Environment.URL, DB_URL);
        properties.put(Environment.USER, DB_USERNAME);
        properties.put(Environment.PASS, DB_PASSWORD);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        properties.put(Environment.HBM2DDL_AUTO, "");

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        return configuration;
    }
}