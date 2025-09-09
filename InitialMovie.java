import java.util.Arrays;

public class InitialMovie {
    public static void loadDefaultMovies(MovieService movieService) {
        String filename = "movies.txt";
        movieService.loadFromFile(filename);

        // Only add default movies if the textfile is empty
        if (movieService.isEmpty()) {
            movieService.addMovie("Avengers", Arrays.asList("Action", "Drama"));
            movieService.addMovie("Iron Man", Arrays.asList("Action"));
            movieService.addMovie("Gladiator", Arrays.asList("Action", "Drama"));
            movieService.addMovie("Titanic", Arrays.asList("Drama"));
            movieService.addMovie("The Pursuit of Happyness", Arrays.asList("Drama"));
            movieService.addMovie("Frozen", Arrays.asList("Animation", "Drama"));
            movieService.addMovie("Toy Story", Arrays.asList("Animation", "Comedy"));
            movieService.addMovie("Zootopia", Arrays.asList("Animation", "Comedy"));
            movieService.addMovie("Mr. Bean's Holiday", Arrays.asList("Comedy"));
            movieService.addMovie("The Conjuring", Arrays.asList("Horror", "Drama"));
            movieService.addMovie("It", Arrays.asList("Horror"));
            movieService.addMovie("A Quiet Place", Arrays.asList("Horror", "Drama"));
            movieService.saveToFile(filename);
        }
    }
}