package run;

import java.awt.image.BufferedImage;
import java.util.stream.DoubleStream;

class Histogram {
    public double[] R = new double[256];
    public double[] G = new double[256];
    public double[] B = new double[256];

    protected Histogram calculate(BufferedImage image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int clr = image.getRGB(x, y);
                final int rVal = (clr & 0x00ff0000) >> 16;
                final int gVal = (clr & 0x0000ff00) >> 8;
                final int bVal = clr & 0x000000ff;

                this.R[rVal]++;
                this.G[gVal]++;
                this.B[bVal]++;
            }
        }

        // Flattens RGB to 2048 values per histogram
        int px = image.getHeight() * image.getWidth();
        int size = Config.HISTOGRAM_SIZE; // 2048 (default)
        this.R = DoubleStream.of(R).map(e -> e * size / px).toArray();
        this.G = DoubleStream.of(G).map(e -> e * size / px).toArray();
        this.B = DoubleStream.of(B).map(e -> e * size / px).toArray();

        return this;
    }
}
