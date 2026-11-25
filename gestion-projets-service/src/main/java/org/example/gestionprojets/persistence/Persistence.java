package org.example.gestionprojets.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.example.gestionprojets.domain.DeveloppeurEntity;
import org.example.gestionprojets.domain.PlateformeEntity;
import org.example.gestionprojets.domain.ProjetEntity;
import org.example.gestionprojets.domain.TacheEntity;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.function.Function;

/** Demarrage Hibernate et helpers de transactions : aucun conteneur, aucun framework. */
public final class Persistence {

    private static EntityManagerFactory factory;
    private static StandardServiceRegistry registry;

    private Persistence() {
    }

    public static synchronized void start() {
        if (factory != null) {
            return;
        }
        registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver")
                .applySetting("hibernate.connection.url", jdbcUrl())
                .applySetting("hibernate.connection.username", env("DB_USER", "devplatform"))
                .applySetting("hibernate.connection.password", env("DB_PASSWORD", "devplatform"))
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .applySetting("hibernate.hbm2ddl.auto", "update")
                .applySetting("hibernate.show_sql", "false")
                .build();

        factory = new MetadataSources(registry)
                .addAnnotatedClass(PlateformeEntity.class)
                .addAnnotatedClass(ProjetEntity.class)
                .addAnnotatedClass(DeveloppeurEntity.class)
                .addAnnotatedClass(TacheEntity.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    public static EntityManagerFactory factory() {
        if (factory == null) {
            throw new IllegalStateException("La persistance n'est pas demarree");
        }
        return factory;
    }

    public static <T> T read(Function<EntityManager, T> work) {
        try (EntityManager em = factory().createEntityManager()) {
            return work.apply(em);
        }
    }

    public static <T> T write(Function<EntityManager, T> work) {
        try (EntityManager em = factory().createEntityManager()) {
            EntityTransaction transaction = em.getTransaction();
            try {
                transaction.begin();
                T result = work.apply(em);
                transaction.commit();
                return result;
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e;
            }
        }
    }

    public static synchronized void stop() {
        if (factory instanceof SessionFactory sessionFactory) {
            sessionFactory.close();
        }
        factory = null;
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
            registry = null;
        }
    }

    private static String jdbcUrl() {
        return "jdbc:postgresql://" + env("DB_HOST", "localhost")
                + ":" + env("DB_PORT", "5432")
                + "/" + env("DB_NAME", "devplatform");
    }

    private static String env(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
