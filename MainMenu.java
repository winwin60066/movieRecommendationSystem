import java.util.*;

public class MainMenu {
    private MovieService movieService;
    private Scanner scanner;

    public MainMenu(MovieService movieService) {
        this.movieService = movieService;
        this.scanner = new Scanner(System.in);
    }

    public void displayMainMenu() {
        int choice;
        do {
            
            System.out.println("MOVIE RECOMMENDATION SYSTEM\n");
            System.out.println("----- Main Menu -----");
            System.out.println("[1] Recommend Movie");
            System.out.println("[2] Manage Movie");
            System.out.println("[0] Exit");
            System.out.print("Enter your choice: ");

            choice = getValidIntInput();

            switch (choice) {
                case 1:
                    recommendationMenu();
                    break;
                case 2:
                    manageMovieMenu();
                    break;
                case 0:
                    
                    System.out.println("\n[Exiting system...]\n");
                    System.exit(0);
                default:
                    System.out.println("\n[Invalid choice, try again]\n");
            }
        } while (choice != 0);
    }

    private void recommendationMenu() {
        if (movieService.isEmpty()) {
            
            System.out.println("No movies in the system yet!");
            pressEnterToContinue();
            return;
        }
        System.out.println("\n--- Recommend Movie ---");
        System.out.println("[1] Get recommendations by movie name");
        System.out.println("[2] Get recommendations by movie type");
        System.out.println("[3] Back to Main Menu");
        System.out.println("[0] Exit");
        System.out.print("Enter your choice: ");

        int option = getValidIntInput();
        switch (option) {
            case 1:
                recommendByMovie();
                break;
            case 2:
                recommendByType();
                break;
            case 3:
                pressEnterToContinue();
                break;
            case 0:
                System.out.println("\n[Exiting system...]\n");
                pressEnterToContinue();
                System.exit(0);
            default:
                System.out.println("\n[Invalid choice, try again]\n");
        }
    }

    private void recommendByMovie() {
        
        System.out.println("\nAvailable movies:");
        int index = 1;
        for (String movie : movieService.getAllMovies()) {
            System.out.println(index + ". " + movie);
            index++;
        }
        System.out.print("Enter a movie you like: ");
        String movie = scanner.nextLine();

        if (!movieService.movieExists(movie)) {
            System.out.println("\n[Movie not found in the system]");
            pressEnterToContinue();
            return;
        }

        Set<String> types = movieService.getTypesByMovie(movie);
        System.out.println("\n" + movie + " belongs to types: " + types);

        Set<String> recommendations = movieService.getRecommendationsByMovie(movie);
        if (recommendations.isEmpty()) {
            System.out.println("\n[No recommendations found for this movie]");
        } else {
            System.out.println("\nRecommended movies:");
            index = 1;
            for (String rec : recommendations) {
                System.out.println(index + ". " + rec);
                index++;
            }
            System.out.print("\nWould you like to see the graph traversal results? (y/n): ");
            String ans = scanner.nextLine();
            if (ans.equalsIgnoreCase("y")) {
                Set<String> subGraphMovies = new HashSet<>();
                subGraphMovies.add(movie);
                subGraphMovies.addAll(recommendations);
                MovieGraphPopup.showGraph(movieService, subGraphMovies, movie, null); // Pass input movie, no selected type
            }
        }
        pressEnterToContinue();
    }

    private void recommendByType() {
        
        System.out.println("\nAvailable types:");
        int index = 1;
        for (String type : movieService.getAllTypes()) {
            System.out.println(index + ". " + type);
            index++;
        }
        System.out.print("\nEnter a type: ");
        String type = scanner.nextLine();

        if (!movieService.typeExists(type)) {
            System.out.println("\n[Type not found in the system]");
            pressEnterToContinue();
            return;
        }

        System.out.println("\nRecommended movies under type '" + type + "':");
        index = 1;
        for (String movie : movieService.getMoviesByType(type)) {
            System.out.println(index + ". " + movie);
            index++;
        }

        System.out.print("\nWould you like to see the graph traversal results? (y/n): ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("y")) {
            Set<String> subGraphMovies = new HashSet<>(movieService.getMoviesByType(type));
            MovieGraphPopup.showGraph(movieService, subGraphMovies, null, type); // Pass selected type, no input movie
        }
        pressEnterToContinue();
    }

    private void manageMovieMenu() {
        int option;
        do {
            
            System.out.println("\n--- Manage Movie ---");
            System.out.println("[1] Add Movie with Types");
            System.out.println("[2] Remove Movie");
            System.out.println("[3] Remove Type from Movie");
            System.out.println("[4] Show All Movie Graph");
            System.out.println("[5] Back to Main Menu");
            System.out.println("[0] Exit");
            System.out.print("Enter your choice: ");

            option = getValidIntInput();

            switch (option) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    removeMovie();
                    break;
                case 3:
                    removeTypeFromMovie();
                    break;
                case 4:
                    showMovieGraph();
                    break;
                case 5:
                    displayMainMenu();
                    pressEnterToContinue();
                    break;
                case 0:
                    System.out.println("\n[Exiting system...]\n");
                    System.exit(0);
                default:
                    System.out.println("\n[Invalid choice, try again]\n");
            }
        } while (option != 0);
    }

    private void addMovie() {
        System.out.println("\nAvailable movies:");
        if (movieService.isEmpty()) {
            System.out.println("\n[No movies yet]");
        } else {
            int index = 1;
            for (String movie : movieService.getAllMovies()) {
                System.out.println(index + ". " + movie + ": " + movieService.getTypesByMovie(movie));
                index++;
            }
        }

        String movie;
        do {
            System.out.print("\nEnter movie name: ");
            movie = scanner.nextLine().trim();
            if (movie.isEmpty()) {
                System.out.println("\n[Movie name cannot be empty, please try again]");
            }
        } while (movie.isEmpty());

        boolean exists = movieService.movieExists(movie);

        List<String> types;
        do {
            System.out.print("Enter types (comma separated): ");
            String input = scanner.nextLine().trim();
            types = new ArrayList<>();
            if (!input.isEmpty()) {
                for (String s : input.split("\\s*,\\s*")) { //split and ignore space
                    if (!s.isEmpty()) {
                        types.add(s);
                    }
                }
            }
            if (types.isEmpty()) {
                System.out.println("\n[At least one valid type must be provided, please try again]");
            }
        } while (types.isEmpty());

        movieService.addMovie(movie, types);

        if (exists) {
            System.out.println("\n[Successfully added new types to existing movie: " + movie + " (current types: " + movieService.getTypesByMovie(movie) + ")]");
        } else {
            System.out.println("\n[Successfully added new movie: " + movie + " with types " + types + "]");
        }
        movieService.saveToFile("movies.txt");
        pressEnterToContinue();
    }

    private void removeMovie() {
        
        System.out.println("\nAvailable movies:");
        if (movieService.isEmpty()) {
            System.out.println("No movies to remove.");
            pressEnterToContinue();
            return;
        }
        int index = 1;
        for (String movie : movieService.getAllMovies()) {
            System.out.println(index + ". " + movie + ": " + movieService.getTypesByMovie(movie));
            index++;
        }
        System.out.print("\nEnter movie name to remove: ");
        String movie = scanner.nextLine();

        if (!movieService.movieExists(movie)) {
            System.out.println("Movie not found in the system.");
        } else {
            movieService.removeMovie(movie);
            System.out.println("\n[Successfully removed movie: " + movie + "]");
            movieService.saveToFile("movies.txt");
        }
        pressEnterToContinue();
    }

    private void removeTypeFromMovie() {
        
        System.out.println("\nAvailable movies:");
        if (movieService.isEmpty()) {
            System.out.println("No movies to modify.");
            pressEnterToContinue();
            return;
        }
        int index = 1;
        for (String movie : movieService.getAllMovies()) {
            System.out.println(index + ". " + movie + ": " + movieService.getTypesByMovie(movie));
            index++;
        }
        System.out.print("\nEnter movie name: ");
        String movie = scanner.nextLine();

        if (!movieService.movieExists(movie)) {
            System.out.println("Movie not found in the system.");
            pressEnterToContinue();
            return;
        }

        Set<String> types = movieService.getTypesByMovie(movie);
        System.out.println("\nTypes for " + movie + ": " + types);
        System.out.print("Enter type to remove: ");
        String type = scanner.nextLine();

        if (!types.contains(type)) {
            System.out.println("Type '" + type + "' not found for movie: " + movie);
            pressEnterToContinue();
            return;
        }

        if (types.size() == 1) {
            System.out.print("This is the last type for " + movie + ". Removing it will delete the movie. Proceed? (y/n): ");
            String confirm = scanner.nextLine();
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Operation cancelled.");
                pressEnterToContinue();
                return;
            }
            movieService.removeMovie(movie);
            System.out.println("\n[Successfully removed movie: " + movie + "]");
        } else {
            if (movieService.removeTypeFromMovie(movie, type)) {
                System.out.println("\n[Successfully removed type '" + type + "' from movie: " + movie + "]");
            } else {
                System.out.println("Type '" + type + "' not found for movie: " + movie);
            }
        }
        movieService.saveToFile("movies.txt");
        pressEnterToContinue();
    }

    private void showMovieGraph() {
        if (movieService.isEmpty()) {
            
            System.out.println("\n[No movies to display in the graph]");
            pressEnterToContinue();
            return;
        }
        MovieGraphPopup.showGraph(movieService, movieService.getAllMovies(), null, null); // No input movie or selected type
        pressEnterToContinue();
    }

    private int getValidIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("\n[Invalid choice! Please enter number, try again!]\n\nEnter your choice: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }

    private void pressEnterToContinue() {
        System.out.print("\n[Press Enter to continue...]\n");
        scanner.nextLine();
    }
}