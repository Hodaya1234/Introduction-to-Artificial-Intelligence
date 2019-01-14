import java.util.*;

/**
 * Breadth-first search implementation
 */
public class BFS extends SearchAlgorithm {
    /**
     * Using a queue in order to select the next successor to develop.
     * @param start the root state
     * @param goal the goal state
     * @return the Result object
     */
    public Result Search(Node start, Node goal){
        Queue<Node> openNodes = new LinkedList<>();
        openNodes.add(start);

        Node current;
        List<Node> successors;
        int numberOfNodes = 0;

        while (!openNodes.isEmpty()){
            current = openNodes.remove();
            numberOfNodes++;
            if (current.equals(goal)){
                return new Result(ReconstructPath(current), numberOfNodes, 0);
            }
            successors = current.GetSuccessors();
            openNodes.addAll(successors);
            }
        return null;
    }
}

