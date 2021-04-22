package algorithms;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Algorithm {
    private int length;
    private int[] array;

    Algorithm(int length) {
        this.length = length;
    }

    public int[] getArray() {
        return this.array;
    }

    public void initialize() {
        this.array = new int[this.length];

        for (int i = 0; i < this.length; i++) {
            this.array[i] = i + 1;
        }
    }

    public void shuffle() {
        Random random = ThreadLocalRandom.current();

        for (int i = this.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            int a = this.array[index];
            this.array[index] = this.array[i];
            this.array[i] = a;
        }
    }

    public boolean isSorted() {
        for (int i = 0; i < this.length - 1; i++) {
            if (this.array[i] > this.array[i + 1]) {
                return false;
            }
        }

        return true;
    }

    abstract public void sort();
}
