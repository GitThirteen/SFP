package run;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ProgressBar {
    private static ProgressBar instance;

    private int progress;
    private final int elements;

    private ProgressBar() {
        this.progress = 0;
        this.elements = this.getFileCount();
    }

    public static ProgressBar generate() {
        if (instance == null) {
            instance = new ProgressBar();
        }
        return instance;
    }

    public void update() {
        this.progress++;
        this.draw();
    }

    public void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ignored) { }
    }

    private void draw() {
        int percentage = (int) Math.ceil((this.progress / (double) this.elements) * 100);
        int totalSegs = 20;

        int doneSegs = (int) Math.ceil(percentage / (double) ((100 / totalSegs) % 20));
        String progress = "[" + "=".repeat(doneSegs) + "-".repeat(totalSegs - doneSegs) + "]";
        System.out.println(progress + " (" + percentage + "%)");
        System.out.println(this.progress == this.elements ? "Comparing..." : "Reading...");
        System.out.println(this.progress + " / " + this.elements);
    }

    private int getFileCount() {
        File dir = new File(Config.FOLDER_PATH);

        return getFileCount(dir);
    }

    private int getFileCount(File dir) {
        int count = 0;
        for (File element : Objects.requireNonNull(dir.listFiles())) {
            if (Collector.fileIsImage(element)) {
                count++;
            }
            if (element.isDirectory()) {
                count += getFileCount(element);
            }
        }
        return count;
    }
}
