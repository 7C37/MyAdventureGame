package adventure.combat;

/** enemy w/ repeating attack and block intents */
public class Enemy {
    /** enemy name */
    private final String name; //enemy label
    /** max hp */
    private final int maxHealth; //health cap
    /** base attack */
    private final int damage; //attack dmg
    /** current hp */
    private int health; //current hp
    /** current block */
    private int block; //enemy shield
    /** current turn */
    private int turn=1; //intent cycle

    /** creates enemy
     * @param name enemy label
     * @param hp starting hp
     * @param damage attack dmg
     */
    public Enemy(String name,int hp,int damage) {
        if(name==null||name.isBlank()||hp<=0||damage<0) throw new IllegalArgumentException("Invalid enemy stats");
        this.name=name;
        maxHealth=hp;
        health=hp;
        this.damage=damage;
    }

    /** takes dmg after block
     * @param amount incoming dmg
     * @return hp lost
     */
    public int takeDamage(int amount) {
        int hit=Math.max(0,amount); //incoming dmg
        int absorbed=Math.min(block,hit); //blocked dmg
        block-=absorbed;
        int dealt=Math.min(health,hit-absorbed); //actual hp loss
        health-=dealt;
        return dealt;
    }

    /** adds enemy block
     * @param amount block gain
     */
    public void addBlock(int amount) {
        if(amount>0) block+=amount;
    }

    /** checks next action
     * @return true for attack
     */
    public boolean willAttack() { return turn%3!=0; }

    /** describes visible intent
     * @return intent label
     */
    public String getIntent() {
        return willAttack()?"Attack ("+damage+" dmg)":"Defend (+8 block)";
    }

    /** advances enemy intent */
    public void nextTurn() { turn++; }

    /** checks enemy survival
     * @return true if alive
     */
    public boolean isAlive() { return health>0; }

    /** gets enemy name
     * @return name
     */
    public String getName() { return name; }

    /** gets attack power
     * @return base dmg
     */
    public int getDamage() { return damage; }

    /** shows enemy status
     * @return health and block
     */
    @Override
    public String toString() {
        return name+" | HP: "+health+"/"+maxHealth+" | Block: "+block;
    }
}

