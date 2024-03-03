    package tn.esprit.pidev.entities;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.springframework.data.mongodb.core.mapping.DBRef;
    import org.springframework.data.mongodb.core.mapping.Document;
    import org.springframework.data.annotation.Id;

    import org.springframework.data.mongodb.core.mapping.Field;

    import java.util.List;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Document(collection ="Question")

    public class Question {
        @Id
        private String _id;
        private String questionText;
        @ElementCollection
        private List<String> options;
        private int correctOption;
        private String explication;
        private int notequstion;
        private int dureequestion;

        @DBRef
        @JsonIgnore
        private Quiz quiz;


    }
