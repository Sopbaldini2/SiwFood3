package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.repository.IngredientRepository;

@Service
public class IngredientService {

	@Autowired
	private IngredientRepository ingredientRepository;
	
	public Ingredient findById(Long ingredientId) {
		return ingredientRepository.findById(ingredientId).get();
	}

	public Iterable<Ingredient> findAll() {
		return ingredientRepository.findAll();
	}

	public void save(Ingredient ingredient) {
		ingredientRepository.save(ingredient);
	}

	public Iterable<Ingredient> findIngredientsNotInRecipe(@Param ("recipeId") Long recipeId) {
        return ingredientRepository.findIngredientsNotInRecipe(recipeId);
	}
}
