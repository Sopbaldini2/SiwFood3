package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.CredentialsService;

@Controller
public class ProfileController {

	
	@Autowired
    private CredentialsService credentialsService;

		
	@GetMapping("/user/profile")
    public String getProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        Credentials credentials = credentialsService.findUserByUsername(username);

        model.addAttribute("name", credentials.getUser().getName());
        model.addAttribute("surname", credentials.getUser().getSurname());
        model.addAttribute("email", credentials.getUser().getEmail());
        model.addAttribute("job", credentials.getUser().getJob());
        model.addAttribute("biography", credentials.getUser().getBiography());
        model.addAttribute("image", credentials.getUser().getImage());
        model.addAttribute("dateOfBirth", credentials.getUser().getDateOfBirth());

        return "user/profile";
    }
	
	@GetMapping("/user/edit")
    public String showEditProfileForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        Credentials credentials = credentialsService.findUserByUsername(username);

        model.addAttribute("name", credentials.getUser().getName());
        model.addAttribute("surname", credentials.getUser().getSurname());
        model.addAttribute("job", credentials.getUser().getJob());
        model.addAttribute("biography", credentials.getUser().getBiography());
        model.addAttribute("dateOfBirth", credentials.getUser().getDateOfBirth());

        return "user/editProfile.html";
    }

    @PostMapping("/user/edit")
    public String updateProfile(@RequestParam("biography") String biography,
    		                    @RequestParam("job") String job,
                                @RequestParam("image") String nuovaImmagine,
                                Authentication authentication) {
        String username = authentication.getName();
        Credentials credentials = credentialsService.findUserByUsername(username);

        credentials.getUser().setBiography(biography);
        credentials.getUser().setJob(job);
        credentials.getUser().setImage(nuovaImmagine); // Aggiorna l'immagine con la nuova stringa

        credentialsService.save(credentials);
        return "redirect:/user/profile";
    }
}  

