package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class ProfileController {

	
	@Autowired
    private CredentialsService credentialsService;
		
	@GetMapping("/user/profile")
    public String getProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        Credentials credentials = credentialsService.getCredentials(username);

        model.addAttribute("name", credentials.getUser().getName());
        model.addAttribute("surname", credentials.getUser().getSurname());
        model.addAttribute("email", credentials.getUser().getEmail());
        model.addAttribute("job", credentials.getUser().getJob());
        model.addAttribute("biography", credentials.getUser().getBiography());
        model.addAttribute("image", credentials.getUser().getImage());
        model.addAttribute("dateOfBirth", credentials.getUser().getDateOfBirth());

        return "user/profile";
    }
	
	
	
}
