package adventure.game;
import adventure.characters.GameCharacter;
import adventure.combat.Combat;
import adventure.world.*;

/** handles stage progress, gold, and node rewards */
public class AdventureGame {
    /** current player */
    private final GameCharacter player; //chosen hero
    /** current stage */
    private Stage stage=new Stage(1); //zone
    /** active battle */
    private Combat combat; //null outside fights
    /** active encounter type */
    private NodeType encounter; //reward tier
    /** next path row */
    private int row; //0-3, boss last
    /** saved currency */
    private int gold; //shop funds
    /** shop state */
    private boolean shopping; //in shop node
    /** adventure ended */
    private boolean finished; //win or loss
    /** final victory */
    private boolean won; //all bosses cleared

    /** starts game w/ selected character
     * @param player chosen character
     */
    public AdventureGame(GameCharacter player) {
        if(player==null||!player.isAlive()) throw new IllegalArgumentException("Invalid character");
        this.player=player;
    }

    /** gets available nodes
     * @return current node choices
     */
    public NodeType[] getOptions() { return stage.getOptions(row); }

    /** visits chosen node w/o skipping rows
     * @param choice 1-based node selection
     * @return action result
     */
    public String chooseNode(int choice) {
        if(finished||combat!=null||shopping) return "Cannot choose a node right now.";
        NodeType[] options=getOptions(); //valid branches
        if(choice<1||choice>options.length) return "Invalid node. Try again.";
        NodeType type=options[choice-1]; //selected location
        switch(type) {
            case ENEMY,ELITE,BOSS -> {
                encounter=type;
                combat=new Combat(player,stage.createEnemy(type));
                return "Encounter: "+combat.getEnemy().getName()+"!";
            }
            case TREASURE -> {
                int found=20+stage.getNumber()*10; //gold loot
                gold+=found;
                row++;
                return "You found "+found+" gold.";
            }
            case REST -> {
                int before=player.getHealth(); //hp before rest
                player.heal(20+stage.getNumber()*5);
                row++;
                return "You rested and recovered "+(player.getHealth()-before)+" HP.";
            }
            case SHOP -> {
                shopping=true;
                return "Shopkeeper: healing supplies cost 15 gold.";
            }
        }
        return "Unknown node.";
    }

    /** buys healing without charging on failure
     * @return purchase result
     */
    public String buySupplies() {
        if(!shopping) return "You are not in a shop.";
        if(gold<15) return "Not enough gold.";
        if(player.getHealth()==player.getMaxHealth()) return "Health is already full.";
        int before=player.getHealth(); //hp before purchase
        gold-=15;
        player.heal(25);
        return "Spent 15 gold and restored "+(player.getHealth()-before)+" HP.";
    }

    /** exits shop and advances path
     * @return exit message
     */
    public String leaveShop() {
        if(!shopping) return "You are not in a shop.";
        shopping=false;
        row++;
        return "You leave the shop.";
    }

    /** grants fight rewards or ends run
     * @return battle outcome
     */
    public String resolveBattle() {
        if(combat==null||!combat.isOver()) return "The battle is still ongoing.";
        combat=null;
        if(!player.isAlive()) {
            finished=true;
            return "Defeat! Your adventure ends in "+stage.getName()+".";
        }
        int reward=(encounter==NodeType.ELITE?35:encounter==NodeType.BOSS?45:15)+stage.getNumber()*5; //win gold
        gold+=reward;
        if(encounter==NodeType.BOSS) {
            if(stage.getNumber()==3) {
                won=true;
                finished=true;
                return "Victory! You defeated the Undead King and completed the adventure! +"+reward+" gold.";
            }
            String cleared=stage.getName(); //completed zone
            stage=new Stage(stage.getNumber()+1);
            row=0;
            return "Boss defeated! +"+reward+" gold. Cleared "+cleared+". Next: "+stage.getName()+".";
        }
        row++;
        return "Enemy defeated! Collected "+reward+" gold.";
    }

    /** gets character
     * @return chosen hero
     */
    public GameCharacter getPlayer() { return player; }
    /** gets zone
     * @return active stage
     */
    public Stage getStage() { return stage; }
    /** gets battle
     * @return active combat or null
     */
    public Combat getCombat() { return combat; }
    /** gets row
     * @return path progress
     */
    public int getRow() { return row; }
    /** gets currency
     * @return current gold
     */
    public int getGold() { return gold; }
    /** checks shop mode
     * @return true in shop
     */
    public boolean isShopping() { return shopping; }
    /** checks end of adventure
     * @return true after victory/defeat
     */
    public boolean isFinished() { return finished; }
    /** checks final victory
     * @return true if all stages cleared
     */
    public boolean hasWon() { return won; }
}

