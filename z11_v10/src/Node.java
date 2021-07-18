import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

    private final String name;
    // список узлов от source до текущего
    private List<Node> shortestPath = new LinkedList<>();
    // расстояние от source узла до текущего
    private Integer distance = Integer.MAX_VALUE;
    // смежные узлы
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance)
    {
        adjacentNodes.put(destination, distance);
    }

    public Node(String name) {
        this.name = name;
    }

    public void setDistance(int i)
    {
        distance = i;
    }

    public Integer getDistance()
    {
        return distance;
    }

    public List<Node> getShortestPath()
    {
        return shortestPath;
    }

    public Map<Node, Integer> getAdjacentNodes()
    {
        return adjacentNodes;
    }

    public String getName()
    {
        return name;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return name.equals(node.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }

    public void setShortestPath(LinkedList<Node> shortestPath)
    {
        this.shortestPath = shortestPath;
    }
}