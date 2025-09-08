import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class MovieGraphPanel extends JPanel {
    private Map<String, Point> nodes; // Stores both movie and type nodes
    private List<String[]> edges; // Edges between movies and types
    //private MovieService movieService; TODO
    private Set<String> recommendedMovies;
    private Set<String> types;

    public MovieGraphPanel(MovieService movieService, Set<String> recommendedMovies) {
        //this.movieService = movieService; TODO
        this.recommendedMovies = recommendedMovies;
        this.nodes = new HashMap<>();
        this.edges = new ArrayList<>();
        this.types = new HashSet<>();

        // Collect types associated with recommended movies
        for (String movie : recommendedMovies) {
            types.addAll(movieService.getTypesByMovie(movie));
        }

        // Generate edges: connect each movie to its types
        for (String movie : recommendedMovies) {
            for (String type : movieService.getTypesByMovie(movie)) {
                if (types.contains(type)) { // Only include types present in recommended movies
                    edges.add(new String[]{movie, type});
                }
            }
        }

        // Position nodes in a circular layout
        positionNodesCircularly();
    }

    private void positionNodesCircularly() {
        int centerX = 250; // Center of the panel
        int centerY = 200;
        int movieRadius = 150; // Radius for movie nodes
        int typeRadius = 100;  // Radius for type nodes (inner circle)
        
        // Position movie nodes
        int index = 0;
        int numMovies = recommendedMovies.size();
        for (String movie : recommendedMovies) {
            double angle = 2 * Math.PI * index / Math.max(numMovies, 1);
            int x = (int) (centerX + movieRadius * Math.cos(angle));
            int y = (int) (centerY + movieRadius * Math.sin(angle));
            nodes.put(movie, new Point(x, y));
            index++;
        }

        // Position type nodes
        index = 0;
        int numTypes = types.size();
        for (String type : types) {
            double angle = 2 * Math.PI * index / Math.max(numTypes, 1);
            int x = (int) (centerX + typeRadius * Math.cos(angle));
            int y = (int) (centerY + typeRadius * Math.sin(angle));
            nodes.put(type, new Point(x, y));
            index++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw edges
        g.setColor(Color.BLACK);
        for (String[] edge : edges) {
            Point p1 = nodes.get(edge[0]);
            Point p2 = nodes.get(edge[1]);
            if (p1 != null && p2 != null) {
                g.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }

        // Draw nodes (movies in cyan, types in yellow)
        for (String node : nodes.keySet()) {
            Point p = nodes.get(node);
            if (recommendedMovies.contains(node)) {
                g.setColor(Color.CYAN); // Movie nodes
            } else {
                g.setColor(Color.YELLOW); // Type nodes
            }
            g.fillOval(p.x - 20, p.y - 20, 40, 40);
            g.setColor(Color.BLACK);
            g.drawOval(p.x - 20, p.y - 20, 40, 40);
            g.drawString(node, p.x - 20, p.y - 25);
        }
    }
}

public class MovieGraphPopup {
    public static void showGraph(MovieService movieService, Set<String> recommendedMovies) {
        JFrame frame = new JFrame("Movie Recommendation Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new MovieGraphPanel(movieService, recommendedMovies));
        frame.setVisible(true);
    }
}