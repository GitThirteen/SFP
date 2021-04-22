package algorithms;

public class BubbleSort extends Algorithm {
    public BubbleSort(int length) {
        super(length);
    }

    @Override
    public void sort() {
        int[] array = this.getArray();

        for (int i = 0; i < array.length - 1; i++) {
            int n1 = array[i];
            int n2 = array[i + 1];

            if (n1 > n2) {
                array[i] = n2;
                array[i + 1] = n1;
            }
        }
    }
}
