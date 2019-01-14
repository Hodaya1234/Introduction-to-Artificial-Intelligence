import java.util.ArrayList;
import java.util.List;

/**
 * A graph node, contains a state, the value inside the node,
 * can generate its successors
 * Each node knows its depth, its parent, and the operator that led to it
 */
public interface Node {
    Object GetState();
    ArrayList<Node> GetSuccessors();
    void SetParent(Node parent);
    Node GetParent();
    boolean equals(Object other);
    Operator GetOperator();
    int GetDepthIndex();
    long getCreatedTime();
}
