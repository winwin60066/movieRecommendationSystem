import javax.swing.*;
import java.awt.*;
//import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

//a

class MovieGraphPanel extends JPanel {
    private Map<String, Point> nodes;
    private List<String[]> edges;

    public MovieGraphPanel() {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
        // Example positions for nodes
        nodes.put("Inception", new Point(100, 50));
        nodes.put("Interstellar", new Point(300, 50));
        nodes.put("Tenet", new Point(200, 200));

        // Example edges
        edges.add(new String[]{"Inception", "Interstellar"});
        edges.add(new String[]{"Inception", "Tenet"});
        edges.add(new String[]{"Tenet", "Interstellar"});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw edges
        g.setColor(Color.BLACK);
        for (String[] edge : edges) {
            Point p1 = nodes.get(edge[0]);
            Point p2 = nodes.get(edge[1]);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Draw nodes
        for (String movie : nodes.keySet()) {
            Point p = nodes.get(movie);
            g.setColor(Color.CYAN);
            g.fillOval(p.x - 20, p.y - 20, 40, 40);
            g.setColor(Color.BLACK);
            g.drawOval(p.x - 20, p.y - 20, 40, 40);
            g.drawString(movie, p.x - 20, p.y - 25);
        }
    }
}

public class MovieGraphPopup {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Movie Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.add(new MovieGraphPanel());
        frame.setVisible(true);
    }
}
