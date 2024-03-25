import view.*;

/**
 * Точка входа в программу.
 */
public class Application {
    public static void main(String[] args) {
        Window w = new Window(args[0]);
        w.run();
    }
}
