package algorithms;

public class SelectionSort extends Algorithm {
    private int curPos = 0;

    public SelectionSort(int length) {
        super(length);
    }

    @Override
    public void sort() {
        int[] array = this.getArray();

        int lowestValuePos = this.curPos;
        int lowestValue = array[lowestValuePos];
        for (int i = this.curPos + 1; i < array.length; i++) {
            if (array[i] < lowestValue) {
                lowestValue = array[i];
                lowestValuePos = i;
            }
        }

        int temp = array[this.curPos];
        array[this.curPos] = array[lowestValuePos];
        array[lowestValuePos] = temp;

        this.curPos++;
    }
}
