package ua.goit.dao.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceProvider {

    private static final EntityManagerFactory FACTORY;
    static {
        try {
            FACTORY = Persistence.createEntityManagerFactory("hw7");
        } catch (Throwable t) {
            t.printStackTrace();
            throw t;
        }
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

}
