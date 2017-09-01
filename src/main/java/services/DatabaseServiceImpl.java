package services;

import db.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseServiceImpl implements DatabaseService {
    private SessionFactory sessionFactory;

    DatabaseServiceImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(Player.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/likedb?useLegacyDatetimeCode=false&serverTimezone=UTC");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "root");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();

        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}