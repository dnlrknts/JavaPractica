import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Graph {


    private Set<Node> nodes = new HashSet<>();

    public Graph()
    {
    }
    public Graph(Set<Node> nodes)
    {
        this.nodes = nodes;
    }

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }
    public void calculateShortestPathFromSource(Node source)
    {
        source.setDistance(0);

        // урегулированные узлы (известно минимальное расстояние от source)
        Set<Node> settledNodes = new HashSet<>();
        // неурегулированные узлы (пока не известно минимальное расстояние от source)
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0)
        {
            // находим ближайший неурегулированный узел
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);

            // для каждого узла связанного с текущим
            for (Map.Entry<Node,Integer> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet())
            {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                // если этото узела нет в урегулированных
                if (!settledNodes.contains(adjacentNode))
                {
                    //пересчитать расстояние до него
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    // добавить в неурегулированный
                    unsettledNodes.add(adjacentNode);
                }
            }
            // после проверки всех узлов связанных с текущим
            // текущий узел считается урегулированным
            settledNodes.add(currentNode);
        }
        // установить финальную точку
        nodes.stream().filter(node->node!=source)
                .forEach(node -> node.getShortestPath()
                        .add(node));
    }
    private static Node getLowestDistanceNode(Set<Node> unsettledNodes)
    {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        // пройти по всем неурегулированным узлам и определить ближайший
        for (Node node: unsettledNodes)
        {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance)
            {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }
    private static void calculateMinimumDistance(Node evaluationNode,
                                                 Integer edgeWeigh, Node sourceNode)
    {
        Integer sourceDistance = sourceNode.getDistance();
        // если расстояние через текуищий узел меньше чем в evaluationNode
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance())
        {
            // обновить расстояние в evaluationNode
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            // добавить sourceNode список кратчайшего пути до evaluationNode
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}