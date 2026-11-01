package adventure.console;
import adventure.characters.*;

/** starts adventure game */
public class AdventureApp {
    /** tests playable characters
     * @param args command-line args
     */
    public static void main(String[] args) {
        GameCharacter[] party={new Warrior(),new Rogue(),new Wizard()}; //test characters
        //prints stats via superclass references
        for(GameCharacter member:party) System.out.println(member);
    }
}
