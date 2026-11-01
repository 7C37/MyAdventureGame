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
}

