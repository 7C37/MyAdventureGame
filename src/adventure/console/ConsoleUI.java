package adventure.console;
import java.util.Scanner;
import adventure.characters.*;
import adventure.combat.*;

/** displays console menus and delegates to game rules */
public class ConsoleUI {
    /** starts character selection and demo battle */
    public void run() {
        Scanner input=new Scanner(System.in); //console input
        GameCharacter player=chooseCharacter(input); //selected class
        if(player==null) {
            System.out.println("Goodbye!");
            return;
        }
        Combat fight=new Combat(player,new Enemy("Cave Crawler",55,11)); //demo battle
        System.out.println("\nDefeat the Cave Crawler!");
        while(!fight.isOver()) {
            System.out.println("\n"+player+" | Block: "+fight.getBlock());
            System.out.println(fight.getEnemy()+" | Intent: "+fight.getEnemy().getIntent());
            System.out.println("1) Attack (1 AP)  2) Defend (1 AP)  3) Special (2 AP, 20 resource)");
            System.out.println("4) End turn  5) Quit");
            String choice=input.nextLine().trim(); //menu choice
            String result; //action message
            switch(choice) {
                case "1": result=fight.attack(); break;
                case "2": result=fight.defend(); break;
                case "3": result=fight.special(); break;
                case "4": result=fight.endTurn(); break;
                case "5": System.out.println("Game closed."); return;
                default: result="Invalid choice. Enter 1-5.";
            }
            System.out.println(result);
        }
        System.out.println(player.isAlive()?"Victory!":"Defeat!");
    }

    /** chooses character w/ input validation
     * @param input shared console input
     * @return character or null to quit
     */
    private GameCharacter chooseCharacter(Scanner input) {
        while(true) {
            System.out.println("\nChoose a character: 1) Warrior  2) Rogue  3) Wizard  4) Quit");
            String choice=input.nextLine().trim(); //class choice
            switch(choice) {
                case "1": return new Warrior();
                case "2": return new Rogue();
                case "3": return new Wizard();
                case "4": return null;
                default: System.out.println("Invalid choice. Enter 1-4.");
            }
        }
    }
}

