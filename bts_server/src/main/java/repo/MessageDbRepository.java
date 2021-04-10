package repo;

import domain.Message;
import domain.validator.MessageValidator;
import repo.utils.DBUtils;

import java.util.Properties;

public class MessageDbRepository implements MessageRepository {

    private final DBUtils dbUtils;
    private final MessageValidator validator;

    public MessageDbRepository(Properties properties, MessageValidator validator) {
        this.dbUtils = new DBUtils(properties);
        this.validator = validator;
    }

    @Override
    public void add(Message entity) {

    }

    @Override
    public void update(Message entity) {

    }

    @Override
    public void delete(Message entity) {

    }

    @Override
    public Iterable<Message> findAll() {
        return null;
    }

    @Override
    public Message findOne() {
        return null;
    }
}
