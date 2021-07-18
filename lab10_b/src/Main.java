import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    private static final Map<String,Node> nodes = new HashMap<>();
    private static Set<Node> read(String filename) throws IOException
    {
        Files.lines(Path.of(filename))
                .map(s->s.split(";"))
                .forEach(arr->
                {
                    String sourceName = arr[0];
                    String destName = arr[1];
                    int dist = Integer.parseInt(arr[2]);
                    // добавляем в пул узлов, если узел с таким именем не найден
                    nodes.putIfAbsent(sourceName,new Node(sourceName));
                    nodes.putIfAbsent(destName,new Node(destName));
                    // соединяем в две стороны
                    nodes.get(sourceName).addDestination(nodes.get(destName),dist);
                    nodes.get(destName).addDestination(nodes.get(sourceName),dist);
                });
        return new HashSet<>(nodes.values());
    }
    public static void main(String[] args) throws IOException
    {
        Graph g = new Graph(read("data.txt"));
        g.calculateShortestPathFromSource(nodes.get("Minsk"));
        Node brest = nodes.get("Brest");
        System.out.println(brest.getShortestPath().stream().map(Node::getName).toList());
        System.out.println(brest.getDistance());

        Node velikie_luki = nodes.get("Velikie Luki");
        System.out.println(velikie_luki.getShortestPath().stream().map(Node::getName).toList());
        System.out.println(velikie_luki.getDistance());
    }
}
