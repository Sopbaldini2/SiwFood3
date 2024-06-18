package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import it.uniroma3.siw.model.Ingredient;
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
	
	/*@GetMapping("/formSearchIngredients")
	public String formSearchIngredients() {
		return "formSearchIngredients.html";
	}*/

	@PostMapping("/searchIngredients")
	public String searchIngredients(Model model, @RequestParam String name) {
		model.addAttribute("ingredients", this.ingredientService.findByName(name));
		return "foundIngredients.html";
	}
	
	// Verifica se è corretto 
	@GetMapping("/admin/ingredient/{id}")
    public String deleteIngredient(@PathVariable("id") Long id, Model model) {
        // Controlla se l'utente è autenticato come admin (puoi utilizzare Spring Security per questo)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
        if (!isAdmin) {
            model.addAttribute("errorMessage", "Accesso negato");
            return "error";
        }

        // Cancella l'ingrediente
        try {
            ingredientService.deleteById(id);
            return "redirect:/usAd/indexIngredient"; // Redirect alla pagina degli ingredienti dopo la cancellazione
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Si è verificato un errore durante la cancellazione dell'ingrediente");
            return "error";
        }
    }
}