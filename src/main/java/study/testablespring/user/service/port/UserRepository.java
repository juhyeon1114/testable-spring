package study.testablespring.user.service.port;

import study.testablespring.user.domain.User;
import study.testablespring.user.domain.UserStatus;
import java.util.Optional;

public interface UserRepository {

    User getById(long id);

    Optional<User> findById(long id);

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    User save(User user);
}
