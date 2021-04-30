package org.bug_tracker.repo;

import constants.BugStatus;
import domain.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BugRepository extends JpaRepository<Bug, Long> {

    List<Bug> findBugsByTester_Id(long id);

    List<Bug> findBugsByProgrammer_IdOrStatus(long id, BugStatus status);

    List<Bug> findBugsByStatusAndTester_Id(BugStatus status, long id);

    List<Bug> findBugsByStatusOrProgrammer_Id(BugStatus status, long id);
}
