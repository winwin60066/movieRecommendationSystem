import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

public class Main extends JPanel {

    // Simple inner class instead of record (works on Java 8+)
    static class City {
        final String name;
        final int x, y;
        City(String name, int x, int y) {
            this.name = name; this.x = x; this.y = y;
        }
    }

    private final City[] cities = new City[] {
            new City("Seattle", 75, 50),
            new City("San Francisco", 50, 210),
            new City("Los Angeles", 75, 275),
            new City("Denver", 275, 175),
            new City("Kansas City", 400, 245),
            new City("Chicago", 450, 100),
            new City("Boston", 700, 80),
            new City("New York", 675, 120),
            new City("Atlanta", 575, 295),
            new City("Miami", 600, 400),
            new City("Dallas", 408, 325),
            new City("Houston", 450, 360)
    };

    private final int[][] edges = new int[][] {
            {0, 1}, {0, 3}, {0, 5},
            {1, 0}, {1, 2}, {1, 3},
            {2, 1}, {2, 3}, {2, 4}, {2, 10},
            {3, 0}, {3, 1}, {3, 2}, {3, 4}, {3, 5},
            {4, 2}, {4, 3}, {4, 5}, {4, 7}, {4, 8}, {4, 10},
            {5, 0}, {5, 3}, {5, 4}, {5, 6}, {5, 7},
            {6, 5}, {6, 7}, {7, 4}, {7, 5}, {7, 6}, {7, 8},
            {8, 4}, {8, 7}, {8, 9}, {8, 10}, {8, 11},
            {9, 8}, {9, 11}, {10, 2}, {10, 4}, {10, 8}, {10, 11},
            {11, 8}, {11, 9}, {11, 10}
    };

    private BufferedImage background;

    public Main() {
        setPreferredSize(new Dimension(750, 450));
        try {
            // Online background image; comment this out if you want offline-only
            background = ImageIO.read(new URL(
                    "https://upload.wikimedia.org/wikipedia/commons/3/37/Blank_US_Map_(states_only).svg.png"
            ));
        } catch (Exception e) {
            // Fallback: just use a plain background if the image can't be loaded
            background = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Enable antialiasing
        if (g instanceof Graphics2D) {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }

        // Draw background map (scaled to panel)
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(245, 245, 245));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        // Draw edges
        g.setColor(Color.GRAY);
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            City c1 = cities[edge[0]];
            City c2 = cities[edge[1]];
            g.drawLine(c1.x, c1.y, c2.x, c2.y);
        }

        // Draw cities
        for (int i = 0; i < cities.length; i++) {
            City city = cities[i];
            g.setColor(Color.RED);
            g.fillOval(city.x - 4, city.y - 4, 8, 8);
            g.setColor(Color.BLACK);
            g.drawString(city.name, city.x + 6, city.y - 6);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("US Map with Cities");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.add(new Main());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
