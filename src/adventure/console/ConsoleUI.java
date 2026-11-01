
package adventure.console;
import java.util.Scanner;
import adventure.characters.*;
import adventure.combat.Combat;
import adventure.game.AdventureGame;
import adventure.world.NodeType;

/** reads menus and displays the adventure */
public class ConsoleUI {
    /** true while user has not quit */
    private boolean running=true; //console loop flag

    /** starts game loop and switches between menus */
    public void run() {
        Scanner input=new Scanner(System.in); //console input
        GameCharacter player=chooseCharacter(input); //selected hero
        if(player==null) { System.out.println("Goodbye!"); return; }
        AdventureGame game=new AdventureGame(player); //run state
        System.out.println("Adventure started! Clear all three stages."); 
        while(running&&!game.isFinished()) {
            if(game.getCombat()!=null) battleMenu(input,game);
            else if(game.isShopping()) shopMenu(input,game);
            else nodeMenu(input,game);
        }
        if(running) System.out.println(game.hasWon()?"You won the game!":"Game over.");
    }


    /** selects character w/ input validation
     * @param input shared scanner
     * @return hero or null if quitting
     */
    private GameCharacter chooseCharacter(Scanner input) {
        while(true) {
            System.out.println("Choose character: 1) Warrior 2) Rogue 3) Wizard 4) Quit");
            if(!input.hasNextLine()) return null;
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


    /** displays path choices and visits one
     * @param input shared scanner
     * @param game current run
     */
    private void nodeMenu(Scanner input,AdventureGame game) {
        System.out.println("\n=== "+game.getStage().getName()+" | Path "+(game.getRow()+1)+"/4 ===");
        System.out.println(game.getPlayer()+" | Gold: "+game.getGold());
        NodeType[] options=game.getOptions(); //open paths
        for(int i=0;i<options.length;i++) System.out.println((i+1)+") "+options[i].getLabel());
        System.out.println("Enter node number, or Q to quit:");
        String choice=input.nextLine().trim(); //node input
        if(choice.equalsIgnoreCase("q")) {
            System.out.println("Game closed.");
            running=false;
            return;
        }
        try { System.out.println(game.chooseNode(Integer.parseInt(choice))); }
        catch(NumberFormatException e) { System.out.println("Invalid node. Enter a number."); }
    }

    /** offers temporary healing shop
     * @param input shared scanner
     * @param game current run
     */
    private void shopMenu(Scanner input,AdventureGame game) {
        System.out.println("\nShop | Gold: "+game.getGold()+" | HP: "+game.getPlayer().getHealth()+"/"+game.getPlayer().getMaxHealth());
        System.out.println("1) Buy supplies (15 gold, +25 HP)  2) Leave shop");
        String choice=input.nextLine().trim(); //shop choice
        switch(choice) {
            case "1": System.out.println(game.buySupplies()); break;
            case "2": System.out.println(game.leaveShop()); break;
            default: System.out.println("Invalid choice. Enter 1-2.");
        }
    }

    /** displays fight and dispatches action
     * @param input shared scanner
     * @param game current run
     */
    private void battleMenu(Scanner input,AdventureGame game) {
        Combat fight=game.getCombat(); //active fight
        System.out.println("\n"+fight.getPlayer()+" | Block: "+fight.getBlock());
        System.out.println(fight.getEnemy()+" | Intent: "+fight.getEnemy().getIntent());
        System.out.println("1) Attack (1 AP)  2) Defend (1 AP)  3) Special (2 AP, 20 resource)");
        System.out.println("4) End turn  5) Quit");
        String choice=input.nextLine().trim(); //combat action
        String result=switch(choice) {
            case "1" -> fight.attack();
            case "2" -> fight.defend();
            case "3" -> fight.special();
            case "4" -> fight.endTurn();
            case "5" -> "quit";
            default -> "Invalid choice. Enter 1-5.";
        };
        if(result.equals("quit")) {
            System.out.println("Game closed.");
            running=false;
            return;
        }
        System.out.println(result);
        if(fight.isOver()) System.out.println(game.resolveBattle());
    }
}


