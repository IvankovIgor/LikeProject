package services;

import org.hibernate.Session;

public interface DatabaseService {
    Session getSession();
}
