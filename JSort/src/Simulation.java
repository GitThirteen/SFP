import algorithms.Algorithm;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Simulation {
    private static final int CANVAS_WIDTH = 750;
    private static final int CANVAS_HEIGHT = 750;
    private static final int LENGTH = 5000;
    private static final Algorithms ALGORITHM = Algorithms.InsertionSort;

    private static final EnumMap<Algorithms, Algorithm> algorithmMap = new EnumMap<>(Algorithms.class);

    public static void main(String[] args) throws InterruptedException {
        addAllAlgorithms();
        Algorithm algorithm = algorithmMap.get(ALGORITHM);
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT, StdDraw.BLACK, algorithm, ALGORITHM.toString());

        canvas.createCanvas();
        algorithm.initialize();
        algorithm.shuffle();

        canvas.draw();
        while(!algorithm.isSorted()) {
            canvas.clearCanvas();
            algorithm.sort();
            canvas.draw();
        }

        System.out.println(Arrays.toString(algorithm.getArray()));
    }

    private static void addAllAlgorithms() {
        Simulation.algorithmMap.put(Algorithms.BubbleSort, new algorithms.BubbleSort(Simulation.LENGTH));
        Simulation.algorithmMap.put(Algorithms.SelectionSort, new algorithms.SelectionSort(Simulation.LENGTH));
        Simulation.algorithmMap.put(Algorithms.InsertionSort, new algorithms.InsertionSort(Simulation.LENGTH));
        Simulation.algorithmMap.put(Algorithms.BogoSort, new algorithms.BogoSort(Simulation.LENGTH));
        Simulation.algorithmMap.put(Algorithms.MergeSort, new algorithms.MergeSort(Simulation.LENGTH));
    }
}
