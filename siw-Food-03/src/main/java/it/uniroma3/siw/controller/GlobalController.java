package it.uniroma3.siw.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import it.uniroma3.siw.model.Credentials;

@ControllerAdvice
public class GlobalController {
    @ModelAttribute("userDetails")

    public UserDetails getUser() {
        UserDetails user = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }
    
    @ModelAttribute("userRole")
    public String getUserRole() {
        UserDetails user = getUser();
        if (user != null) {
            // Assuming your userDetails object has a method to get authorities/roles
            return user.getAuthorities().stream()
                       .map(GrantedAuthority::getAuthority)
                       .filter(role -> role.equals(Credentials.ADMIN_ROLE))
                       .findFirst()
                       .orElse(Credentials.DEFAULT_ROLE);
        }
        return Credentials.DEFAULT_ROLE;
    }
}
