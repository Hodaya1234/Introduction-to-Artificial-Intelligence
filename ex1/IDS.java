import java.util.*;

/**
 * iterative deepening search - similar to DFS but each iteration has a larger limit than the previous
 */
public class IDS extends SearchAlgorithm {

    /**
     * Loop over ascending limits until a result is found
     * @param start the root node
     * @param goal the goal node
     * @return the Result object
     */
    public Result Search(Node start, Node goal){
        Result res;
        for(int i = 0; i<1000; i++){
            res = LimitedSearch(start, goal, i);
            if (res!=null){
                return res;
            }
        }
        return null;
    }

    /**
     * One iteration of search with a particular limit
     * @param start root node
     * @param goal goal node
     * @param limit the algorithm does not develop nodes that have depth > limit
     * @return Result object
     */
    private Result LimitedSearch(Node start, Node goal, int limit){

        Stack<Node> openNodes = new Stack<>();
        openNodes.push(start);

        Node current;
        List<Node> successors;
        int numberOfNodes = 0;

        while (!openNodes.isEmpty()){
            current = openNodes.pop();
            numberOfNodes++;
            if (current.equals(goal)){
                return new Result(ReconstructPath(current), numberOfNodes, current.GetDepthIndex());
            }
            if(current.GetDepthIndex() <= limit){
                successors = current.GetSuccessors();
                for (int i = successors.size() - 1; i >= 0; i--){
                    openNodes.push(successors.get(i));
                }
            }
        }
        return null;
    }
}
