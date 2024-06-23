package it.uniroma3.siw.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
//import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
//import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

	@Autowired
    private UserService userService;
	
	@Autowired
    private CredentialsService credentialsService;
	
	@GetMapping("/admin/indexCooke")
	public String indexCooke() {
		return "admin/indexCooke.html";
	}
	
	/*@GetMapping("/admin/manageCookes")
	public String manageCookes(Model model) {
		model.addAttribute("cookes", this.userService.findAll());
		return "admin/manageCookes.html";
	}*/
	
	@GetMapping("/admin/manageCookes")
	public String manageCookes(Model model, Principal principal) {
	    // Get the currently logged-in user's username (assuming it's the email from Credentials)
	    String loggedInUsername = principal.getName();

	    // Find credentials by username
	    Optional<Credentials> optionalCredentials = credentialsService.findByUsername(loggedInUsername);

	    if (optionalCredentials.isPresent()) {
	        // Get the associated user object from credentials
	        User loggedInUser = optionalCredentials.get().getUser();

	        // Get all users (cookes)
	        List<User> cooks = (List<User>) userService.findAll();

	        // Filter out the logged-in user from the list of cooks
	        cooks = cooks.stream()
	                .filter(cook -> !cook.getId().equals(loggedInUser.getId()))
	                .collect(Collectors.toList());

	        model.addAttribute("cookes", cooks);
	        return "admin/manageCookes.html";
	    } else {
	        // Handle case where credentials are not found (should not happen if properly authenticated)
	        model.addAttribute("error", "Credentials not found");
	        return "errorPage"; // Provide appropriate error handling
	    }
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

