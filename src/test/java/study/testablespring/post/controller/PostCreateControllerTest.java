package study.testablespring.post.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import study.testablespring.mock.TestContainer;
import study.testablespring.post.controller.response.PostResponse;
import study.testablespring.post.domain.PostCreate;
import study.testablespring.user.domain.User;
import study.testablespring.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
            .clockHolder(() -> 1679530673958L)
            .build();
        testContainer.userRepository.save(User.builder()
            .id(1L)
            .email("kok202@naver.com")
            .nickname("kok202")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .lastLoginAt(100L)
            .build());
        PostCreate postCreate = PostCreate.builder()
            .writerId(1)
            .content("helloworld")
            .build();

        // when
        ResponseEntity<PostResponse> result = testContainer.postCreateController.create(postCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("kok202");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1679530673958L);
    }
}
