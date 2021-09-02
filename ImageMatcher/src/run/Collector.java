package run;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public final class Collector {
    private final ProgressBar PROGRESS_BAR = ProgressBar.generate();

    public ArrayList<Image> getAllImages(String dirPath) {
        ArrayList<Image> images = new ArrayList<>();
        File dir = new File(dirPath);

        return this.getAllImages(dir, images);
    }

    private ArrayList<Image> getAllImages(File dir, ArrayList<Image> images) {
        File[] elements = dir.listFiles();

        if (elements == null) {
            throw new NullPointerException("Parameter <dir> in " + Collector.class.getDeclaredMethods()[1].getName() + "() is null");
        }

        for (File element : elements) {
            if (fileIsImage(element)) {
                Image img = Image.generate(element);
                images.add(img);
                PROGRESS_BAR.clear();
                PROGRESS_BAR.update();
                continue;
            }

            if (element.isDirectory()) {
                this.getAllImages(element, images);
            }
        }

        return images;
    }

    public static boolean fileIsImage(File file) {
        String mimeType = "";
        
        try {
            mimeType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mimeType != null && mimeType.split("/")[0].equals("image");
    }
}
