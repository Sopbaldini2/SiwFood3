package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PathVariable;

//import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
//import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class UserController {

	@Autowired
    private UserService userService;

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
}

