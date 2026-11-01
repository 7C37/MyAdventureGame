package adventure.combat;
import adventure.characters.GameCharacter;

/** runs combat rules w/o console input */
public class Combat {
    /** active character */
    private final GameCharacter player; //player
    /** active foe */
    private final Enemy enemy; //opponent
    /** temporary player block */
    private int block; //turn shield

    /** creates one fight
     * @param player selected character
     * @param enemy opposing enemy
     */
    public Combat(GameCharacter player,Enemy enemy) {
        if(player==null||enemy==null) throw new IllegalArgumentException("Missing combatant");
        this.player=player;
        this.enemy=enemy;
    }

    /** gets player
     * @return active character
     */
    public GameCharacter getPlayer() { return player; }

    /** gets enemy
     * @return active foe
     */
    public Enemy getEnemy() { return enemy; }

    /** gets player block
     * @return temporary shield
     */
    public int getBlock() { return block; }

    /** checks if battle ended
     * @return true if someone fell
     */
    public boolean isOver() {
        return !player.isAlive()||!enemy.isAlive();
    }

    /** plays basic attack for 1 ap
     * @return action result
     */
    public String attack() {
        if(isOver()) return "Battle already ended.";
        if(!player.spendAP(1)) return "Not enough AP.";
        int dealt=enemy.takeDamage(player.getPower()+4); //hp dmg
        player.gainResource(15);
        return player.getName()+" attacks for "+dealt+" damage and gains resource.";
    }

    /** plays special attack for 2 ap and 20 resource
     * @return action result
     */
    public String special() {
        if(isOver()) return "Battle already ended.";
        if(player.getAP()<2) return "Need 2 AP.";
        if(player.getResource()<20) return "Need 20 "+player.getResourceName()+".";
        player.spendAP(2);
        player.spendResource(20);
        int dealt=enemy.takeDamage(player.getPower()*2+8); //hp dmg
        return player.getName()+" uses a special attack for "+dealt+" damage.";
    }

    /** gains block for 1 ap
     * @return action result
     */
    public String defend() {
        if(isOver()) return "Battle already ended.";
        if(!player.spendAP(1)) return "Not enough AP.";
        int gained=7+player.getDefense(); //scaled block
        block+=gained;
        return player.getName()+" gains "+gained+" block.";
    }

    /** manually ends turn and applies enemy intent
     * @return enemy action result
     */
    public String endTurn() {
        if(isOver()) return "Battle already ended.";
        String result; //enemy action log
        if(enemy.willAttack()) {
            int taken=Math.max(0,enemy.getDamage()-block); //net dmg
            player.takeDamage(taken);
            result=enemy.getName()+" attacks for "+taken+" damage after block.";
        } else {
            enemy.addBlock(8);
            result=enemy.getName()+" gains 8 block.";
        }
        block=0;
        if(player.isAlive()) {
            player.resetAP();
            enemy.nextTurn();
        }
        return result;
    }
}

