package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Recipe;
//import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
//import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

	@Autowired
    private UserService userService;
	
	
	@GetMapping("/admin/indexCooke")
	public String indexCooke() {
		return "admin/indexCooke.html";
	}
	
	@GetMapping("/admin/manageCookes")
	public String manageCookes(Model model) {
		model.addAttribute("cookes", this.userService.findAll());
		return "admin/manageCookes.html";
	}
	

	// Mostra un elenco di tutti i cuochi
	@GetMapping("/cooke")
    public String showAllCooks(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "cookes.html";
    }
	
	@GetMapping("/cookess/{id}")
	public String getProfile(@PathVariable("id") Long id, Model model) {
		model.addAttribute("cooke", this.userService.findById(id));
		return "yourProfile.html";
	}
	
	@GetMapping("/admin/deleteCommunity/{id}")
	public String deleteCommunity(@PathVariable("id") Long id, Model model) {
	    User user = userService.findById(id);
	    if (user != null) {
	        // Rimuovi il cuoco da tutte le ricette
	        for (Recipe recipe : user.getRecipes()) {
	            recipe.setCooke(null); // Rimuovi il riferimento dell'utente dalla ricetta
	        }
	        user.getRecipes().clear(); // Rimuovi le ricette dall'utente

	        // Ora puoi eliminare l'utente
	        userService.deleteById(id);

	        // Redirect alla pagina degli utenti dopo la cancellazione
	        return "redirect:/cooke";
	    } else {
	        // Nel caso in cui l'utente non esista
	        model.addAttribute("messaggioErrore", "User not found");
	        return "admin/indexCooke.html";
	    }
	}
	
}

