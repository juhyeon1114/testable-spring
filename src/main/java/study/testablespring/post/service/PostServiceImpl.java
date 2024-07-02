package study.testablespring.post.service;

import study.testablespring.common.domain.exception.ResourceNotFoundException;
import study.testablespring.common.service.port.ClockHolder;
import study.testablespring.post.controller.port.PostService;
import study.testablespring.post.domain.Post;
import study.testablespring.post.domain.PostCreate;
import study.testablespring.post.domain.PostUpdate;
import study.testablespring.post.service.port.PostRepository;
import study.testablespring.user.domain.User;
import study.testablespring.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreate postCreate) {
        User writer = userRepository.getById(postCreate.getWriterId());
        Post post = Post.from(writer, postCreate, clockHolder);
        return postRepository.save(post);
    }

    public Post update(long id, PostUpdate postUpdate) {
        Post post = getById(id);
        post = post.update(postUpdate, clockHolder);
        return postRepository.save(post);
    }
}