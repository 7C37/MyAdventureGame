package adventure.characters;

/** shared character stats and actions */
public abstract class GameCharacter {
    /** character name */
    private final String name; //character name
    /** maximum health */
    private int maxHealth; //health limit
    /** current health */
    private int health; //current hp
    /** defense stat */
    private int defense; //block/evade scaling
    /** current resource */
    private int resource; //rage/finesse/mana
    /** resource limit */
    private int maxResource; //resource cap
    /** bonus resource gain */
    private int resourceGain; //flat gain bonus
    /** maximum action points */
    private int maxAP; //ap per turn
    /** current action points */
    private int ap; //remaining ap

    /** creates character w/ base stats */
    protected GameCharacter(String name, int hp, int res, int startRes, int ap) {
        this.name = name;
        maxHealth = hp;
        health = hp;
        maxResource = res;
        resource = startRes;
        maxAP = ap;
        this.ap = ap;
    }

    /** gets class-specific damage stat */
    public abstract int getPower();

    /** gets damage stat name */
    public abstract String getPowerName();

    /** gets resource name */
    public abstract String getResourceName();

    /** takes damage w/o dropping below zero */
    public void takeDamage(int dmg) {
        health = Math.max(0, health - Math.max(0, dmg));
    }

    /** restores hp up to max */
    public void heal(int amount) {
        if (amount > 0) health = Math.min(maxHealth, health + amount);
    }

    /** adds resource w/ flat bonus */
    public void gainResource(int amount) {
        if (amount > 0) resource = Math.min(maxResource, resource + amount + resourceGain);
    }

    /** spends resource if available */
    public boolean spendResource(int cost) {
        if (cost < 0 || cost > resource) return false;
        resource -= cost;
        return true;
    }

    /** spends ap if available */
    public boolean spendAP(int cost) {
        if (cost < 0 || cost > ap) return false;
        ap -= cost;
        return true;
    }

    /** refreshes ap each turn */
    public void resetAP() {
        ap = maxAP;
    }

    /** checks if character is alive */
    public boolean isAlive() {
        return health > 0;
    }

    /** gets character name */
    public String getName() { return name; }
    /** gets current health */
    public int getHealth() { return health; }
    /** gets maximum health */
    public int getMaxHealth() { return maxHealth; }
    /** gets defense */
    public int getDefense() { return defense; }
    /** gets current resource */
    public int getResource() { return resource; }
    /** gets maximum resource */
    public int getMaxResource() { return maxResource; }
    /** gets current ap */
    public int getAP() { return ap; }
    /** gets maximum ap */
    public int getMaxAP() { return maxAP; }

    /** adds defense stat */
    public void addDefense(int amount) {
        defense = Math.max(0, defense + amount);
    }

    /** adds resource gain bonus */
    public void addResourceGain(int amount) {
        resourceGain = Math.max(0, resourceGain + amount);
    }

    /** increases maximum health */
    public void addMaxHealth(int amount) {
        if (amount <= 0) return;
        maxHealth += amount;
        health += amount;
    }

    /** increases action points */
    public void addAP(int amount) {
        if (amount <= 0) return;
        maxAP += amount;
        ap += amount;
    }

    /** displays character stats */
    public String toString() {
        return name + " | HP: " + health + "/" + maxHealth
            + " | " + getResourceName() + ": " + resource + "/" + maxResource
            + " | AP: " + ap + "/" + maxAP
            + " | " + getPowerName() + ": " + getPower()
            + " | Defense: " + defense;
    }


    /** gets fixed character moves
     * @return five class abilities
     */
    public abstract Ability[] getAbilities();

}
