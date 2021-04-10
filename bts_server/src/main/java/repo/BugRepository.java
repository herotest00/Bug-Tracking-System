package repo;

import constants.BugStatus;
import domain.Bug;

public interface BugRepository extends Repository<Long, Bug> {

    Iterable<Bug> findAllBugsForTester(long id);

    Iterable<Bug> findAllBugsForProgrammer(long id);

    Iterable<Bug> filterBugsByStatusForTester(long id, BugStatus status);

    Iterable<Bug> filterBugsByStatusForProgrammer(long id, BugStatus status);
}
