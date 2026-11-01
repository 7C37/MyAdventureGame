package adventure.game;
import adventure.characters.GameCharacter;
import adventure.combat.Combat;
import adventure.items.*;
import adventure.world.*;

/** handles stage progress, gold, and equipment */
public class AdventureGame {
    /** current player */
    private final GameCharacter player; //chosen hero
    /** equipment inventory */
    private final Inventory inventory=new Inventory(); //owned gear
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
                return "Shopkeeper: buy supplies or equipment.";
            }
        }
        return "Unknown node.";
    }
    /** gets gear available for purchase
     * @return shop equipment list
     */
    public Equipment[] getShopItems() {
        int tier=stage.getNumber(); //stage difficulty
        return new Equipment[]{
            new Equipment("Guard Helmet",Equipment.Slot.HELMET,tier+1,0,14+tier*4,false),
            new Equipment("Plate Armor",Equipment.Slot.ARMOR,2*tier+2,0,22+tier*4,false),
            new Equipment("Swift Leggings",Equipment.Slot.LEGGINGS,tier+1,1,18+tier*4,false),
            new Equipment("Focus Ring",Equipment.Slot.RING,0,tier*2,18+tier*5,false)
        };
    }
    /** purchases one item for inventory
     * @param index zero-based shop choice
     * @return purchase result
     */
    public String buyGear(int index) {
        if(!shopping) return "You are not in a shop.";
        Equipment[] items=getShopItems(); //shop stock
        if(index<0||index>=items.length) return "Invalid shop item.";
        Equipment item=items[index]; //selected gear
        if(gold<item.getPrice()) return "Not enough gold.";
        gold-=item.getPrice();
        inventory.add(item);
        return "Purchased "+item.getName()+" for "+item.getPrice()+" gold. Open inventory to equip it.";
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
    /** equips a carried item outside fights
     * @param index zero-based bag index
     * @return action result
     */
    public String equipItem(int index) {
        if(combat!=null||shopping||finished) return "Manage equipment from the map.";
        return inventory.equip(index,player);
    }
    /** takes off gear outside fights
     * @param slot zero-based gear slot
     * @return action result
     */
    public String unequipItem(int slot) {
        if(combat!=null||shopping||finished) return "Manage equipment from the map.";
        return inventory.unequip(slot,player);
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
        String extra=""; //elite loot message
        if(encounter==NodeType.ELITE) {
            String[] names={"Guardian Sigil","Sentinel Sigil","Reaper Sigil"}; //rare names
            int tier=stage.getNumber(); //elite tier
            Equipment drop=new Equipment(names[tier-1],Equipment.Slot.RING,2*tier,3*tier,0,true); //elite-only ring
            inventory.add(drop);
            extra=" Elite drop: "+drop.getName()+".";
        }
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
        return "Enemy defeated! Collected "+reward+" gold."+extra;
    }
    /** gets character
     * @return chosen hero
     */
    public GameCharacter getPlayer() { return player; }
    /** gets inventory
     * @return gear bag and slots
     */
    public Inventory getInventory() { return inventory; }
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


