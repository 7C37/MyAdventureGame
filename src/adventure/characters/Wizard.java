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
    
    /** gets wizard's fixed moves
     * @return wizard abilities
     */
    @Override
    public Ability[] getAbilities() {
        return new Ability[]{
            new Ability("Bonk",1,0,5,0,0,18),
            new Ability("Heal",1,20,0,0,18,0),
            new Ability("Fireball",2,25,20,0,0,0),
            new Ability("Lightning Strike",2,40,30,0,0,0),
            new Ability("Black Hole",3,75,45,0,0,0)
        };
    }

}

