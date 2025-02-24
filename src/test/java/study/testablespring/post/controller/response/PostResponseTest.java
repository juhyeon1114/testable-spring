package study.testablespring.post.controller.response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import study.testablespring.post.controller.response.PostResponse;
import study.testablespring.post.domain.Post;
import study.testablespring.user.domain.User;
import study.testablespring.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

public class PostResponseTest {

    @Test
    public void Post로_응답을_생성할_수_있다() {
        // given
        Post post = Post.builder()
            .content("helloworld")
            .writer(User.builder()
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build())
            .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("kok202");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
