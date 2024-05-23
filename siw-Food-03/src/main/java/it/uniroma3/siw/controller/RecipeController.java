package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.controller.validator.RecipeValidator;
import it.uniroma3.siw.model.Recipe;
//import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.service.RecipeService;
import jakarta.validation.Valid;

@Controller
public class RecipeController {
	
	@Autowired
	private RecipeService recipeService;
	
	/*@Autowired
	private IngredientService ingredientService;*/
	
	@Autowired
	private RecipeValidator recipeValidator;
	
	
	@GetMapping("/indexRecipe")
	public String indexRecipe() {
		return "indexRecipe.html";
	}
	
	@GetMapping("/manageRecipes")
	public String manageRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "manageRecipes.html";
	}
	
	@PostMapping("/recipe")
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
		return "recipess.html";
	}
	
	@GetMapping("/formNewRecipe")
	public String formNewRecipe(Model model) {
		model.addAttribute("recipe", new Recipe());
		return "formNewRecipe.html";
	}

	@GetMapping("/formUpdateRecipe/{id}")
	public String formUpdateRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "formUpdateRecipe.html";
	}
}
