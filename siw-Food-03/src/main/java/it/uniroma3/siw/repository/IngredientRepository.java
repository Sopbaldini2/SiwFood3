package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ingredient;

public interface IngredientRepository extends CrudRepository <Ingredient, Long>{

	@Query("SELECT i FROM Ingredient i WHERE i.idIngredient NOT IN (SELECT ing.idIngredient FROM Recipe r JOIN r.ingredients ing WHERE r.id = :recipeId)")
	Iterable<Ingredient> findIngredientsNotInRecipe(Long recipeId);

}
