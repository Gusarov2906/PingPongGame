package MainPackage;

/**
 * Main class of program
 */
public class Main {
    public static PingPongFrame pingPongFrame;
    public static int width = 1280;
    public static int height = 720;
    public static boolean isRunning = false;
    public static boolean pressedW = false;
    public static boolean pressedS = false;
    public static boolean pressedUpArrow = false;
    public static boolean pressedDownArrow = false;
    public static boolean isReset = false;

    /**
     * Method where starts program
     * @param args - arguments
     */
    public static void main(String[] args) {
        pingPongFrame = new PingPongFrame();
    }
}
