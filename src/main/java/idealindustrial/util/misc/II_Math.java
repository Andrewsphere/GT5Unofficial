package idealindustrial.util.misc;

public class II_Math {
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int sign(int a) {
        if (a < 0) {
            return -1;
        }
        if (a > 0) {
            return 1;
        }
        return 0;
    }

    public static final double NINETY_DEGREES_RAD = Math.toRadians(90);
}
