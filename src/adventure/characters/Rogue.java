package adventure.characters;

/** rogue character w/ dexterity and finesse */
public class Rogue extends GameCharacter {
    /** dexterity for dmg scaling */
    private int dexterity; //dmg stat

    /** creates rogue w/ base stats */
    public Rogue() {
        super("Rogue",80,100,0,4);
        dexterity=10;
    }

    /** gets dexterity
     * @return dexterity
     */
    @Override
    public int getPower() { return dexterity; }

    /** gets stat name
     * @return dexterity label
     */
    @Override
    public String getPowerName() { return "Dexterity"; }

    /** gets resource name
     * @return finesse label
     */
    @Override
    public String getResourceName() { return "Finesse"; }

    /** increases dexterity
     * @param amount dexterity gain
     */
    public void addDexterity(int amount) {
        if(amount>0) dexterity+=amount;
    }
}

