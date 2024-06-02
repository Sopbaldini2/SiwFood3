package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import it.uniroma3.siw.controller.validator.RecipeValidator;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private RecipeValidator recipeValidator;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/usAd/indexRecipe")
	public String indexRecipe() {
		return "usAd/indexRecipe.html";
	}
	
	@GetMapping("/admin/manageRecipes")
	public String manageRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "admin/manageRecipes.html";
	}
	
	@PostMapping("usAd/recipe")
	public String newRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult, Model model) {
		
		this.recipeValidator.validate(recipe, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.recipeService.save(recipe); 
			model.addAttribute("recipe", recipe);
			return "recipe.html";
		} else {
			return "formNewRecipe.html"; 
		}
	}

	@GetMapping("/recipe/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", this.recipeService.findById(id));
		return "recipe.html";
	}

	@GetMapping("/recipe")
	public String getRecipes(Model model) {		
		model.addAttribute("recipes", this.recipeService.findAll());
		return "recipes.html";
	}
	
	@GetMapping("/usAd/formNewRecipe")
	public String formNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		model.addAttribute("user", userService.getCurrentUser()); 
	    model.addAttribute("users", userService.getAllUsers());
		return "usAd/formNewRecipe.html";
	}

	@GetMapping("/usAd/formUpdateRecipe/{id}")
	public String formUpdateRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "usAd/formUpdateRecipe.html";
	}
	
	 // Metodo per cancellare una ricetta
    @DeleteMapping("/admin/deleteRecipe/{recipeId}")
    public String deleteRecipe(@PathVariable("recipeId") Long recipeId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        User currentUser = userService.findByEmail(currentUserEmail);
        Recipe recipe = recipeService.findById(recipeId);
        
        if (recipe != null && currentUser != null) {
            if (userService.isUserAdmin(currentUser) || currentUser.getId().equals(recipe.getCooke().getId())) {
                recipeService.deleteRecipe(recipeId);
                model.addAttribute("successMessage", "Ricetta cancellata con successo.");
            } else {
                model.addAttribute("errorMessage", "Non hai il permesso per cancellare questa ricetta.");
            }
        } else {
            model.addAttribute("errorMessage", "Ricetta non trovata.");
        }
        
        return "redirect:/admin/manageRecipes";
    }
	
	/*@GetMapping("/formSearchRecipes")
	public String formSearchRecipes() {
		return "formSearchRecipes.html";
	}*/

	@PostMapping("/searchRecipes")
	public String searchRecipes(Model model, @RequestParam String name) {
		model.addAttribute("recipes", this.recipeService.findByName(name));
		return "foundRecipes.html";
	}
	
	@GetMapping("/admin/updateIngredients/{id}")
	public String updateIngredients(@PathVariable("id") Long id, Model model) {

		List<Ingredient> ingredientsToAdd = this.ingredientsToAdd(id);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);
		model.addAttribute("recipe", this.recipeService.findById(id));

		return "admin/ingredientsToAdd.html";
	}

	@GetMapping("/admin/addIngredientToRecipe/{ingredientId}/{recipeId}")
	public String addIngredientToRecipe(@PathVariable("ingredientId") Long ingredientId, @PathVariable("recipeId") Long recipeId, Model model) {
		Recipe recipe = this.recipeService.findById(recipeId);
		Ingredient ingredient = this.ingredientService.findById(ingredientId);
		Set<Ingredient> ingredients = recipe.getIngredients();
		ingredients.add(ingredient);
		this.recipeService.save(recipe);
		
		List<Ingredient> ingredientsToAdd = ingredientsToAdd(recipeId);
		
		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);

		return "admin/ingredientsToAdd.html";
	}
	
	@GetMapping("/admin/removeIngredientFromRecipe/{ingredientId}/{recipeId}")
	public String removeActorFromMovie(@PathVariable("ingredientId") Long ingredientId, @PathVariable("recipeId") Long recipeId, Model model) {
		Recipe recipe = this.recipeService.findById(recipeId);
		Ingredient ingredient = this.ingredientService.findById(ingredientId);
		Set<Ingredient> ingredients = recipe.getIngredients();
		ingredients.remove(ingredient);
		this.recipeService.save(recipe);

		List<Ingredient> ingredientsToAdd = ingredientsToAdd(recipeId);
		
		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientToAdd", ingredientsToAdd);

		return "admin/ingredientsToAdd.html";
	}

	private List<Ingredient> ingredientsToAdd(Long recipeId) {
		List<Ingredient> ingredientsToAdd = new ArrayList<>();

		for (Ingredient i : ingredientService.findIngredientsNotInRecipe(recipeId)) {
			ingredientsToAdd.add(i);
		}
		return ingredientsToAdd;
	}
	
}
