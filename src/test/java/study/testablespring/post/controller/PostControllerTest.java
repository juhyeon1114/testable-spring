package study.testablespring.post.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import study.testablespring.common.domain.exception.ResourceNotFoundException;
import study.testablespring.mock.TestContainer;
import study.testablespring.post.controller.response.PostResponse;
import study.testablespring.post.domain.Post;
import study.testablespring.post.domain.PostUpdate;
import study.testablespring.user.domain.User;
import study.testablespring.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class PostControllerTest {

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
            .build();
        User user = User.builder()
            .id(1L)
            .email("kok202@naver.com")
            .nickname("kok202")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .lastLoginAt(100L)
            .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
            .id(1L)
            .content("helloworld")
            .writer(user)
            .createdAt(100L)
            .build());

        // when
        ResponseEntity<PostResponse> result = testContainer.postController.getById(1L);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("helloworld");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("kok202");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
            .build();

        // when
        // then
        assertThatThrownBy(() -> {
            testContainer.postController.getById(1);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() {
        // given
        TestContainer testContainer = TestContainer.builder()
            .clockHolder(() -> 200L)
            .build();
        User user = User.builder()
            .id(1L)
            .email("kok202@naver.com")
            .nickname("kok202")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .lastLoginAt(100L)
            .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
            .id(1L)
            .content("helloworld")
            .writer(user)
            .createdAt(100L)
            .build());

        // when
        ResponseEntity<PostResponse> result = testContainer.postController.update(1L, PostUpdate.builder()
            .content("foobar")
            .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("foobar");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("kok202");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
        assertThat(result.getBody().getModifiedAt()).isEqualTo(200L);
    }
}
