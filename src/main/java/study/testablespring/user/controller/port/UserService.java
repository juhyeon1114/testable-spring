package study.testablespring.user.controller.port;

import study.testablespring.user.domain.User;
import study.testablespring.user.domain.UserCreate;
import study.testablespring.user.domain.UserUpdate;

public interface UserService {

    User getByEmail(String email);

    User getById(long id);

    User create(UserCreate userCreate);

    User update(long id, UserUpdate userUpdate);

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}
