package it.uniroma3.siw.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;

@Controller
public class ReviewController {
	
	@Autowired
    private ReviewService reviewService;
	
	@Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;
	
	@GetMapping("/usAd/indexReview")
	public String indexReview() {
		return "/usAd/indexReview.html";
	}

	@GetMapping("/admin/manageReview")
	public String manageReview(Model model) {
		model.addAttribute("reviews", this.reviewService.findAll());
		return "/admin/manageReview.html";
	}
	
	@GetMapping("/admin/review/{id}")
    public String deleteReview(@PathVariable("id") Long id, Model model) {
        Review review = reviewService.findById(id);
        if (review != null) {
        	reviewService.delete(review);
            /* Redirect alla pagina dell'indice delle recensioni dopo la cancellazione*/
            return "redirect:/recipe";
        } else {
            // Nel caso in cui la recensione non esista
            model.addAttribute("messaggioErrore", "Recensione non trovato");
            return "/usAd/indexReview.html";
            }
        }
	
	@GetMapping("/usAd/formNewReview")
	public String formNewReview(@RequestParam("id") Long recipeId, Model model, Principal principal) {
		if (principal != null) {
	        String cookeName = principal.getName(); 
	        User cooke = userService.findUserByUsername(cookeName); 
	        if (cooke != null) {
	            model.addAttribute("cookeName", cooke.getName()); 
	            model.addAttribute("cookeSurname", cooke.getSurname()); 
	        }
	    }
	    model.addAttribute("review", new Review());
	    model.addAttribute("recipeId", recipeId);
	    return "/usAd/formNewReview";
	}
	
	
	@PostMapping("/usAd/review")
	public String newRecensione(@ModelAttribute("review") Review review,
	                            @RequestParam("recipeId") Long recipeId,
	                            Principal principal, Model model) {
		
		String cookeName = principal.getName();
		User cooke = userService.findUserByUsername(cookeName);
		
		if (cooke == null) {
	        model.addAttribute("messaggioErrore", "Cliente non trovato");
	        return "/usAd/formNewRecensione";
	    }
	    
		review.setUser(cooke);
	    
		Recipe recipe = recipeService.findById(recipeId);
	    if (recipe != null) {
	    	review.setRecipe(recipe);
	        
	        if (!reviewService.existsByCookeAndRecipe(review.getUser(), recipe)) {
	        	reviewService.saveReview(review);
	            model.addAttribute("review", review);
	            return "redirect:/recipe/" + recipeId;
	        } else {
	            model.addAttribute("messaggioErrore", "Questa recensione esiste già");
	            model.addAttribute("review", reviewService.findAll());
	            return "/usAd/formNewReview"; 
	        }
	    } else {
	        return "redirect:/recipe";
	    }
	}
}

