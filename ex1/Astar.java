import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * A* algorithm: uses a priority queue when choosing the next successor to develop.
 * The priority queue receives the Comparator = the heuristics as input, and it combines the distance from the start
 * node to the current node.
 */
public class Astar extends SearchAlgorithm {

    private Comparator<Node> h;

    Astar(Comparator<Node> h){
        this.h = h;
    }

    /**
     * Search for the goal using the heuristics and the depth of the node
     * @param start the root node - start position
     * @param end the goal node
     * @return A result object with the relevant information
     */
    @Override
    public Result Search(Node start, Node end) {
        PriorityQueue<Node> openNodes = new PriorityQueue<>(11, h);

        openNodes.add(start);
        Node current;
        int numberOfNodes = 0;

        while (!openNodes.isEmpty()) {
            current = openNodes.remove();
            numberOfNodes++;
            if (current.equals(end)) {
                return new Result(ReconstructPath(current), numberOfNodes, current.GetDepthIndex());
            }
            openNodes.addAll(current.GetSuccessors());
        }
        return null;
    }
}
