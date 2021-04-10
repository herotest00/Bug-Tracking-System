package repo;

import domain.Entity;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

public interface Repository<ID, E extends Entity<ID>> {

    Logger logger = LogManager.getLogger();

    void add(E entity);

    void update(E entity);

    void delete(E entity);

    Iterable<E> findAll();

    E findOne();
}
