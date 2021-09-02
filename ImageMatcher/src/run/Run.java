package run;

import exceptions.FolderIsEmptyException;
import exceptions.InvalidPathException;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Run {
    public static void main(String[] args) {
        Matcher matcher = new Matcher();
        new Thread(matcher).start();
    }
}

class Matcher implements Runnable {
    ArrayList<Image> images = new ArrayList<>();

    @Override
    public void run() {
        try {
            Config.validateDirPath();
            this.images = new Collector().getAllImages(Config.FOLDER_PATH);

            for (int i = 0; i < this.images.size(); i++) {
                Image image = this.images.get(i);

                for (int other = i + 1; other < this.images.size(); other++) {
                    Image next = this.images.get(other);

                    double compPercent = image.compareTo(next, Config.compFunc);
                    if (compPercent > image.bestMatch.percentage) {
                        image.bestMatch.percentage = compPercent;
                        image.bestMatch.image = next;
                    }

                    if (compPercent > next.bestMatch.percentage) {
                        next.bestMatch.percentage = compPercent;
                        next.bestMatch.image = image;
                    }
                }
            }

            HashMap<Image, Image> matches = new HashMap<>();

            for (Image image : this.images) {
                if (image.bestMatch.percentage >= 90) {
                    Image match = image.bestMatch.image;

                    boolean contains = matches.containsKey(match) && matches.get(match) == image;
                    if (!contains) {
                        matches.put(image, image.bestMatch.image);
                    }
                }
            }

            System.out.println("======== RESULTS ========");
            System.out.println("Matches: " + (matches.isEmpty() ? "No Match found." : matches.size()));

            int i = 1;
            for (Map.Entry<Image, Image> match : matches.entrySet()) {
                Image first = match.getKey();
                Image second = match.getValue();

                System.out.println("#" + i + " | (" + this.format(first.bestMatch.percentage, 2) + "%)");
                System.out.println("> Found a similarity between...");
                System.out.println(">> Name:" + first.file.getName());
                System.out.println(">> Path:" + first.file.getAbsolutePath());
                System.out.println("> and...");
                System.out.println(">> Name:" + second.file.getName());
                System.out.println(">> Path:" + second.file.getAbsolutePath());
                System.out.println("\n");
                i++;
            }
        } catch (FolderIsEmptyException | InvalidPathException e) {
            e.printStackTrace();
        }
    }

    private String format(double d, int p) {
        if (p < 1 || p > 17) {
            throw new InvalidParameterException("Parameter <p> must lie between 1 and 17.");
        }

        DecimalFormat df = new DecimalFormat("##." + "#".repeat(p));
        return df.format(d);
    }
}
