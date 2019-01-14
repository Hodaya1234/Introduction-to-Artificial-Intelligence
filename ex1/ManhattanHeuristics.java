import java.util.Comparator;

/**
 * The "Manhattan distance" heuristic sets higher priority to the nodes that have lower manhattan distance from the
 * goal node
 * @param <Node>
 */
public class ManhattanHeuristics<Node> implements Comparator<Node> {

    /**
     * Calculate the sum of manhattan distances for each number in the state to its goal state.
     * @param state The input state to calculate the sum of the distances for all the numbers
     * @param N The size of each dimension of the square. (The square size is N X N)
     * @return The sum
     */
    private static int StateManhattanDistance(int[] state, int N){
        int distances = 0;
        for (int index = 0; index < N*N; index++){
            int currentNum = state[index];
            if(currentNum==0) continue;
            distances += ManhattanDistance(index+1, currentNum, N);
        }
        return distances;
    }

    /**
     * Calculate the Manhattan distance for two numbers in a square of dimensions N X N.
     * @param i the first number
     * @param j the second number
     * @param N the dimension size
     * @return the distance
     */
    private static int ManhattanDistance(int i, int j, int N){
        if(i==j){
            return 0;
        }
        int xDistance = Math.abs(((i - 1) % N) - ((j-1) % N));
        int yDistance = Math.abs(Math.floorDiv(i, N) - Math.floorDiv(j, N));
        return xDistance + yDistance;
    }


    /**
     * The function for the comparator interface - uses to extract nodes from the priority queue.
     * The higher priority is given to the nodes with minimum (depth + manhattan distance to the goal)
     * @param o1 first node
     * @param o2 second node
     * @return a positive number if o2 should be before o1
     * 0 if they have equal priorities
     * a negative number if o1 should be before o2
     */
    @Override
    public int compare(Node o1, Node o2) {
        if (!(o1 instanceof PuzzleNode && o2 instanceof PuzzleNode)){
            throw new ClassCastException();
        }
        PuzzleNode p1 = (PuzzleNode) o1;
        PuzzleNode p2 = (PuzzleNode) o2;
        int N = (int)Math.sqrt(p1.GetSize());
        int g_1 = p1.GetDepthIndex();
        int g_2 = p2.GetDepthIndex();
        int h_1 = StateManhattanDistance(p1.GetState(), N);
        int h_2 = StateManhattanDistance(p2.GetState(), N);

        int sub = (g_1 + h_1) - (g_2 + h_2);
        if (sub == 0){
            long time = p1.getCreatedTime() - p2.getCreatedTime();
            if (time == 0) {
                return p1.GetOperator().ordinal() - p2.GetOperator().ordinal();
            }
            return (int)Math.signum(time);
        }
        return sub;
    }
}
