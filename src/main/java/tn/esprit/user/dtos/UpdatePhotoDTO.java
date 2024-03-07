package tn.esprit.user.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdatePhotoDTO {
    MultipartFile photo;
}

