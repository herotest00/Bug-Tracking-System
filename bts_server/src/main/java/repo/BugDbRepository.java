package repo;

import constants.BugStatus;
import domain.Bug;
import domain.validator.BugValidator;
import repo.utils.DBUtils;

import java.util.Properties;

public class BugDbRepository implements BugRepository {

    private final DBUtils dbUtils;
    private final BugValidator validator;

    public BugDbRepository(Properties properties, BugValidator validator) {
        this.dbUtils = new DBUtils(properties);
        this.validator = validator;
    }

    @Override
    public void add(Bug entity) {

    }

    @Override
    public void update(Bug entity) {

    }

    @Override
    public void delete(Bug entity) {

    }

    @Override
    public Iterable<Bug> findAll() {
        return null;
    }

    @Override
    public Bug findOne() {
        return null;
    }

    @Override
    public Iterable<Bug> findAllBugsForTester(long id) {
        return null;
    }

    @Override
    public Iterable<Bug> findAllBugsForProgrammer(long id) {
        return null;
    }

    @Override
    public Iterable<Bug> filterBugsByStatusForTester(long id, BugStatus status) {
        return null;
    }

    @Override
    public Iterable<Bug> filterBugsByStatusForProgrammer(long id, BugStatus status) {
        return null;
    }
}
