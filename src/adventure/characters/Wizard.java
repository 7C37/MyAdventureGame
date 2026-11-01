package adventure.characters;

/** wizard character w/ intelligence and mana */
public class Wizard extends GameCharacter {
    /** intelligence for dmg scaling */
    private int intelligence; //dmg stat

    /** creates wizard w/ base stats */
    public Wizard() {
        super("Wizard",70,100,100,3);
        intelligence=10;
    }

    /** gets intelligence
     * @return intelligence
     */
    @Override
    public int getPower() { return intelligence; }

    /** gets stat name
     * @return intelligence label
     */
    @Override
    public String getPowerName() { return "Intelligence"; }

    /** gets resource name
     * @return mana label
     */
    @Override
    public String getResourceName() { return "Mana"; }

    /** increases intelligence
     * @param amount intelligence gain
     */
    public void addIntelligence(int amount) {
        if(amount>0) intelligence+=amount;
    }
}

