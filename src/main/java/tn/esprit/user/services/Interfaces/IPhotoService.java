package tn.esprit.user.services.Interfaces;

import tn.esprit.user.entities.Photo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IPhotoService {
    Photo addPhoto(MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getPhoto(String photoId);
}

