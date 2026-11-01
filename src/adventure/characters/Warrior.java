package adventure.characters;

/** warrior character w/ strength and rage */
public class Warrior extends GameCharacter {
    /** strength for dmg scaling */
    private int strength; //dmg stat

    /** creates warrior w/ base stats */
    public Warrior() {
        super("Warrior",100,100,0,3);
        strength=10;
    }

    /** gets strength
     * @return strength
     */
    @Override
    public int getPower() { return strength; }

    /** gets stat name
     * @return strength label
     */
    @Override
    public String getPowerName() { return "Strength"; }

    /** gets resource name
     * @return rage label
     */
    @Override
    public String getResourceName() { return "Rage"; }

    /** increases strength
     * @param amount strength gain
     */
    public void addStrength(int amount) {
        if(amount>0) strength+=amount;
    }
    
    /** gets warrior's fixed moves
     * @return warrior abilities
     */
    @Override
    public Ability[] getAbilities() {
        return new Ability[]{
            new Ability("Light Attack",1,0,7,0,0,20),
            new Ability("Defend",1,0,0,9,0,12),
            new Ability("Heavy Attack",2,20,20,0,0,0),
            new Ability("Cleave",2,40,16,0,0,0),
            new Ability("Sunder",3,100,45,0,0,0)
        };
    }

}

