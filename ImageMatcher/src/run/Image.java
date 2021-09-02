package run;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Represents an image.
 */
public final class Image {
    public File file;

    public Histogram histogram;
    public Bucket bucket;
    public int[][] binary;

    public Match bestMatch;

    private Image() { }

    /**
     * A factory method for generating a new image object for further processing. Acts as a
     * facade, calling all necessary methods to fill out the fields of a valid image object,
     * as seen {@link Image here}.
     *
     * @param file A file
     * @return A generated image object
     */
    public static Image generate(File file) {
        Image image = new Image();
        image.file = file;

        switch (Config.compFunc) {
            case "histogram" -> {
                image.histogram = image.generateHistogram();
            }
            case "bucket" -> {
                image.bucket = image.generateBucket();
            }
            case "binarize" -> {
                image.binary = image.generateBinary();
            }
            default -> {
                throw new InvalidParameterException("Expected 'histogram', 'bucket' or 'binarize' when generating object of type 'Image', found '" + Config.compFunc + "' instead.");
            }
        }

        image.bestMatch = image.createMatch();

        return image;
    }

    /**
     * Compares the image to another image.
     *
     * @param   image     The second image this image gets compared with
     * @param   function  Determines the comparison function used
     *
     * || "histogram" > Compares the histogram of both images.
     * || "bucket" > Compares different buckets (red, green, blue, black, white) of both images.
     * || "binarize" (suggested) > Compares the binarized versions of the images against each other.
     *
     * @return Returns the similarity between both images as percentage (double) between 0 and 100.
     */
    public double compareTo(Image image, String function) {
        return switch (function) {
            case "histogram" -> Comparer.histogram(this, image);
            case "bucket" -> Comparer.bucketComp(this, image);
            case "binarize" -> Comparer.binarize(this, image);
            default -> throw new IllegalArgumentException("No function with name '" + function + "' found.");
        };
    }

    /**
     * Converts a file object to a BufferedImage.
     *
     * @return A BufferedImage (.jpg)
     */
    private BufferedImage fileToImage() {
        if (this.file == null) {
            return null;
        }

        try {
            BufferedImage image = ImageIO.read(this.file);
            ImageIO.write(image, "JPG", this.file);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Resizes the BufferedImage of this instance to the width and height provided in the Config file.
     * Inspired by a code snippet by Nam Ha Minh, seen here: https://www.codejava.net/java-se/graphics/how-to-resize-images-in-java
     *
     * @return A BufferedImage resized to the dimensions provided in the config
     */
    private BufferedImage getResizedImage(BufferedImage original) {
        if (original == null) {
            return null;
        }

        int resizedWidth = Config.RESIZE_WIDTH;
        int resizedHeight = Config.RESIZE_HEIGHT;

        BufferedImage output = new BufferedImage(resizedWidth, resizedHeight, original.getType());
        Graphics2D g2d = output.createGraphics();
        g2d.drawImage(original, 0, 0, resizedWidth, resizedHeight, null);
        g2d.dispose();

        return output;
    }

    private Match createMatch() {
        return new Match();
    }

    private Histogram generateHistogram() {
        BufferedImage image = this.fileToImage();
        if (image == null) {
            return null;
        }

        return new Histogram().calculate(image);
    }

    private Bucket generateBucket() {
        BufferedImage image = this.fileToImage();
        if (image == null) {
            return null;
        }

        return new Bucket().calculate(this.getResizedImage(image));
    }

    private int[][] generateBinary() {
        BufferedImage image = this.fileToImage();
        if (image == null) {
            return null;
        }

        int resizedHeight = Config.RESIZE_HEIGHT;
        int resizedWidth = Config.RESIZE_WIDTH;

        BufferedImage resized = this.getResizedImage(image);

        int[][] binary = new int[resizedWidth][resizedHeight];
        int threshold = this.detThreshold(image); //this.detThreshold(this.imageToGreyscale(image));

        // TODO?
        for (int y = 0; y < resizedHeight; y++) {
            for (int x = 0; x < resizedWidth; x++) {
                final int clr = resized.getRGB(x, y);
                final int rVal = (clr & 0x00ff0000) >> 16;
                final int gVal = (clr & 0x0000ff00) >> 8;
                final int bVal = clr & 0x000000ff;

                int greyVal = (rVal + gVal + bVal) / 3;
                binary[x][y] = (greyVal >= threshold) ? 1 : 0;
            }
        }

        return binary;
    }

    private BufferedImage imageToGreyscale(BufferedImage image) {
        BufferedImage output = new BufferedImage(Config.RESIZE_WIDTH, Config.RESIZE_HEIGHT, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = output.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        
        return output;
    }

    private int detThreshold(BufferedImage gsImage) {
        // Generate histogram
        int[] histogram = new int[256];

        for (int y = 0; y < gsImage.getHeight(); y++) {
            for (int x = 0; x < gsImage.getWidth(); x++) {
                final int val = 0xFF & gsImage.getRGB(x, y);
                histogram[val]++;
            }
        }

        /*for (int i = 0; i < 256; i++) {
            System.out.println("" + i + ": " + histogram[i]);
        }*/

        float sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }

        int total = gsImage.getHeight() * gsImage.getWidth();
        double sumBG = 0;
        double weightBG = 0;
        double max = 0;

        int threshold = 0;
        for (int i = 0; i < 255; i++) {
            // Wikipedia implementation
            /*double weightFG = total - weightBG;

            if (weightBG > 0 && weightFG > 0) {
                double meanFG = (sum - sumBG) / weightFG;
                double meanBG = sumBG / weightBG;

                double val = weightBG * weightFG * Math.pow(meanBG - meanFG, 2);

                if (val >= max) {
                    threshold = i;
                    max = val;
                }
            }

            weightBG += histogram[i];
            sumBG += i * histogram[i];*/

            weightBG += histogram[i];
            if (weightBG == 0) continue;

            double weightFG = total - weightBG;
            if (weightFG == 0) break;

            sumBG += i * histogram[i];

            double meanFG = (sum - sumBG) / weightFG;
            double meanBG = sumBG / weightBG;

            double val = weightBG * weightFG * Math.pow(meanBG - meanFG, 2);

            if (val >= max) {
                threshold = i;
                max = val;
            }
        }

        return threshold;
    }

    private static class Comparer {
        private static double histogram(Image image1, Image image2) {
            double[][] ownRGB = new double[][] { image1.histogram.R, image1.histogram.G, image1.histogram.B };
            double[][] otherRGB = new double[][] { image2.histogram.R, image2.histogram.G, image2.histogram.B };

            double td = 0;
            for (int channel = 0; channel < 3; channel++) {
                for (int val = 0; val < 256; val++) {
                    double expected = ownRGB[channel][val];
                    double observed = otherRGB[channel][val];

                    double deviation = expected != 0 ? Math.abs(1 - (observed / expected)) : 0;
                    td += deviation;
                }
            }

            return 100 - (td / (3 * 256));
        }

        private static double bucketComp(Image image1, Image image2) {
            double td = 0;

            for (BucketColors color : BucketColors.values()) {
                int v1 = image1.bucket.values.get(color);
                int v2 = image2.bucket.values.get(color);

                td += (v1 < v2) ? (v1 == 0) ? 1 : v1 / (double) v2 : (v2 == 0) ? 1 : v2 / (double) v1;
            }

            return td * 100 / BucketColors.values().length;
        }

        private static double binarize(Image image1, Image image2) {
            int resizedHeight = Config.RESIZE_HEIGHT;
            int resizedWidth = Config.RESIZE_WIDTH;

            int matches = 0;

            for (int y = 0; y < resizedHeight; y++) {
                for (int x = 0; x < resizedWidth; x++) {
                    if (image1.binary[x][y] == image2.binary[x][y]) {
                        matches++;
                    }
                }
            }

            return (matches / (double) (resizedHeight * resizedWidth)) * 100;
        }
    }
}

final class Match {
    public double percentage;
    public Image image;

    Match() {
        this.percentage = 0;
        this.image = null;
    }
}
