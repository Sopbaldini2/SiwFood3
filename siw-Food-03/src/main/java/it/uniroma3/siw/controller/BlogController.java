package it.uniroma3.siw.controller;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.model.Post;
import it.uniroma3.siw.service.ImageService;
import it.uniroma3.siw.service.PostService;

@Controller
public class BlogController {
	
	@Autowired
    private PostService postService;
	
	@Autowired
    private ImageService imageService;

    /*@GetMapping("/blog")
    public String blogAdmin(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "/blog";
    }*/
	
	@GetMapping("/blog")
    public String blogAdmin(Model model) {
        List<Post> posts = postService.getAllPosts().stream().map(post -> {
            post.setFormattedDate(post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
            return post;
        }).collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "/blog";
    }
	
	@GetMapping("/admin/indexPost")
	public String indexPost() {
		return "/admin/indexPost.html";
	}
	
	@GetMapping("/admin/managePosts")
	public String managePosts(Model model) {
		model.addAttribute("posts", this.postService.getAllPosts());
		return "admin/managePosts.html";
	}

    @GetMapping("/admin/new")
    public String newPost(Model model) {
        model.addAttribute("post", new Post());
        return "admin/newPost";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post,
    		               @RequestParam("imageB") MultipartFile imageB) {
    	
    	if (!imageB.isEmpty()) {
            Image image = imageService.saveImagePost(imageB);
            post.setImageP(image);
        }
    	
        postService.savePost(post);
        return "redirect:/blog";
    }
   
    @GetMapping("/admin/delete/{id}")
    public String deletePost(@PathVariable("id") Long id, Model model) {
        postService.deletePostById(id);
        return "redirect:/admin/managePosts"; // Redirect alla pagina di gestione dei post dopo la cancellazione
    }
    
    @GetMapping("/admin/formUpdatePost/{id}")
    public String formUpdatePost(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "admin/formUpdatePost";
    }

    @GetMapping("/admin/formUpdateContentPost/{id}")
    public String formUpdateContentPost(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        if (post != null) {
            model.addAttribute("post", post);
            return "admin/updateContentPost";
        } else {
            model.addAttribute("messaggioErrore", "Post not found");
            return "admin/managePosts";
        }
    }
    
    @PostMapping("/admin/updateContentPost/{id}")
    public String updateContentPost(@PathVariable("id") Long id,
                                    @RequestParam("content") String newContent,
                                    Model model) {
        Post post = postService.findById(id);
        if (post != null) {
            post.setContent(newContent); // Utilizza il setter per aggiornare il contenuto
            postService.savePost(post);
            return "redirect:/admin/managePosts";
        } else {
            model.addAttribute("messaggioErrore", "Post not found");
            return "admin/managePosts";
        }
    }
    

    @GetMapping("/admin/formUpdateImagePost/{id}")
    public String formUpdateImagePost(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "admin/updateImagePost"; // Assicura che questo nome corrisponda al tuo file HTML
    }

    // Endpoint per aggiornare l'immagine di un post
    @PostMapping("/admin/updateImagePost/{id}")
    public String updateImagePost(@PathVariable("id") Long id,
                                  @RequestParam("imageP") MultipartFile imageP,
                                  Model model) {
        Post post = postService.findById(id);
        if (post != null) {
                if (imageP != null && !imageP.isEmpty()) {
                    // Usa il metodo esistente per salvare l'immagine
                    Image newImage = imageService.saveImagePost(imageP);

                    // Aggiorna l'immagine del post
                    post.setImageP(newImage);

                    // Salva il post aggiornato
                    postService.savePost(post);
                }
                
            model.addAttribute("post", post);
            return "redirect:/admin/managePosts"; // Redirect alla pagina di gestione dei post
        } else {
            model.addAttribute("messaggioErrore", "Post not found");
            return "admin/managePosts"; // Gestisci il caso in cui il post non sia trovato
        }
    }

}
