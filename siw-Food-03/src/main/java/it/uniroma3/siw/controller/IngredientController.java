package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.service.IngredientService;

@Controller
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
	@GetMapping("/usAd/indexIngredient")
	public String indexIngredient() {
		return "usAd/indexIngredient.html";
	}
	
	@GetMapping("/admin/manageIngredients")
	public String manageIngredients(Model model) {
		model.addAttribute("ingredients", this.ingredientService.findAll());
		return "admin/manageIngredients.html";
	}
	
	@PostMapping("/usAd/ingredient")
	public String newIngredient(@ModelAttribute("ingredient") Ingredient ingredient, Model model) {
		if (!ingredientService.existsByName(ingredient.getName())) {
			this.ingredientService.save(ingredient); 
			model.addAttribute("ingredient", ingredient);
			return "ingredient.html";
		} else {
			model.addAttribute("messaggioErrore", "Questo ingrediente esiste già");
			return "usAd/formNewIngredient.html"; 
		}
	}

	@GetMapping("/ingredient/{id}")
	public String getIngredient(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingredient", this.ingredientService.findById(id));
		return "ingredient.html";
	}

	@GetMapping("/ingredient")
	public String getIngredients(Model model) {
		model.addAttribute("ingredients", this.ingredientService.findAll());
		return "ingredients.html";
	}
	
	@GetMapping("/usAd/formNewIngredient")
	public String formNewIngredient(Model model) {
		model.addAttribute("ingredient", new Ingredient());
		return "usAd/formNewIngredient.html";
	}
	
	@GetMapping("/admin/formUpdateIngredient/{id}")
	public String formUpdateIngredient(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingredient", ingredientService.findById(id));
		return "admin/formUpdateIngredient.html";
	}
	
	@GetMapping("/admin/formUpdateDescription/{id}")
    public String formUpdateDescription(@PathVariable("id") Long id, Model model) {
        Ingredient ingredient = ingredientService.findById(id);
        model.addAttribute("ingredient", ingredient);
        return "admin/updateDescriptionIngredient.html"; // Nome del file HTML senza estensione
    }

    @PostMapping("/admin/updateDescriptionIngredient/{id}")
    public String updateDescriptionIngredient(@PathVariable("id") Long id, 
                                              @ModelAttribute("ingredient") Ingredient updatedIngredient,
                                              Model model) {
        Ingredient ingredient = ingredientService.findById(id);
        if (ingredient != null) {
            ingredient.setDescription(updatedIngredient.getDescription());
            ingredientService.save(ingredient); // Salva l'ingrediente aggiornato nel database
            model.addAttribute("ingredient", ingredient);
            return "redirect:/admin/manageIngredients"; // Redirect alla pagina di gestione ingredienti
        } else {
            model.addAttribute("messaggioErrore", "Ingredient not found");
            return "admin/manageIngredients"; // Gestisci il caso in cui l'ingrediente non sia trovato
        }
    }
	
	/*@GetMapping("/formSearchIngredients")
	public String formSearchIngredients() {
		return "formSearchIngredients.html";
	}*/

	@PostMapping("/searchIngredients")
	public String searchIngredients(Model model, @RequestParam String name) {
		model.addAttribute("ingredients", this.ingredientService.findByName(name));
		return "foundIngredients.html";
	}
	
	@GetMapping("/admin/deleteIngredient/{id}")
	public String deleteIngredient(@PathVariable("id") Long id, Model model) {
	    Ingredient ingredient = ingredientService.findById(id);
	    if (ingredient != null) {
	        // Rimuovi l'ingrediente da tutte le ricette
	        for (Recipe recipe : ingredient.getRecipes()) {
	            recipe.getIngredients().remove(ingredient);
	        }
	        // Salva le modifiche alle ricette
	        ingredient.getRecipes().clear(); // Rimuovi il riferimento dall'ingrediente stesso
	        ingredientService.deleteById(id); // Cancella l'ingrediente

	        // Redirect alla pagina dell'indice degli ingredienti dopo la cancellazione
	        return "redirect:/ingredient";
	    } else {
	        // Nel caso in cui l'ingrediente non esista
	        model.addAttribute("messaggioErrore", "Ingredient not found");
	        return "usAd/indexIngredient.html";
	    }
	}

}