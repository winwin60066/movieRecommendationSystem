import java.util.Arrays;

public class InitialMovie {
    public static void loadDefaultMovies(MovieService movieService) {
        String filename = "movies.txt";
        movieService.loadFromFile(filename);

        // Only add default movies if the textfile is empty
        if (movieService.isEmpty()) {
            movieService.addMovie("Avengers", Arrays.asList("Action", "Drama", "Sci-Fi"));
            movieService.addMovie("Iron Man", Arrays.asList("Action", "Sci-Fi"));
            movieService.addMovie("Gladiator", Arrays.asList("Action", "Drama", "Adventure"));
            movieService.addMovie("Titanic", Arrays.asList("Drama", "Romance"));
            movieService.addMovie("Pursuit of Happyness", Arrays.asList("Drama"));
            movieService.addMovie("Frozen", Arrays.asList("Animation", "Drama", "Adventure"));
            movieService.addMovie("Toy Story", Arrays.asList("Animation", "Comedy"));
            movieService.addMovie("Zootopia", Arrays.asList("Animation", "Comedy"));
            movieService.addMovie("Mr. Bean's Holiday", Arrays.asList("Comedy"));
            movieService.addMovie("The Conjuring", Arrays.asList("Horror", "Drama", "Thriller"));
            movieService.addMovie("The Conjuring 2", Arrays.asList("Horror", "Drama", "Thriller"));
            movieService.addMovie("The Conjuring 3", Arrays.asList("Horror", "Drama", "Thriller")); 
            movieService.addMovie("The Conjuring 4", Arrays.asList("Horror", "Drama", "Thriller")); 
            movieService.addMovie("Inception", Arrays.asList("Sci-Fi", "Thriller", "Drama"));
            movieService.addMovie("The Dark Knight", Arrays.asList("Action", "Thriller", "Drama"));
            movieService.addMovie("Star Wars", Arrays.asList("Sci-Fi", "Action", "Adventure")); 
            movieService.addMovie("Jurassic Park", Arrays.asList("Sci-Fi", "Adventure", "Thriller"));
            movieService.addMovie("LOTR: Fellowship", Arrays.asList("Fantasy", "Adventure", "Drama")); 
            movieService.addMovie("Harry Potter", Arrays.asList("Fantasy", "Adventure")); 
            movieService.addMovie("The Matrix", Arrays.asList("Sci-Fi", "Action", "Thriller"));
            movieService.addMovie("La La Land", Arrays.asList("Romance", "Drama", "Comedy"));
            movieService.addMovie("Interstellar", Arrays.asList("Sci-Fi", "Drama", "Adventure"));
            movieService.addMovie("Jaws", Arrays.asList("Thriller", "Adventure"));
            movieService.addMovie("Shutter Island", Arrays.asList("Thriller", "Drama"));
            movieService.addMovie("The Lion King", Arrays.asList("Animation", "Drama", "Adventure"));
            movieService.addMovie("Finding Nemo", Arrays.asList("Animation", "Comedy", "Adventure"));
            movieService.addMovie("Alien", Arrays.asList("Sci-Fi", "Horror", "Thriller")); 
            movieService.addMovie("Forrest Gump", Arrays.asList("Drama", "Romance", "Comedy"));
            movieService.saveToFile("movies.txt");

            movieService.saveToFile(filename);
        }
    }
}