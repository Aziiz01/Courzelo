package tn.esprit.user.services.Implementations;

import tn.esprit.user.entities.Photo;
import tn.esprit.user.repositories.PhotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.user.services.Interfaces.IPhotoService;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoService implements IPhotoService {
    private final PhotoRepository photoRepository;

    @Override
    public Photo addPhoto(MultipartFile file) throws IOException {
        log.info("Starting Adding Photo");
        Photo photo = new Photo(
                file.getName(),
                file.getContentType(),
                new Binary(BsonBinarySubType.BINARY, file.getBytes())
        );
        log.info("Finished Adding Photo");
        return photoRepository.save(photo);
    }

    @Override
    public ResponseEntity<byte[]> getPhoto(String photoId) {
        Optional<Photo> photoOptional = photoRepository.findById(photoId);
        if (photoOptional.isPresent()) {
            Photo photo = photoOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(photo.getType()))
                    .body(photo.getImage().getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}