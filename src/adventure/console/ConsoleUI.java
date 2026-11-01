package adventure.console;
import java.util.Scanner;
import adventure.characters.*;
import adventure.combat.Combat;
import adventure.game.AdventureGame;
import adventure.items.Equipment;
import adventure.world.NodeType;

/** handles console menus and user input */
public class ConsoleUI {
    /** main loop flag */
    private boolean running=true; //true until quit

    /** launches character selection and game loop */
    public void run() {
        Scanner input=new Scanner(System.in); //console input
        GameCharacter player=chooseCharacter(input); //selected hero
        if(player==null) { System.out.println("Goodbye!"); return; }
        AdventureGame game=new AdventureGame(player); //active run
        System.out.println("Adventure started! Clear all three stages.");
        try {
            while(running&&!game.isFinished()) {
                if(game.getCombat()!=null) battleMenu(input,game);
                else if(game.isShopping()) shopMenu(input,game);
                else nodeMenu(input,game);
            }
            if(running) System.out.println(game.hasWon()?"You won the game!":"Game over.");
        } catch(java.util.NoSuchElementException e) { System.out.println("Input closed."); }
    }
    /** chooses one of three characters
     * @param input shared scanner
     * @return hero or null on quit
     */
    private GameCharacter chooseCharacter(Scanner input) {
        while(true) {
            System.out.println("Choose character: 1) Warrior 2) Rogue 3) Wizard 4) Quit");
            if(!input.hasNextLine()) return null;
            String choice=input.nextLine().trim(); //menu input
            switch(choice) {
                case "1": return new Warrior();
                case "2": return new Rogue();
                case "3": return new Wizard();
                case "4": return null;
                default: System.out.println("Invalid choice. Enter 1-4.");
            }
        }
    }
    /** shows paths and inventory entry
     * @param input shared scanner
     * @param game current run
     */
    private void nodeMenu(Scanner input,AdventureGame game) {
        System.out.println("\n=== "+game.getStage().getName()+" | Path "+(game.getRow()+1)+"/4 ===");
        System.out.println(game.getPlayer()+" | Gold: "+game.getGold());
        NodeType[] options=game.getOptions(); //open paths
        for(int i=0;i<options.length;i++) System.out.println((i+1)+") "+options[i].getLabel());
        System.out.println("Enter node number, I for inventory, or Q to quit:");
        String choice=input.nextLine().trim(); //node input
        if(choice.equalsIgnoreCase("q")) {
            System.out.println("Game closed.");
            running=false;
            return;
        }
        if(choice.equalsIgnoreCase("i")) { inventoryMenu(input,game); return; }
        try { System.out.println(game.chooseNode(Integer.parseInt(choice))); }
        catch(NumberFormatException e) { System.out.println("Invalid node. Enter a number."); }
    }
    /** displays gear and changes worn slots
     * @param input shared scanner
     * @param game current run
     */
    private void inventoryMenu(Scanner input,AdventureGame game) {
        System.out.println(game.getInventory().describe());
        System.out.println("Item # to equip, U1-U4 to unequip, or B to return:");
        String choice=input.nextLine().trim().toUpperCase(); //gear command
        if(choice.equals("B")) return;
        try {
            if(choice.startsWith("U")) System.out.println(game.unequipItem(Integer.parseInt(choice.substring(1))-1));
            else System.out.println(game.equipItem(Integer.parseInt(choice)-1));
        } catch(NumberFormatException e) { System.out.println("Invalid inventory command."); }
    }
    /** displays health and gear purchases
     * @param input shared scanner
     * @param game current run
     */
    private void shopMenu(Scanner input,AdventureGame game) {
        System.out.println("\nShop | Gold: "+game.getGold()+" | HP: "+game.getPlayer().getHealth()+"/"+game.getPlayer().getMaxHealth());
        System.out.println("1) Healing supplies (+25 HP): 15 gold");
        Equipment[] stock=game.getShopItems(); //available equipment
        for(int i=0;i<stock.length;i++) System.out.println((i+2)+") "+stock[i]+" - "+stock[i].getPrice()+" gold");
        System.out.println("6) Leave shop");
        String choice=input.nextLine().trim(); //shop choice
        if(choice.equals("1")) System.out.println(game.buySupplies());
        else if(choice.equals("6")) System.out.println(game.leaveShop());
        else {
            try { System.out.println(game.buyGear(Integer.parseInt(choice)-2)); }
            catch(NumberFormatException e) { System.out.println("Invalid shop choice."); }
        }
    }
    /** displays battle and applies actions
     * @param input shared scanner
     * @param game current run
     */
    private void battleMenu(Scanner input,AdventureGame game) {
        Combat fight=game.getCombat(); //active fight
        System.out.println("\n"+fight.getPlayer()+" | Block: "+fight.getBlock());
        System.out.println(fight.getEnemy()+" | Intent: "+fight.getEnemy().getIntent());
        System.out.println("1) Attack (1 AP)  2) Defend (1 AP)  3) Special (2 AP, 20 resource)");
        System.out.println("4) End turn  5) Quit");
        String choice=input.nextLine().trim(); //combat input
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



