package it.uniroma3.siw.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class ProfileController {

	
	@Autowired
    private CredentialsService credentialsService;
	
	@Autowired
    private ReviewService reviewService;
	
	 @Autowired 
	 private ImageService imageService;

		
	@GetMapping("/usAd/profile")
    public String getProfile(Model model, Authentication authentication) {
        String username = authentication.getName();
        Credentials credentials = credentialsService.findUserByUsername(username);
        List<Review> reviews = reviewService.findByCooke(credentials.getUser());

        model.addAttribute("name", credentials.getUser().getName());
        model.addAttribute("surname", credentials.getUser().getSurname());
        model.addAttribute("email", credentials.getUser().getEmail());
        model.addAttribute("job", credentials.getUser().getJob());
        model.addAttribute("phrase", credentials.getUser().getPhrase());
        model.addAttribute("biography", credentials.getUser().getBiography());
        model.addAttribute("image", credentials.getUser().getImage());
        model.addAttribute("dateOfBirth", credentials.getUser().getDateOfBirth());
        model.addAttribute("reviews", reviews);

        return "usAd/profile";
    }
	
	@GetMapping("/usAd/edit")
    public String showEditProfileForm(Model model, Authentication authentication) {
        String username = authentication.getName();
        Credentials credentials = credentialsService.findUserByUsername(username);

        model.addAttribute("name", credentials.getUser().getName());
        model.addAttribute("surname", credentials.getUser().getSurname());
        model.addAttribute("job", credentials.getUser().getJob());
        model.addAttribute("phrase", credentials.getUser().getPhrase());
        model.addAttribute("biography", credentials.getUser().getBiography());
        model.addAttribute("dateOfBirth", credentials.getUser().getDateOfBirth());

        return "usAd/editProfile.html";
    }

    /*@PostMapping("/usAd/edit")
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
        return "redirect:/usAd/profile";
    }*/    
	
	@PostMapping("/usAd/edit")
	public String updateProfile(@RequestParam("biography") String biography,
			                    @RequestParam("phrase") String phrase,
	                            @RequestParam("job") String job,
	                            @RequestParam("image") MultipartFile nuovaImmagine,
	                             Authentication authentication) {
		
	    String username = authentication.getName();
	    Credentials credentials = credentialsService.findUserByUsername(username);
	    User user = credentials.getUser();

	    user.setBiography(biography);
	    user.setPhrase(phrase);
	    user.setJob(job);

	    if (nuovaImmagine != null && !nuovaImmagine.isEmpty()) {
	        try {
	            Image image = new Image(nuovaImmagine.getBytes());
	            
	            // Salva l'immagine nel database
	            image = imageService.saveImage(image);
	            
	            // Imposta l'utente nell'immagine
	            image.setUser(user);
	            
	            // Imposta l'immagine nell'utente
	            user.setImage(image);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    credentialsService.save(credentials);
	    return "redirect:/usAd/profile";
	}
    
    @GetMapping("/usAd/deleteReview/{id}")
    public String deleteReview(@PathVariable("id") Long id, Authentication authentication, Model model) {
        String username = authentication.getName();
        Credentials credentials = credentialsService.findUserByUsername(username);
        
        // Verifica che la recensione da cancellare appartenga all'utente autenticato
        Review review = reviewService.findById(id);
        if (review != null && review.getUser().getId().equals(credentials.getUser().getId())) {
        	reviewService.delete(review);
            return "redirect:/usAd/profile";
        } else {
            // Gestione errore se l'utente prova a cancellare una recensione non sua
            model.addAttribute("errore", "Non hai il permesso per cancellare questa recensione.");
            return "redirect:/usAd/profile";
        }
    }
}  

