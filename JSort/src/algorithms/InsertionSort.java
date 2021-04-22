package algorithms;

public class InsertionSort extends Algorithm {
    private int curPos = 0;

    public InsertionSort(int length) {
        super(length);
    }

    @Override
    public void sort() {
        int[] array = this.getArray();

        this.curPos++;
        int value = array[this.curPos];
        int i = this.curPos;
        while (i > 0 && array[i - 1] > value) {
            array[i] = array[i - 1];
            i = i - 1;
        }
        array[i] = value;
    }
}
