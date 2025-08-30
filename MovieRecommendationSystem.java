import java.util.*;

//a

class Graph {
    private Map<String, List<String>> graph;

    public Graph() {
        graph = new HashMap<>();
    }

    // Add Vertex
    public void addVertex(String movie) {
        graph.putIfAbsent(movie, new ArrayList<>());
        System.out.println("Added movie: " + movie);
    }

    // Remove Vertex
    public void removeVertex(String movie) {
        if (graph.containsKey(movie)) {
            graph.remove(movie);
            for (List<String> neighbors : graph.values()) {
                neighbors.remove(movie);
            }
            System.out.println("Removed movie: " + movie);
        } else {
            System.out.println("Movie not found.");
        }
    }

    // Add Edge
    public void addEdge(String movie1, String movie2) {
        if (graph.containsKey(movie1) && graph.containsKey(movie2)) {
            graph.get(movie1).add(movie2);
            graph.get(movie2).add(movie1);
            System.out.println("Connected " + movie1 + " and " + movie2);
        } else {
            System.out.println("One or both movies not found.");
        }
    }

    // Remove Edge
    public void removeEdge(String movie1, String movie2) {
        if (graph.containsKey(movie1)) graph.get(movie1).remove(movie2);
        if (graph.containsKey(movie2)) graph.get(movie2).remove(movie1);
        System.out.println("Disconnected " + movie1 + " and " + movie2);
    }

    // BFS Traversal (for recommendations)
    public List<String> recommend(String startMovie) {
        if (!graph.containsKey(startMovie)) {
            System.out.println("Movie not found.");
            return Collections.emptyList();
        }

        List<String> recommendations = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(startMovie);

        while (!queue.isEmpty()) {
            String movie = queue.poll();
            if (!visited.contains(movie)) {
                visited.add(movie);
                for (String neighbor : graph.get(movie)) {
                    if (!visited.contains(neighbor)) {
                        recommendations.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }
        return recommendations;
    }

    // Display Graph
    public void display() {
        for (String movie : graph.keySet()) {
            System.out.println(movie + " -> " + graph.get(movie));
        }
    }
}

public class MovieRecommendationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph g = new Graph();

        while (true) {
            System.out.println("\n--- Movie Recommendation System ---");
            System.out.println("1. Add Movie");
            System.out.println("2. Remove Movie");
            System.out.println("3. Add Connection");
            System.out.println("4. Remove Connection");
            System.out.println("5. Show Graph");
            System.out.println("6. Recommend Movies");
            System.out.println("0. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter movie name: ");
                    String mvAdd = sc.nextLine();
                    g.addVertex(mvAdd);
                    break;
                case 2:
                    System.out.print("Enter movie name to remove: ");
                    String mvRemove = sc.nextLine();
                    g.removeVertex(mvRemove);
                    break;
                case 3:
                    System.out.print("Enter first movie: ");
                    String m1 = sc.nextLine();
                    System.out.print("Enter second movie: ");
                    String m2 = sc.nextLine();
                    g.addEdge(m1, m2);
                    break;
                case 4:
                    System.out.print("Enter first movie: ");
                    String rm1 = sc.nextLine();
                    System.out.print("Enter second movie: ");
                    String rm2 = sc.nextLine();
                    g.removeEdge(rm1, rm2);
                    break;
                case 5:
                    g.display();
                    break;
                case 6:
                    System.out.print("Enter movie to get recommendations: ");
                    String start = sc.nextLine();
                    List<String> recs = g.recommend(start);
                    System.out.println("Recommended movies: " + recs);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
