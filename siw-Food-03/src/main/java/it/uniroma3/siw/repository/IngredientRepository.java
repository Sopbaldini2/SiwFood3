package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ingredient;

public interface IngredientRepository extends CrudRepository <Ingredient, Long>{

	@Query("SELECT i FROM Ingredient i WHERE i.id NOT IN (SELECT ing.id FROM Recipe r JOIN r.ingredients ing WHERE r.id = :recipeId)")
	Iterable<Ingredient> findIngredientsNotInRecipe(Long recipeId);

	public boolean existsByName(String name);
	public List<Ingredient> findByName(String name);

}
