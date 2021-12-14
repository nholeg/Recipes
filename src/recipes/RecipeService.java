package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    RecipyRepository recipyRepository;

    Comparator<Recipe> dateComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe o1, Recipe o2) {
            return o2.getDate().compareTo(o1.getDate());
        }
    };

    @Autowired
    public RecipeService(RecipyRepository recipyRepository) {
        this.recipyRepository = recipyRepository;
    }

    public Optional<Recipe> findById(long id) {
        return recipyRepository.findById(id);
    }

    public Recipe save(Recipe recipe) {
        return recipyRepository.save(recipe);
    }

    public void deleteById(long id) { recipyRepository.deleteById(id); }

    public List<Recipe> findAllByCategory(String category) {
        return findAll().stream()
                .filter(e -> e.getCategory().toLowerCase().equals(category.toLowerCase()))
                .sorted(dateComparator)
                .collect(Collectors.toList());
    }

    public List<Recipe> findAllByName(String name) {
        return findAll().stream()
                .filter(e -> e.getName().toLowerCase().contains(name.toLowerCase()))
                .sorted(dateComparator)
                .collect(Collectors.toList());
    }

    public List<Recipe> findAll() {
        return (List<Recipe>) recipyRepository.findAll();
    }
}
