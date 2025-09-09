import java.util.*;
import java.io.*;

public class MovieService {
    private Map<String, Set<String>> movieToTypes = new LinkedHashMap<>();
    private Map<String, Set<String>> typeToMovies = new HashMap<>();

    public void addMovie(String movieName, List<String> types) {
        // Clean up the types (trim whitespace)
        List<String> cleanedTypes = new ArrayList<>();
        for (String type : types) {
            cleanedTypes.add(type.trim());
        }

        Set<String> newTypeSet = new HashSet<>(cleanedTypes);

        // If movie exists, merge types
        Set<String> typeSet = movieToTypes.getOrDefault(movieName, new HashSet<>());
        typeSet.addAll(newTypeSet);
        movieToTypes.put(movieName, typeSet);

        // Update typeToMovies for new types
        for (String type : newTypeSet) {
            typeToMovies.putIfAbsent(type, new HashSet<>());
            typeToMovies.get(type).add(movieName);
        }
    }

    public void removeMovie(String movieName) {
        if (!movieToTypes.containsKey(movieName)) {
            return; // Movie doesn't exist, nothing to remove
        }

        // Remove movie from movieToTypes and update typeToMovies
        Set<String> types = movieToTypes.remove(movieName);
        for (String type : types) {
            Set<String> movies = typeToMovies.get(type);
            if (movies != null) {
                movies.remove(movieName);
                if (movies.isEmpty()) {
                    typeToMovies.remove(type); // Remove type if no movies are associated
                }
            }
        }
    }

    public boolean removeTypeFromMovie(String movieName, String type) {
        if (!movieToTypes.containsKey(movieName)) {
            return false; // Movie doesn't exist
        }

        Set<String> types = movieToTypes.get(movieName);
        if (!types.contains(type)) {
            return false; // Type doesn't exist for this movie
        }

        // Remove type from movie
        types.remove(type);
        if (types.isEmpty()) {
            movieToTypes.remove(movieName); // Remove movie if it has no types
        }

        // Update typeToMovies
        Set<String> movies = typeToMovies.get(type);
        if (movies != null) {
            movies.remove(movieName);
            if (movies.isEmpty()) {
                typeToMovies.remove(type); // Remove type if no movies are associated
            }
        }
        return true;
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
        return new LinkedHashMap<>(movieToTypes);
    }

    public Map<String, Set<String>> getTypeToMoviesMap() {
        return new HashMap<>(typeToMovies);
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (Map.Entry<String, Set<String>> entry : movieToTypes.entrySet()) {
                writer.println(entry.getKey() + ":" + String.join(",", entry.getValue()));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return; // File doesn't exist, skip loading
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(":", 2);
                if (parts.length == 2) {
                    String movie = parts[0].trim();
                    List<String> types = Arrays.asList(parts[1].split(","));
                    addMovie(movie, types);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading file: " + e.getMessage());
        }
    }
}