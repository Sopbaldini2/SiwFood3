package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.service.ImageService;

@Controller
public class ImageController {

	@Autowired 
    private ImageService imageService;
    
    @GetMapping("/display/image/{id}")
    public ResponseEntity<byte[]> displayItemImage(@PathVariable("id") Long id) {
        Optional<Image> imgOptional = imageService.getImage(id);

        if (imgOptional.isPresent()) {
            Image img = imgOptional.get();
            byte[] image = img.getBytes();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
