package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Post;
import it.uniroma3.siw.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

	public Post findById(Long id) {
		return postRepository.findById(id).get();
	}

	public void deletePostById(Long id) {
		postRepository.deleteById(id);
		
	}


}
