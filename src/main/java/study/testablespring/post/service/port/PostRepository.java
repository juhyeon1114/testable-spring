package study.testablespring.post.service.port;

import study.testablespring.post.domain.Post;
import java.util.Optional;

public interface PostRepository {

    Optional<Post> findById(long id);

    Post save(Post post);
}
