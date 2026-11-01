package adventure.world;
import adventure.combat.Enemy;

/** one zone w/ paths and themed encounters */
public class Stage {
    /** zone number */
    private final int number; //difficulty tier
    /** zone name */
    private final String name; //zone label
    /** boss name */
    private final String boss; //stage boss
    /** regular enemy name */
    private final String normal; //regular foe
    /** elite enemy name */
    private final String elite; //elite foe

    /** sets zone data
     * @param number zone number (1-3)
     */
    public Stage(int number) {
        if(number<1||number>3) throw new IllegalArgumentException("Invalid stage");
        this.number=number;
        switch(number) {
            case 1 -> { name="The Underground"; normal="Cave Crawler"; elite="Stone Guardian"; boss="The Golem"; }
            case 2 -> { name="The Lost Ruins"; normal="Ruin Stalker"; elite="Ancient Sentinel"; boss="Twin Gargoyles"; }
            default -> { name="The Tomb"; normal="Skeleton"; elite="Crypt Reaper"; boss="The Undead King"; }
        }
    }

    /** gets next node choices
     * @param row current path row
     * @return available locations
     */
    public NodeType[] getOptions(int row) {
        return switch(row) {
            case 0 -> new NodeType[]{NodeType.ENEMY,NodeType.TREASURE};
            case 1 -> new NodeType[]{NodeType.ENEMY,NodeType.REST};
            case 2 -> new NodeType[]{NodeType.ELITE,NodeType.SHOP};
            case 3 -> new NodeType[]{NodeType.BOSS};
            default -> new NodeType[0];
        };
    }

    /** creates foe scaled by stage
     * @param type encounter node
     * @return new enemy instance
     */
    public Enemy createEnemy(NodeType type) {
        return switch(type) {
            case ENEMY -> new Enemy(normal,26+12*number,4+2*number);
            case ELITE -> new Enemy(elite,40+15*number,5+3*number);
            case BOSS -> new Enemy(boss,60+25*number,7+3*number);
            default -> throw new IllegalArgumentException("Not a combat node");
        };
    }

    /** gets stage name
     * @return stage label
     */
    public String getName() { return name; }

    /** gets stage number
     * @return zone tier
     */
    public int getNumber() { return number; }
}

