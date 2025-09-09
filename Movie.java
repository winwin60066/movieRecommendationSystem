import java.util.*;

public class Movie {
    private String name;
    private Set<String> types;

    public Movie(String name, List<String> types) {
        this.name = name;
        this.types = new HashSet<>(types);
    }

    public String getName() {
        return name;
    }

    public Set<String> getTypes() {
        return new HashSet<>(types);
    }

    public boolean hasType(String type) {
        return types.contains(type);
    }

    @Override
    public String toString() {
        return name + " : " + types;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}