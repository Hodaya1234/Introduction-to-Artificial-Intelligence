/**
 * A container class for the result of each search algorithm
 */
public class Result {

    private String path;
    private int nodeNumber;
    private int cost;

    /**
     * constructor
     * @param path a string that represents the reconstruction of the path that led to the result.
     * @param nodeNumber the number of nodes that were checked (got out of the "open list")
     * @param cost forr each algorithm the cost is defined differently, but it usually means the depth of the goal node
     */
    public Result(String path, int nodeNumber, int cost) {
        this.path = path;
        this.nodeNumber = nodeNumber;
        this.cost = cost;
    }

    /**
     *
     * @return the reconstruction of the path
     */
    public String getPath() {
        return path;
    }

    /**
     *
     * @return the number of nodes that were developed
     */
    public int getNodeNumber() {
        return nodeNumber;
    }

    /**
     *
     * @return the "cost" of the search - usually the depth of the goal node in comparison to the root
     */
    public int getCost() {
        return cost;
    }
}
