package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;

	public void saveReview(Review review) {
		reviewRepository.save(review);
	}

	public Review findById(Long id) {
		return reviewRepository.findById(id).get();
	}
	
	 public void delete(Review review) {
	        reviewRepository.delete(review);
	 }

	public boolean existsById(Long id) {
		return reviewRepository.existsById(id);
	}

	public Iterable<Review> findAll() {
		return reviewRepository.findAll();
	}

	public boolean existsByCookeAndRecipe(User cooke, Recipe recipe) {
        return reviewRepository.existsByCookeAndRecipe(cooke, recipe);
    }

	public List<Review> findByCooke(User cooke) {
        return reviewRepository.findByCooke(cooke);
    }

	public List<Review> findByRecipeId(Long id) {
		return reviewRepository.findByRecipeId(id);
	}

	public void deleteById(Long id) {
		reviewRepository.deleteById(id);
	}

}
