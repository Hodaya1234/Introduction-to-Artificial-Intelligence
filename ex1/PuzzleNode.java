import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.arraycopy;

/**
 * The node for our assignment - a square grid of integers the should be re-arranged in the right order.
 * The square is of dimension N X N, and contains the integers in the range [1, N*N-1] and 0 for empty spot.
 */
public class PuzzleNode implements Node, Comparable {

    private int N; // the size of the square is N X N
    private int size;
    private int depthIndex;
    private int[] state;
    private int zeroIndex;
    private ArrayList<Node> successors;
    private Node parent;
    private Operator parentOperator;
    private long createdTime;

    /**
     * Constructor
     * @param depthIndex the depth from the root (0). Each puzzle node creates its successors with (depth+1)
     * @param N size of each side of the square
     * @param state a list of n*n integers in the order of the puzzle
     * @param zeroIndex the index of the empty spot - 0.
     */
    public PuzzleNode(int depthIndex, int N, int[] state, int zeroIndex, long createdTime ){
        this.N = N;
        this.depthIndex = depthIndex;
        this.state = state;
        this.zeroIndex = zeroIndex;
        this.size = N*N;
        this.createdTime = createdTime;
    }

    /**
     *
     * @return The time of the creation of the node
     */
    public long getCreatedTime(){
        return createdTime;
    }

    /**
     *
     * @return return the state
     */
    @Override
    public int[] GetState() {
        return state;
    }

    /**
     *
     * @return the depth from the root
     */
    @Override
    public int GetDepthIndex(){
        return depthIndex;
    }

    /**
     *
     * @return N*N - the length of the numbers list
     */
    public int GetSize(){
        return size;
    }

    /**
     * Create a successor
     * @param nextIndex the index for the zero-spot
     * @param operator the operator that was being made to get to the successor
     * @return the new created puzzle node
     */
    private PuzzleNode CreateNextNode(int nextIndex, Operator operator, long createdTime){
        int tempNumber;
        int[] newState = new int[size];
        arraycopy(state, 0, newState, 0, size);
        tempNumber = state[nextIndex];
        newState[zeroIndex] = tempNumber;
        newState[nextIndex] = 0;
        PuzzleNode nextNode=  new PuzzleNode(depthIndex +1, N, newState, nextIndex, createdTime);
        nextNode.SetParent(this);
        nextNode.SetOperator(operator);
        return nextNode;
    }

    /**
     * return all possible successors in a particular order. (U, D, L, R)
     * for each option check if it is valid - i.e if a number can be moved to the empty spot
     * @return a list of the valid successors by order
     */
    @Override
    public ArrayList<Node> GetSuccessors() {
        if (successors != null){
            return successors;
        }
        successors = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        if (zeroIndex < size - N){
            // The UP move is legal
            successors.add(CreateNextNode(zeroIndex + N, Operator.U, currentTime));
        }
        if (zeroIndex >= N){
            // The DOWN move is legal
            successors.add(CreateNextNode(zeroIndex - N, Operator.D, currentTime));
        }
        if (zeroIndex % N < N-1){
            // The LEFT operator is legal
            successors.add(CreateNextNode(zeroIndex + 1, Operator.L, currentTime));
        }
        if (zeroIndex % N > 0){
            // The RIGHT operator is legal
            successors.add(CreateNextNode(zeroIndex - 1, Operator.R, currentTime));
        }
        return successors;
    }

    /**
     *
     * @param parent The previous node that led to the current one
     */
    @Override
    public void SetParent(Node parent) {
        this.parent = parent;
    }

    /**
     *
     * @return the parent node
     */
    @Override
    public Node GetParent() {
        return parent;
    }

    /**
     *
     * @return the operator that led to the current node
     */
    @Override
    public Operator GetOperator(){ return this.parentOperator; }

    /**
     *
     * @param o the operator that led to the current node
     */
    public void SetOperator(Operator o){
        parentOperator = o;
    }

    /**
     * compare a puzzle node to another puzzle node. compare them by the depth index (lower index gets higher priority)
     * and by the order of the operators.
     * @param other another puzzle node
     * @return a positive integer if the current node has lower priority
     * zero if they are equal priority
     * a negative integer if the current node has higher priority
     */
    @Override
    public int compareTo(Object other) {
        if (!(other instanceof PuzzleNode)) throw new ClassCastException();
        PuzzleNode otherNode = (PuzzleNode) other;
        if (otherNode.depthIndex == this.depthIndex)
            return Integer.signum(this.parentOperator.ordinal() - otherNode.parentOperator.ordinal());
        return Integer.signum(this.depthIndex - otherNode.depthIndex);
    }

    /**
     * check if two puzzle nodes have the same state
     * @param other another puzzle node
     * @return true if their states are identical
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PuzzleNode)) return false;
        PuzzleNode otherNode = (PuzzleNode) other;
        return Arrays.equals(otherNode.GetState(), state);
    }
}
