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
    
    /** gets rogue's fixed moves
     * @return rogue abilities
     */
    @Override
    public Ability[] getAbilities() {
        return new Ability[]{
            new Ability("Stab",1,0,6,0,0,20),
            new Ability("Dodge",1,0,0,10,0,15),
            new Ability("Riposte",2,20,12,7,0,0),
            new Ability("Vital Strike",2,40,25,0,0,0),
            new Ability("Assassinate",3,100,55,0,0,0)
        };
    }

}

