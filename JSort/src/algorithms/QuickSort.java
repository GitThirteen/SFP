package algorithms;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort extends Algorithm {
    private final int pivot;

    public QuickSort(int length) {
        super(length);
        this.pivot = this.generatePivot();
    }

    @Override
    public void sort() {

    }

    private int generatePivot() {
        return ThreadLocalRandom.current().nextInt(0, this.getArray().length + 1);
    }
}
