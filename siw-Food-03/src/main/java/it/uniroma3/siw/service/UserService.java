package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The UserService handles logic for Users.
 */
@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;
    
    @Autowired
    private CredentialsRepository credentialsRepository;

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public User getUser(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }

    /**
     * This method saves a User in the DB.
     * @param user the User to save into the DB
     * @return the saved User
     * @throws DataIntegrityViolationException if a User with the same username
     *                              as the passed User already exists in the DB
     */
    @Transactional
    public User saveUser(User user) {
        return this.userRepository.save(user);
    }

    /**
     * This method retrieves all Users from the DB.
     * @return a List with all the retrieved Users
     */
    @Transactional
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        Iterable<User> iterable = this.userRepository.findAll();
        for(User user : iterable)
            result.add(user);
        return result;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isUserAdmin(User user) {
        // Trova le credenziali dell'utente
        Credentials credentials = credentialsRepository.findByUser(user);
        
        // Verifica se le credenziali sono presenti e se il ruolo è ADMIN
        return credentials != null && credentials.getRole().equals(Credentials.ADMIN_ROLE);
    }
    
    /*Verificare se funziona */
    
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}
	
	public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();
        return findByEmail(currentUserEmail);
    }

	public void deleteById(Long id) {
		try {
        userRepository.deleteById(id);
         } catch (EntityNotFoundException e) {
            throw new RuntimeException("Il cuoco con ID " + id + " non esiste", e);
         }
	}

	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
}

