package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;

//import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
//import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

	@Autowired
    private UserService userService;
	
	/*@Autowired
	private CredentialsService credentialsService;*/
	
	@GetMapping("/user/indexUser")
	public String indexUser() {
		return "user/indexUser.html";
	}	

	// Mostra i dettagli di un singolo cuoco
	/*@GetMapping("/cooke/{id}")
	public String getUserDetails(@PathVariable Long id, Model model) {
	    // Ottieni l'utente (cuoco) dal servizio
	    User user = userService.findById(id);
	    
	    // Verifica se l'utente esiste
	    if (user == null) {
	        /* Se l'utente non esiste, reindirizza alla pagina di errore o gestisci l'errore in modo appropriato*/
	      /* return "redirect:/error";
	    }

	    // Ottieni le credenziali dell'utente
	    Credentials credentials = credentialsService.getCredentialsByUser(user);

	    // Verifica se le credenziali esistono
	    if (credentials == null) {
	        // Se le credenziali non esistono, reindirizza alla pagina di errore o gestisci l'errore in modo appropriato
	        return "redirect:/error";
	    }

	    // Rimuovi la password dall'oggetto Credentials
	    credentials.setPassword(null);

	    // Aggiungi l'utente e le credenziali al modello per visualizzare i dettagli nella vista
	    model.addAttribute("user", user);
	    model.addAttribute("credentials", credentials);
	    
	    // Restituisci il nome della vista per visualizzare i dettagli dell'utente
	    return "cooke.html";
	} */

	// Mostra un elenco di tutti i cuochi
	@GetMapping("/cooke")
    public String showAllCooks(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "cookes.html";
    }
}

