package adventure.characters;

/** shared stats and actions for playable characters */
public abstract class GameCharacter {
    /** character name */
    private final String name; //character name
    /** maximum health */
    private int maxHealth; //health cap
    /** current health */
    private int health; //current hp
    /** defense stat */
    private int defense; //block scaling
    /** current resource */
    private int resource; //rage/finesse/mana
    /** maximum resource */
    private int maxResource; //resource cap
    /** resource gain bonus */
    private int resourceGain; //flat gain bonus
    /** maximum action points */
    private int maxAP; //ap per turn
    /** current action points */
    private int ap; //remaining ap

    /** creates character w/ base stats
     * @param name character name
     * @param hp starting and max hp
     * @param res maximum resource
     * @param startRes starting resource
     * @param ap starting and max ap
     */
    protected GameCharacter(String name,int hp,int res,int startRes,int ap) {
        this.name=name;
        maxHealth=hp;
        health=hp;
        maxResource=res;
        resource=startRes;
        maxAP=ap;
        this.ap=ap;
    }
    /** gets class damage stat
     * @return strength, dexterity, or intelligence
     */
    public abstract int getPower();
    /** gets damage stat label
     * @return stat name
     */
    public abstract String getPowerName();
    /** gets character resource label
     * @return resource name
     */
    public abstract String getResourceName();
    /** gets fixed character moves
     * @return five class abilities
     */
    public abstract Ability[] getAbilities();

    /** reduces hp w/ zero minimum
     * @param dmg incoming damage
     */
    public void takeDamage(int dmg) {
        health=Math.max(0,health-Math.max(0,dmg));
    }
    /** restores hp up to maximum
     * @param amount hp restored
     */
    public void heal(int amount) {
        if(amount>0) health=Math.min(maxHealth,health+amount);
    }
    /** gains resource w/ bonus
     * @param amount base resource gain
     */
    public void gainResource(int amount) {
        if(amount>0) resource=Math.min(maxResource,resource+amount+resourceGain);
    }
    /** spends available resource
     * @param cost resource required
     * @return true if paid
     */
    public boolean spendResource(int cost) {
        if(cost<0||cost>resource) return false;
        resource-=cost;
        return true;
    }
    /** spends available ap
     * @param cost ap required
     * @return true if paid
     */
    public boolean spendAP(int cost) {
        if(cost<0||cost>ap) return false;
        ap-=cost;
        return true;
    }
    /** refreshes ap for next turn */
    public void resetAP() {
        ap=maxAP;
    }
    /** checks survival
     * @return true if hp above zero
     */
    public boolean isAlive() {
        return health>0;
    }
    /** gets character name
     * @return name
     */
    public String getName() { return name; }
    /** gets current hp
     * @return health
     */
    public int getHealth() { return health; }
    /** gets maximum hp
     * @return hp cap
     */
    public int getMaxHealth() { return maxHealth; }
    /** gets defense stat
     * @return defense
     */
    public int getDefense() { return defense; }
    /** gets current resource
     * @return resource
     */
    public int getResource() { return resource; }
    /** gets maximum resource
     * @return resource cap
     */
    public int getMaxResource() { return maxResource; }
    /** gets current ap
     * @return remaining ap
     */
    public int getAP() { return ap; }
    /** gets maximum ap
     * @return ap cap
     */
    public int getMaxAP() { return maxAP; }

    /** adjusts defense w/ zero minimum
     * @param amount defense change
     */
    public void addDefense(int amount) {
        defense=Math.max(0,defense+amount);
    }
    /** adjusts resource gain bonus
     * @param amount gain change
     */
    public void addResourceGain(int amount) {
        resourceGain=Math.max(0,resourceGain+amount);
    }
    /** increases maximum and current hp
     * @param amount hp increase
     */
    public void addMaxHealth(int amount) {
        if(amount<=0) return;
        maxHealth+=amount;
        health+=amount;
    }
    /** increases maximum and current ap
     * @param amount ap increase
     */
    public void addAP(int amount) {
        if(amount<=0) return;
        maxAP+=amount;
        ap+=amount;
    }
    /** describes character stats
     * @return formatted character status
     */
    @Override
    public String toString() {
        return name+" | HP: "+health+"/"+maxHealth
            +" | "+getResourceName()+": "+resource+"/"+maxResource
            +" | AP: "+ap+"/"+maxAP
            +" | "+getPowerName()+": "+getPower()
            +" | Defense: "+defense;
    }
}

