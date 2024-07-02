package study.testablespring.mock;

import study.testablespring.common.service.port.ClockHolder;
import study.testablespring.common.service.port.UuidHolder;
import study.testablespring.post.controller.PostController;
import study.testablespring.post.controller.PostCreateController;
import study.testablespring.post.controller.port.PostService;
import study.testablespring.post.service.PostServiceImpl;
import study.testablespring.post.service.port.PostRepository;
import study.testablespring.user.controller.MyInfoController;
import study.testablespring.user.controller.UserController;
import study.testablespring.user.controller.UserCreateController;
import study.testablespring.user.service.CertificationService;
import study.testablespring.user.service.UserServiceImpl;
import study.testablespring.user.service.port.MailSender;
import study.testablespring.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final PostService postService;
    public final CertificationService certificationService;
    public final UserController userController;
    public final MyInfoController myInfoController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();
        this.postService = PostServiceImpl.builder()
            .postRepository(this.postRepository)
            .userRepository(this.userRepository)
            .clockHolder(clockHolder)
            .build();
        this.certificationService = new CertificationService(this.mailSender);
        UserServiceImpl userService = UserServiceImpl.builder()
            .uuidHolder(uuidHolder)
            .clockHolder(clockHolder)
            .userRepository(this.userRepository)
            .certificationService(this.certificationService)
            .build();
        this.userController = UserController.builder()
            .userService(userService)
            .build();
        this.myInfoController = MyInfoController.builder()
            .userService(userService)
            .build();
        this.userCreateController = UserCreateController.builder()
            .userService(userService)
            .build();
        this.postController = PostController.builder()
            .postService(postService)
            .build();
        this.postCreateController = PostCreateController.builder()
            .postService(postService)
            .build();
    }
}
