public class MovieRecommendationApp {
    public static void main(String[] args) {
        // Initialize the movie service
        MovieService movieService = new MovieService();
        
        // Load default movies
        InitialMovie.loadDefaultMovies(movieService);
        
        // start from main menu
        MainMenu mainMenu = new MainMenu(movieService);
        mainMenu.displayMainMenu();
    }
}