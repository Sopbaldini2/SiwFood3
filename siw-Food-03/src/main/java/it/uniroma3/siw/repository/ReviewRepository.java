package it.uniroma3.siw.repository;

import java.util.List;


import org.springframework.data.repository.CrudRepository;


import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;

public interface ReviewRepository extends CrudRepository <Review, Long>{
	
	public boolean existsById(Long id);

	boolean existsByCookeAndRecipe(User cooke, Recipe recipe);

	List<Review> findByCooke(User cooke);

	List<Review> findByRecipeId(Long id);

}
