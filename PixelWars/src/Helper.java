import java.awt.*;
import java.util.Random;

public class Helper {
    public static int randomArrayItem(int[] array) {
        int random = new Random().nextInt(array.length);

        return array[random];
    }

    public static String randomArrayItem(String[] array) {
        int random = new Random().nextInt(array.length);

        return array[random];
    }

    public static Color generateRandomColor() {
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }
}
