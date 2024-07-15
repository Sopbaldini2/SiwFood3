package it.uniroma3.siw.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Image;
import it.uniroma3.siw.repository.ImageRepository;

@Service
public class ImageService {
	
	@Autowired
    private ImageRepository imageRepository;

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }

	public Image findById(Long imageId) {
		return imageRepository.findById(imageId).get();
	}

	public Image saveImagePost(MultipartFile file) {
        Image image = new Image();
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return imageRepository.save(image);
    }
}
