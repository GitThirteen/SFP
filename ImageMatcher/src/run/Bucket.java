package run;

import java.awt.image.BufferedImage;
import java.util.HashMap;

final class Bucket {
    public HashMap<BucketColors, Integer> values = new HashMap<>();

    protected Bucket calculate(BufferedImage image) {
        for (BucketColors color : BucketColors.values()) {
            this.values.put(color, 0);
        }

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                final int clr = image.getRGB(x, y);
                final int rVal = (clr & 0x00ff0000) >> 16;
                final int gVal = (clr & 0x0000ff00) >> 8;
                final int bVal = clr & 0x000000ff;

                if (rVal > 200 && gVal < 75 && bVal < 75) this.values.put(BucketColors.RED, this.values.get(BucketColors.RED) + 1);
                else if (rVal < 75 && gVal < 75 && bVal > 200) this.values.put(BucketColors.BLUE, this.values.get(BucketColors.BLUE) + 1);
                else if (rVal < 75 && gVal > 200 && bVal < 75) this.values.put(BucketColors.GREEN, this.values.get(BucketColors.GREEN) + 1);
                else if (rVal > 220 && gVal > 220 && bVal > 220) this.values.put(BucketColors.WHITE, this.values.get(BucketColors.WHITE) + 1);
                else if (rVal < 35 && gVal < 35 && bVal < 35) this.values.put(BucketColors.BLACK, this.values.get(BucketColors.BLACK) + 1);
                else this.values.put(BucketColors.NONE, this.values.get(BucketColors.NONE) + 1);
            }
        }

        return this;
    }
}

enum BucketColors {
    RED,
    BLUE,
    GREEN,
    WHITE,
    BLACK,
    NONE
}
