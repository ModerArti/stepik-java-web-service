package com.github.moderarti.database.service;

import com.github.moderarti.models.accounts.UserProfile;
import com.github.moderarti.database.repository.DatabaseRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import static com.github.moderarti.property.PropertiesENUM.*;
import static com.github.moderarti.property.PropertiesHandler.*;

public class DatabaseService {

    private final SessionFactory sessionFactory;

    public DatabaseService() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserProfile.class);

        configuration.setProperty("hibernate.dialect", getProperty(DB_DIALECT));
        configuration.setProperty("hibernate.connection.driver_class", getProperty(DB_DRIVER));
        configuration.setProperty("hibernate.connection.url", getProperty(DB_URL));
        configuration.setProperty("hibernate.connection.username", getProperty(DB_NAME));
        configuration.setProperty("hibernate.connection.password", getProperty(DB_PASSWORD));
        configuration.setProperty("hibernate.show_sql", getProperty(DB_SHOW_SQL));
        configuration.setProperty("hibernate.hbm2ddl.auto", getProperty(DB_HBM2DDL_AUTO));
        return configuration;
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public UserProfile getUser(String name) {
            Session session = sessionFactory.openSession();
            DatabaseRepository repo = new DatabaseRepository(session);
            UserProfile user = repo.getUserByName(name);
            session.close();
            return user;
    }

    public long addUser(UserProfile user)  {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            DatabaseRepository repo = new DatabaseRepository(session);
            long id = repo.saveUser(user);
            transaction.commit();
            session.close();
            return id;
    }

}
