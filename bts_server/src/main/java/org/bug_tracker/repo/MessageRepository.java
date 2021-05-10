package org.bug_tracker.repo;

import domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findMessagesByBug_IdOrderBySendDateAsc(long id);
}
