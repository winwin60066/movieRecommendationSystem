import java.util.*;

public class MovieService {
    private Map<String, Set<String>> movieToTypes = new HashMap<>();
    private Map<String, Set<String>> typeToMovies = new HashMap<>();

    public void addMovie(String movieName, List<String> types) {
        // Clean up the types (trim whitespace)
        List<String> cleanedTypes = new ArrayList<>();
        for (String type : types) {
            cleanedTypes.add(type.trim());
        }

        Set<String> typeSet = new HashSet<>(cleanedTypes);
        movieToTypes.put(movieName, typeSet);

        for (String type : typeSet) {
            typeToMovies.putIfAbsent(type, new HashSet<>());
            typeToMovies.get(type).add(movieName);
        }
    }

    public Set<String> getMoviesByType(String type) {
        return typeToMovies.getOrDefault(type, new HashSet<>());
    }

    public Set<String> getTypesByMovie(String movieName) {
        return movieToTypes.getOrDefault(movieName, new HashSet<>());
    }

    public Set<String> getRecommendationsByMovie(String movieName) {
        if (!movieToTypes.containsKey(movieName)) {
            return new HashSet<>();
        }

        Set<String> types = movieToTypes.get(movieName);
        Set<String> recommendations = new HashSet<>();
        
        for (String type : types) {
            recommendations.addAll(typeToMovies.getOrDefault(type, new HashSet<>()));
        }
        recommendations.remove(movieName); // Remove the input movie itself
        
        return recommendations;
    }

    public boolean movieExists(String movieName) {
        return movieToTypes.containsKey(movieName);
    }

    public boolean typeExists(String type) {
        return typeToMovies.containsKey(type);
    }

    public Set<String> getAllMovies() {
        return movieToTypes.keySet();
    }

    public Set<String> getAllTypes() {
        return typeToMovies.keySet();
    }

    public boolean isEmpty() {
        return movieToTypes.isEmpty();
    }

    public Map<String, Set<String>> getMovieToTypesMap() {
        return new HashMap<>(movieToTypes);
    }

    public Map<String, Set<String>> getTypeToMoviesMap() {
        return new HashMap<>(typeToMovies);
    }
}