package study.testablespring.mock;

import study.testablespring.common.domain.exception.ResourceNotFoundException;
import study.testablespring.user.domain.User;
import study.testablespring.user.domain.UserStatus;
import study.testablespring.user.service.port.UserRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeUserRepository implements UserRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<User> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User getById(long id) {
        return findById(id).orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public Optional<User> findById(long id) {
        return data.stream().filter(item -> item.getId().equals(id)).findAny();
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return data.stream().filter(item -> item.getId().equals(id) && item.getStatus() == userStatus).findAny();
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return data.stream().filter(item -> item.getEmail().equals(email) && item.getStatus() == userStatus).findAny();
    }

    @Override
    public User save(User user) {
        if (user.getId() == null || user.getId() == 0) {
            User newUser = User.builder()
                .id(autoGeneratedId.incrementAndGet())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .certificationCode(user.getCertificationCode())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .build();
            data.add(newUser);
            return newUser;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), user.getId()));
            data.add(user);
            return user;
        }
    }
}
