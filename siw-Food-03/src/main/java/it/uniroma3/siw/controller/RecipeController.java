package it.uniroma3.siw.controller;

//import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import it.uniroma3.siw.controller.validator.RecipeValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
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
	
	@Autowired
    private CredentialsService credentialsService;
	
	
	@GetMapping("/usAd/indexRecipe")
	public String indexRecipe() {
		return "usAd/indexRecipe.html";
	}
	
	@GetMapping("/usAd/manageRecipes")
	public String manageRecipes(Model model) {
		model.addAttribute("recipes", this.recipeService.findAll());
		return "usAd/manageRecipes.html";
	}
	
	@GetMapping("/usAd/manageRecipesUser")
	public String manageRecipesUser(Model model) {
	    // Get authenticated user's username
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();

	    // Find user by username
	    Optional<Credentials> optionalCredentials = credentialsService.findByUsername(username);
	    if (optionalCredentials.isPresent()) {
	        User currentUser = optionalCredentials.get().getUser();

	        // Find recipes where cooke.id equals current user's id
	        Set<Recipe> userRecipes = currentUser.getRecipes();

	        // Add filtered recipes to the model
	        model.addAttribute("recipes", userRecipes);

	        return "usAd/manageRecipes.html";
	    } else {
	        // Handle case where user is not found
	        model.addAttribute("error", "User not found");
	        return "errorPage"; // Return appropriate error page
	    }
	}

	
	@PostMapping("usAd/recipe")
	public String newRecipe(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult, 
	                        @RequestParam(value = "ingredientIds", required = false) List<Long> ingredientIds, Model model) {
	    
	    this.recipeValidator.validate(recipe, bindingResult);
	    
	    if (!bindingResult.hasErrors()) {
	      
	            Set<Ingredient> ingredients = new HashSet<>();
	            for (Long ingredientId : ingredientIds) {
	                Ingredient ingredient = ingredientService.findById(ingredientId);
	                if (ingredient != null) {
	                    ingredients.add(ingredient);
	                }
	            }
	            recipe.setIngredients(ingredients);
	   
	        
	        this.recipeService.save(recipe); 
	        model.addAttribute("recipe", recipe);
	        return "recipe.html";
	    } else {
	        return "/usAd/formNewRecipe.html"; 
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
	    model.addAttribute("ingredients", ingredientService.findAll());
		return "usAd/formNewRecipe.html";
	}

	@GetMapping("/usAd/formUpdateRecipe/{id}")
	public String formUpdateRecipe(@PathVariable("id") Long id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id));
		return "usAd/formUpdateRecipe.html";
	}
	
	@GetMapping("/usAd/updateDescriptionRecipe/{id}")
    public String showUpdateDescriptionForm(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return "/usAd/updateDescriptionRecipeForm"; 
    }

    // Metodo per gestire l'aggiornamento della descrizione
    @PostMapping("/usAd/updateDescriptionRecipe/{id}")
    public String updateDescription(@PathVariable("id") Long id, @RequestParam("description") String description) {
        Recipe recipe = recipeService.findById(id);
        recipe.setDescription(description);
        recipeService.save(recipe);
        return "redirect:/recipe/" + id; // Redirige alla pagina della ricetta aggiornata
    }
    
    // Metodo per gestire l'aggiornamento della preparazione
    @GetMapping("/usAd/updatePreparationRecipe/{id}")
    public String showUpdatePreparationForm(@PathVariable("id") Long id, Model model) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return "/usAd/updatePreparationForm.html"; 
    }

    @PostMapping("/usAd/updatePreparationRecipe/{id}")
    public String updatePreparationRecipe(@PathVariable("id") Long id, @RequestParam("preparation") String preparation) {
        Recipe recipe = recipeService.findById(id);
        recipe.setPreparation(preparation);
        recipeService.save(recipe);
        return "redirect:/recipe/" + id; // Reindirizzamento alla pagina dei dettagli della ricetta
    }
    
	
	 // Metodo per cancellare una ricetta
	/*@GetMapping("/usAd/deleteRecipe/{id}")
    public String deleteRecipe(@PathVariable("id") Long id, Model model) {
        // Ottieni l'utente autenticato
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<Credentials> optionalCredentials = credentialsService.findByUsername(username);
        if (optionalCredentials.isPresent()) {
            User user = optionalCredentials.get().getUser();

            Recipe recipe = recipeService.findById(id);
            if (recipe != null) {
                // Verifica se l'ID del cuoco corrisponde all'ID dell'utente autenticato
                if (recipe.getCooke().getId().equals(user.getId())) {
                    recipeService.deleteRecipe(recipe);
                    // Redirect alla pagina dell'indice delle ricette dopo la cancellazione
                    return "redirect:/recipe";
                } else {
                    // Messaggio di errore se l'utente non è autorizzato a cancellare la ricetta
                    model.addAttribute("messaggioErrore", "Non sei autorizzato a cancellare questa ricetta");
                    return "usAd/indexRecipe.html";
                }
            } else {
                // Nel caso in cui la ricetta non esista
                model.addAttribute("messaggioErrore", "Ricetta non trovata");
                return "usAd/indexRecipe.html";
            }
        } else {
            // Nel caso in cui le credenziali non siano trovate
            model.addAttribute("messaggioErrore", "Utente non trovato");
            return "usAd/indexRecipe.html";
        }
    }*/
    
    @GetMapping("/usAd/deleteRecipe/{id}")
    public String deleteRecipe(@PathVariable("id") Long id) {
        // Trova la ricetta per l'ID fornito
        Recipe recipe = recipeService.findById(id);
        
        if (recipe != null) {
            // Cancella la ricetta
            recipeService.deleteRecipe(recipe);
            // Redirect alla pagina dell'indice delle ricette dopo la cancellazione
            return "redirect:/recipe";
        } else {
            // Nel caso in cui la ricetta non esista
            return "redirect:/usAd/indexRecipe.html?messaggioErrore=Ricetta non trovata";
        }
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
	
	@GetMapping("/usAd/updateIngredients/{id}")
	public String updateIngredients(@PathVariable("id") Long id, Model model) {

		List<Ingredient> ingredientsToAdd = this.ingredientsToAdd(id);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);
		model.addAttribute("recipe", this.recipeService.findById(id));

		return "usAd/ingredientsToAdd.html";
	}

	@GetMapping("/usAd/addIngredientToRecipe/{ingredientId}/{recipeId}")
	public String addIngredientToRecipe(@PathVariable("ingredientId") Long ingredientId, @PathVariable("recipeId") Long recipeId, Model model) {
		Recipe recipe = this.recipeService.findById(recipeId);
		Ingredient ingredient = this.ingredientService.findById(ingredientId);
		Set<Ingredient> ingredients = recipe.getIngredients();
		ingredients.add(ingredient);
		this.recipeService.save(recipe);
		
		List<Ingredient> ingredientsToAdd = ingredientsToAdd(recipeId);
		
		model.addAttribute("recipe", recipe);
		model.addAttribute("ingredientsToAdd", ingredientsToAdd);

		return "usAd/ingredientsToAdd.html";
	}
	
	@GetMapping("/admin/removeIngredientFromRecipe/{ingredientId}/{recipeId}")
	public String removeIngredientFromRecipe(@PathVariable("ingredientId") Long ingredientId, 
	                                         @PathVariable("recipeId") Long recipeId, 
	                                         Model model) {
	    Recipe recipe = this.recipeService.findById(recipeId);
	    Ingredient ingredient = this.ingredientService.findById(ingredientId);
	    Set<Ingredient> ingredients = recipe.getIngredients();
	    ingredients.remove(ingredient);
	    this.recipeService.save(recipe);

	    // Aggiorna gli ingredienti disponibili per l'aggiunta
	    List<Ingredient> ingredientsToAdd = this.ingredientsToAdd(recipeId);

	    // Aggiorna il modello con gli attributi corretti
	    model.addAttribute("recipe", recipe);
	    model.addAttribute("ingredientsToAdd", ingredientsToAdd);

	    return "usAd/ingredientsToAdd.html";
	}

	private List<Ingredient> ingredientsToAdd(Long recipeId) {
		List<Ingredient> ingredientsToAdd = new ArrayList<>();

		for (Ingredient i : ingredientService.findIngredientsNotInRecipe(recipeId)) {
			ingredientsToAdd.add(i);
		}
		return ingredientsToAdd;
	}
	
}
