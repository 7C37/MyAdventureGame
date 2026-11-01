package adventure.console;

/** launches the adventure */
public class AdventureApp {
    /** creates application launcher */
    public AdventureApp() {}

    /** starts console interface
     * @param args unused command-line args
     */
    public static void main(String[] args) {
        new ConsoleUI().run();
    }
}
