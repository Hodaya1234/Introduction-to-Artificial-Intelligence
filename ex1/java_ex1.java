import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class java_ex1 {

    /**
     * The main method: it read the input argument from the file "input.txt"
     * and then send the arguments to the right algorithm class
     * @param args empty - should not receive input arguments
     */
    public static void main(String[] args){
        Map<Integer, SearchAlgorithm> algorithmMap = new HashMap<>();
        algorithmMap.put(1, new IDS());
        algorithmMap.put(2, new BFS());
        algorithmMap.put(3, new Astar(new ManhattanHeuristics<>()));

        File file = new File("input.txt");
        BufferedReader reader;
        String algoTypeStr;
        String nStr;
        String stateStr;
        try {
            reader = new BufferedReader(new FileReader(file));
            algoTypeStr = reader.readLine();
            nStr = reader.readLine();
            stateStr = reader.readLine();
        } catch(Exception e){
            e.printStackTrace();
            return;
        }
        SearchAlgorithm algorithm = algorithmMap.get(Integer.parseInt(algoTypeStr));
        int N = Integer.parseInt(nStr);
        String[] stateStrSplit = stateStr.split("-");
        int[] state = new int[N*N];
        int[] checkList = new int[N*N];
        Arrays.fill(state, 0);
        Arrays.fill(checkList, 0);
        int zeroIndex = -1;
        int currentNum;
        for (int i = 0; i<N*N; i++){
            currentNum = Integer.parseInt(stateStrSplit[i].trim());
            checkList[currentNum] ++;
            state[i] = currentNum;
            if (currentNum == 0){
                zeroIndex = i;
            }
        }
        for(int i = 0; i < N*N; i++){
            if (checkList[i] != 1){
                System.out.println("Wrong state input");
                return;
            }
        }
        PuzzleNode startNode = new PuzzleNode(0,N,state,zeroIndex, 0);
        int[] goal = new int[N*N];
        for (int i = 0; i < N*N; i++){
            goal[i] = i + 1;
        }
        goal[N*N - 1] = 0;
        PuzzleNode goalNode = new PuzzleNode(Integer.MAX_VALUE, N, goal, N*N - 1, 0);
        Result res = algorithm.Search(startNode, goalNode);
        if(res != null){
            WriteResult(res);
        }
    }

    /**
     * Write the result of the algorithm to the output.txt file
     * @param res an object that contains the relevant results for each algorithm type.
     */
    static void WriteResult(Result res){
        StringBuilder builder = new StringBuilder();
        builder.append(res.getPath()).append(" ").append(res.getNodeNumber()).append(" ").append(res.getCost());
        String toWrite = builder.toString();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("output.txt"), StandardCharsets.UTF_8))) {
            writer.write(toWrite);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
