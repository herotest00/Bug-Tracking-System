package repo;

import domain.User;

public interface UserRepository extends Repository<Long, User>{

    User login(User user);
}
