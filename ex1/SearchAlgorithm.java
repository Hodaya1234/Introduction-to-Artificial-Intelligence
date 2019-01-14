import java.util.Stack;

/**
 * The abstract class for the search akgorithms
 */
public abstract class SearchAlgorithm {

    /**
     * The search algorithm differs for each inheriting class
     * @param start the start node
     * @param end the goal node
     * @return a result object
     */
    public abstract Result Search(Node start, Node end);

    /**
     * Receive the goal node that was found, and by going backwards reconstruct the entire path that led to it.
     * @param finalNode the goal node that was found
     * @return a string that represents the path. (U - up, D - down, L - left, R - right)
     */
    public String ReconstructPath(Node finalNode){
        Stack<String> reversedPath = new Stack<>();
        Node currentNode = finalNode;
        while(currentNode.GetParent() != null){
            reversedPath.push(currentNode.GetOperator().toString());
            currentNode = currentNode.GetParent();
        }
        StringBuilder builder = new StringBuilder();
        while (!reversedPath.empty()){
            builder.append(reversedPath.pop());
        }
        return builder.toString();
    }
}