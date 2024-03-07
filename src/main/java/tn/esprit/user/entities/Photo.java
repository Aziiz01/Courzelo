package tn.esprit.user.entities;

import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "photos")
@Data

public class Photo {
    @Id
    private String id;

    private String title;

    private String type;

    private Binary image;

    public Photo(String title, String type, Binary image) {
        this.title = title;
        this.type = type;
        this.image = image;
    }
}