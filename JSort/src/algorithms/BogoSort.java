package algorithms;

public class BogoSort extends Algorithm {
    public BogoSort(int length) {
        super(length);
    }

    @Override
    public void sort() {
        this.shuffle();
    }
}
