import java.util.*;

public class combined {
    private Map<String, Set<String>> movieToTypes = new HashMap<>();
    private Map<String, Set<String>> typeToMovies = new HashMap<>();

    public static void main(String[] args) {
        combined app = new combined();
        app.defaultMovie(); 
        app.mainMenu();
    }

    private void defaultMovie() {
        addMovie("Avengers", Arrays.asList("Action", "Drama"));
        addMovie("Iron Man", Arrays.asList("Action"));
        addMovie("Gladiator", Arrays.asList("Action", "Drama"));
        addMovie("Titanic", Arrays.asList("Drama"));
        addMovie("The Pursuit of Happyness", Arrays.asList("Drama"));
        addMovie("Frozen", Arrays.asList("Animation", "Drama"));
        addMovie("Toy Story", Arrays.asList("Animation", "Comedy"));
        addMovie("Zootopia", Arrays.asList("Animation", "Comedy"));
        addMovie("Mr. Bean’s Holiday", Arrays.asList("Comedy"));
        addMovie("The Conjuring", Arrays.asList("Horror", "Drama"));
        addMovie("It", Arrays.asList("Horror"));
        addMovie("A Quiet Place", Arrays.asList("Horror", "Drama"));
    }


    private void addMovie(String movie, List<String> types) {
        Set<String> typeSet = new HashSet<>(types);
        movieToTypes.put(movie, typeSet);

        for (String type : typeSet) {
            typeToMovies.putIfAbsent(type, new HashSet<>());
            typeToMovies.get(type).add(movie);
        }
    }


    
    private void recommendMovie(Scanner sc) {
    if (movieToTypes.isEmpty()) {
        System.out.println("No movies in the system yet!");
        return;
    }

    System.out.println("\n--- Recommend Movie ---");
    System.out.println("[1] Get recommendations by movie name");
    System.out.println("[2] Get recommendations by movie type");
    System.out.println("[3] Back");
    System.out.print("Enter your choice: ");

    int option;
    while (!sc.hasNextInt()) {
        System.out.print("\n[Invalid choice! Please enter number, try again!]\n\nEnter your choice: ");
        sc.next();
    }
    option = sc.nextInt();
    sc.nextLine();

    switch (option) {
        case 1: //recommend by movie name
            System.out.println("Available movies:");
            for (String m : movieToTypes.keySet()) {
                System.out.println("- " + m);
            }
            System.out.print("Enter a movie you like: ");
            String movie = sc.nextLine();

            if (!movieToTypes.containsKey(movie)) {
                System.out.println("Movie not found in the system.");
                return;
            }

            Set<String> types = movieToTypes.get(movie);
            System.out.println(movie + " belongs to types: " + types);

            Set<String> recommendations = new HashSet<>();
            for (String type : types) {
                recommendations.addAll(typeToMovies.getOrDefault(type, new HashSet<>()));
            }
            recommendations.remove(movie);

            if (recommendations.isEmpty()) {
                System.out.println("No recommendations found for this movie.");
            } else {
                System.out.println("Recommended movies with same types: " + recommendations);
            }
            break;

        case 2: // recommend by movie type            
            System.out.println("Available types:");
            for (String t : typeToMovies.keySet()) {
                System.out.println("- " + t);
            }
            System.out.print("Enter a type: ");
            String type = sc.nextLine();

            if (!typeToMovies.containsKey(type)) {
                System.out.println("Type not found in the system.");
                return;
            }

            System.out.println("Movies under type '" + type + "': " + typeToMovies.get(type));
            break;

        case 3:
            break;
        default:
            System.out.println("Invalid option. Please try again.");
    }
}

    private void manageMovie(Scanner sc) {
        int option;
        do {
            System.out.println("\n--- Manage Movie ---");
            System.out.println("1. Add Movie with Types");
            System.out.println("2. Show Movie Graph");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("\n[Invalid choice! Please enter number, try again!]\n\nEnter your choice: ");
                sc.next();
            }
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter movie name: ");
                    String movie = sc.nextLine();
                    System.out.print("Enter types (comma separated): ");
                    List<String> types = Arrays.asList(sc.nextLine().split(","));
                    addMovie(movie, types);
                    System.out.println("Movie added: " + movie + " with types " + types);
                    break;

                case 2:
                    System.out.println("Movie -> Types:");
                    for (String m : movieToTypes.keySet()) {
                        System.out.println(m + " -> " + movieToTypes.get(m));
                    }
                    System.out.println("\nType -> Movies:");
                    for (String t : typeToMovies.keySet()) {
                        System.out.println(t + " -> " + typeToMovies.get(t));
                    }
                    break;

                case 3:
                    System.out.println("Returning to main menu...");
                    break;

                default:
                    System.out.println("Invalid option, try again.");
            }
        } while (option != 3);
    }

    public void mainMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("MOVIE RECOMMENDATION SYSTEM\n");
            System.out.println("----- Main Menu -----");
            System.out.println("[1] Recommend Movie");
            System.out.println("[2] Manage Movie");
            System.out.println("[3] Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("\n[Invalid choice! Please enter number, try again!]\n\nEnter your choice: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    recommendMovie(sc);
                    break;
                case 2:
                    manageMovie(sc);
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        } while (choice != 3);

        sc.close();
    }


}
