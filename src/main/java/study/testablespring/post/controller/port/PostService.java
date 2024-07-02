package study.testablespring.post.controller.port;

import study.testablespring.post.domain.Post;
import study.testablespring.post.domain.PostCreate;
import study.testablespring.post.domain.PostUpdate;

public interface PostService {

    Post getById(long id);

    Post create(PostCreate postCreate);

    Post update(long id, PostUpdate postUpdate);
}
