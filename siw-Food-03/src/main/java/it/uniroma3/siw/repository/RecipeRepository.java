package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;

public interface RecipeRepository extends CrudRepository <Recipe, Long>{

	public boolean existsByNameAndCooke(String name, User cooke);
	public List<Recipe> findByName(String name);
	public List<Recipe> findByNameIgnoreCaseContaining(String name);
}
