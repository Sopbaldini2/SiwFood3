package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Post;

public interface PostRepository extends CrudRepository <Post, Long>{

}
