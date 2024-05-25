package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;

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

	public boolean existsByName(String name) {
		return ingredientRepository.existsByName(name);
	}

	public List<Ingredient> findByName(String name) {
		return ingredientRepository.findByName(name);
	}

	public void deleteById(Long id) {
        try {
            ingredientRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("L'ingrediente con ID " + id + " non esiste", e);
        }
    }
}
