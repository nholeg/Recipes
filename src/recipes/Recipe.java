package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Validated
public class Recipe {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @JsonIgnore
    private String author;

    @NotBlank
    @Size(min = 8)
    private String date;

    @NotBlank(message = "name is blank")
    private String name;

    @NotBlank(message = "category is blank")
    private String category;

    @NotBlank(message = "description is blank")
    private String description;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients;

    @NotNull
    @Size(min = 1)
    @ElementCollection
    private List<String> directions;

    public Recipe() {
        date = LocalDateTime.now().toString();
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (this.author == null) {
            this.author = author;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }
}