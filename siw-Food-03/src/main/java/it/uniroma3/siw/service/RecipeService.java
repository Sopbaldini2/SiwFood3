package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;


@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	public Recipe findById(Long id) {
		return recipeRepository.findById(id).get();
	}

	public Iterable<Recipe> findAll() {
		return recipeRepository.findAll();
	}

	public void save( Recipe recipe) {
		recipeRepository.save(recipe);
	}

	public List<Recipe> findByName(String name) {
		return recipeRepository.findByName(name);
	}

	public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);
    }

	public void deleteRecipe(Recipe recipe) {
		recipeRepository.delete(recipe);
	}

	public List<Recipe> findByNameIgnoreCaseContaining(String name) {
		return recipeRepository.findByNameIgnoreCaseContaining(name);
	}

}
