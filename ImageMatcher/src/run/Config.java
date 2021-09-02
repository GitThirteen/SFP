package run;

import exceptions.FolderIsEmptyException;
import exceptions.InvalidPathException;

import java.io.File;

public final class Config {
    /**
     * The ABSOLUTE path to your image folder.
     * (E.g.: C:/Users/User/Desktop/MyImagesToCompare)
     */
    public static final String FOLDER_PATH = "C:\\Users\\Michi\\Desktop\\Homework\\Fate";

    /**
     * The function used to compare images. It is highly suggested to
     * keep it at 'binarize', as this will yield the best results by far.
     * If you're interested in trying out the other ones nonetheless,
     * check out the compareTo() method in the Image class.
     */
    public static final String compFunc = "binarize";

    /**
     * The width the images get resized to for later comparison.
     * Reducing the width might result in a performance boost, but
     * if you're unsure, leave it as is.
     */
    public static final int RESIZE_WIDTH = 1080;

    /**
     * The height the images get resized to for later comparison.
     * Reducing the height might result in a performance boost, but
     * if you're unsure, leave it as is.
     */
    public static final int RESIZE_HEIGHT = 720;

    /**
     * The amount of values saved in the generated histograms (given
     * the 'compFunc' parameter is set to 'histogram')
     * Can be ignored if you're not using histogram comparison, but
     * if you do: Bigger integer = Bigger precision = Bigger computing time.
     */
    public static final int HISTOGRAM_SIZE = 2048;

    /**
     * RECOMMENDED: True
     * Toggles whether a warning message appears before program execution.
     * Disable with caution.
     */
    public static final boolean SHOW_WARNING = true;

    /* >>>>>> IGNORE FROM HERE <<<<<< */

    /**
     *
     * @throws InvalidPathException
     * @throws FolderIsEmptyException
     */
    public static void validateDirPath() throws InvalidPathException, FolderIsEmptyException {
        if (FOLDER_PATH.equals("ENTER FOLDER PATH HERE")) {
            throw new InvalidPathException("Path not set in config. Please set the path of the folder containing the images and try again.");
        }

        File dir = new File(FOLDER_PATH);
        String[] elements = dir.list();
        if (elements == null || elements.length == 0) {
            throw new FolderIsEmptyException("Specified path '" + FOLDER_PATH + "' contains no files. Please make sure the path specified in the config contains images.");
        }
    }
}
