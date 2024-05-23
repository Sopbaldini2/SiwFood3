package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;

public interface RecipeRepository extends CrudRepository <Recipe, Long>{

	boolean existsByNameAndCooke(String name, User cooke);

}
