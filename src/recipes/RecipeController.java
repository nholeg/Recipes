package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class RecipeController {
    final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        Optional<Recipe> recipe = recipeService.findById(id);
        return recipe.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<List<Recipe>> searchRecipeByCategory(@RequestParam @NotBlank Optional<String> category, @RequestParam @NotBlank Optional<String> name) {
        List<Recipe> recipes;
        if (category.isPresent()) {
            recipes = recipeService.findAllByCategory(category.get());
        }
        else if (name.isPresent()) {
            recipes = recipeService.findAllByName(name.get());
        }
        else {
            return new ResponseEntity<>(List.of(new Recipe()), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<Map<String, Long>> addRecipe(@Valid @RequestBody Recipe recipe, Authentication auth) {
        recipe.setAuthor(auth.getName());
        Recipe savedRecipe = recipeService.save(recipe);
        return new ResponseEntity<>(Map.of("id", savedRecipe.getId()), HttpStatus.OK);
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> updateRecipe(@Valid @RequestBody Recipe recipe, @PathVariable Long id, Authentication auth) {
        Optional<Recipe> data = recipeService.findById(id);
        if (data.isPresent()) {
            Recipe recipeToUpdate = data.get();
            if (auth.getName().equals(recipeToUpdate.getAuthor())) {
                recipeToUpdate.setDate(LocalDateTime.now().toString());
                recipeToUpdate.setName(recipe.getName());
                recipeToUpdate.setCategory(recipe.getCategory());
                recipeToUpdate.setDirections(recipe.getDirections());
                recipeToUpdate.setDescription(recipe.getDescription());
                recipeToUpdate.setIngredients(recipe.getIngredients());
                Recipe recipeSaved = recipeService.save(recipeToUpdate);
                return new ResponseEntity<>(recipeSaved, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(recipeToUpdate, HttpStatus.FORBIDDEN);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable @Min(value = 1L) long id, Authentication auth) {
        Optional<Recipe> data = recipeService.findById(id);
        if (data.isPresent()) {
            Recipe recipe = data.get();
            if (auth.getName().equals(recipe.getAuthor())) {
                recipeService.deleteById(id);
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            else {
                return new ResponseEntity<>(recipe, HttpStatus.FORBIDDEN);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}