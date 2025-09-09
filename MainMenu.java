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
                    
                    System.out.println("Exiting system...");
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
        System.out.println("[3] Back");
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
                
                System.out.println("Exiting system...");
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
            System.out.println("Movie not found in the system.");
            pressEnterToContinue();
            return;
        }

        Set<String> types = movieService.getTypesByMovie(movie);
        System.out.println(movie + " belongs to types: " + types);

        Set<String> recommendations = movieService.getRecommendationsByMovie(movie);
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations found for this movie.");
        } else {
            System.out.println("\nRecommended movies:");
            index = 1;
            for (String rec : recommendations) {
                System.out.println(index + ". " + rec);
                index++;
            }
            System.out.print("\nDo you want to display the graph now? (y/n): ");
            String ans = scanner.nextLine();
            if (ans.equalsIgnoreCase("y")) {
                Set<String> subGraphMovies = new HashSet<>();
                subGraphMovies.add(movie);
                subGraphMovies.addAll(recommendations);
                MovieGraphPopup.showGraph(movieService, subGraphMovies);
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
            System.out.println("Type not found in the system.");
            pressEnterToContinue();
            return;
        }

        System.out.println("\nMovies under type '" + type + "':");
        index = 1;
        for (String movie : movieService.getMoviesByType(type)) {
            System.out.println(index + ". " + movie);
            index++;
        }

        System.out.print("\nDo you want to display the graph now? (y/n): ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("y")) {
            Set<String> subGraphMovies = new HashSet<>(movieService.getMoviesByType(type));
            MovieGraphPopup.showGraph(movieService, subGraphMovies);
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
            System.out.println("[4] Show Movie Graph");
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
                    System.out.println("Back to main menu");
                    pressEnterToContinue();
                    break;
                case 0:
                    
                    System.out.println("Exiting system...");
                    System.exit(0);
                default:
                    System.out.println("\n[Invalid choice, try again]\n");
            }
        } while (option != 0);
    }

    private void addMovie() {
        
        System.out.println("\nAvailable movies:");
        if (movieService.isEmpty()) {
            System.out.println("No movies yet.");
        } else {
            int index = 1;
            for (String movie : movieService.getAllMovies()) {
                System.out.println(index + ". " + movie + ": " + movieService.getTypesByMovie(movie));
                index++;
            }
        }
        System.out.print("\nEnter movie name: ");
        String movie = scanner.nextLine();

        boolean exists = movieService.movieExists(movie);

        System.out.print("Enter types (comma separated): ");
        String input = scanner.nextLine();
        List<String> types = input.isEmpty() ? new ArrayList<>() : Arrays.asList(input.split(","));

        movieService.addMovie(movie, types);

        //TODO: test for this add movie that already exists(about merge type)
        if (exists) {
            System.out.println("\nSuccessfully added new types to existing movie: " + movie + " (current types: " + movieService.getTypesByMovie(movie) + ")");
        } else {
            System.out.println("\nSuccessfully added new movie: " + movie + " with types " + types);
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
            System.out.println("\nSuccessfully removed movie: " + movie);
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

        System.out.println("\nTypes for " + movie + ": " + movieService.getTypesByMovie(movie));
        System.out.print("Enter type to remove: ");
        String type = scanner.nextLine();

        if (movieService.removeTypeFromMovie(movie, type)) {
            System.out.println("\nSuccessfully removed type '" + type + "' from movie: " + movie);
            movieService.saveToFile("movies.txt");
        } else {
            System.out.println("Type '" + type + "' not found for movie: " + movie);
        }
        pressEnterToContinue();
    }

    private void showMovieGraph() {
        MovieGraphPopup.showGraph(movieService, movieService.getAllMovies());
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
        System.out.print("\n[Press Enter to continue...]");
        scanner.nextLine();
    }
}