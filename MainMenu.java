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
            System.out.println("\nMOVIE RECOMMENDATION SYSTEM\n");
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
                    pressEnterToContinue();
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
            System.out.println("Recommended movies with same types:");
            index = 1;
            for (String rec : recommendations) {
                System.out.println(index + ". " + rec);
                index++;
            }
            System.out.print("Do you want to see the graph right now? (Y/N): ");
            String ans = scanner.nextLine();
            if (ans.equalsIgnoreCase("y")){
                Set<String> subGraphMovies = new HashSet<>();
                subGraphMovies.add(movie);
                subGraphMovies.addAll(recommendations);
                MovieGraphPopup.showGraph(movieService, subGraphMovies);;
        
        }
        pressEnterToContinue();
    }


    }

    private void recommendByType() {
        System.out.println("\nAvailable types:");
        int index = 1;
        for (String type : movieService.getAllTypes()) {
            System.out.println(index + ". " + type);
            index++;
        }
        System.out.print("Enter a type: ");
        String type = scanner.nextLine();

        if (!movieService.typeExists(type)) {
            System.out.println("Type not found in the system.");
            pressEnterToContinue();
            return;
        }

        System.out.println("Movies under type '" + type + "':");
        index = 1;
        for (String movie : movieService.getMoviesByType(type)) {
            System.out.println(index + ". " + movie);
            index++;
        }
        pressEnterToContinue();

        System.out.print("Do you want to see the graph right now? (Y/N): ");
        String ans = scanner.nextLine();
        if (ans.equalsIgnoreCase("y")){
            Set<String> subGraphMovies = new HashSet<>(movieService.getMoviesByType(type));
            MovieGraphPopup.showGraph(movieService, subGraphMovies);;
        }
    }

    private void manageMovieMenu() {
        int option;
        do {
            System.out.println("\n--- Manage Movie ---");
            System.out.println("[1] Add Movie with Types");
            System.out.println("[2] Show Movie Graph");
            System.out.println("[3] Back to Main Menu");
            System.out.println("[0] Exit");
            System.out.print("Enter your choice: ");

            option = getValidIntInput();

            switch (option) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    showMovieGraph();
                    break;
                case 3:
                    System.out.println("Back to main menu");
                    pressEnterToContinue();
                    break;
                case 0:
                    System.out.println("Exiting system...");
                    pressEnterToContinue();
                    System.exit(0);
                default:
                    System.out.println("\n[Invalid choice, try again]\n");
            }
        } while (option != 0);
    }

    private void addMovie() {
        System.out.print("Enter movie name: ");
        String movie = scanner.nextLine();
        System.out.print("Enter types (comma separated): ");
        List<String> types = Arrays.asList(scanner.nextLine().split(","));
        movieService.addMovie(movie, types);
        System.out.println("Movie added: " + movie + " with types " + types);
    }

    private void showMovieGraph(){
        MovieGraphPopup.showGraph(movieService, movieService.getAllMovies());
    }

    //I just want to remain JY code, in case needed 

    // private void showMovieGraph() {
    //     System.out.println("Movie -> Types:");
    //     for (Map.Entry<String, Set<String>> entry : movieService.getMovieToTypesMap().entrySet()) {
    //         System.out.println(entry.getKey() + " -> " + entry.getValue());
    //     }
    //     System.out.println("\nType -> Movies:");
    //     for (Map.Entry<String, Set<String>> entry : movieService.getTypeToMoviesMap().entrySet()) {
    //         System.out.println(entry.getKey() + " -> " + entry.getValue());
    //     }
    // }

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