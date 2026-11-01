package adventure.combat;
import adventure.characters.*;

/** applies player skills and enemy intents */
public class Combat {
    /** selected hero */
    private final GameCharacter player; //active hero
    /** selected enemy */
    private final Enemy enemy; //active foe
    /** active temporary shield */
    private int block; //player block

    /** starts battle and refreshes ap
     * @param player active hero
     * @param enemy active foe
     */
    public Combat(GameCharacter player,Enemy enemy) {
        if(player==null||enemy==null) throw new IllegalArgumentException("Missing combatant");
        this.player=player;
        this.enemy=enemy;
        player.resetAP();
    }
    /** gets hero
     * @return current player
     */
    public GameCharacter getPlayer() { return player; }
    /** gets foe
     * @return current enemy
     */
    public Enemy getEnemy() { return enemy; }
    /** gets current shield
     * @return block value
     */
    public int getBlock() { return block; }
    /** checks combat result
     * @return true if either side defeated
     */
    public boolean isOver() { return !player.isAlive()||!enemy.isAlive(); }

    /** executes selected skill after cost checks
     * @param index zero-based skill slot
     * @return action summary or rejection
     */
    public String playAbility(int index) {
        if(isOver()) return "Battle already ended.";
        Ability[] skills=player.getAbilities(); //fixed skill list
        if(index<0||index>=skills.length) return "Invalid ability number.";
        Ability skill=skills[index]; //selected move
        if(player.getAP()<skill.apCost()) return "Not enough AP.";
        if(player.getResource()<skill.resourceCost()) return "Not enough "+player.getResourceName()+".";
        player.spendAP(skill.apCost());
        player.spendResource(skill.resourceCost());
        String result=player.getName()+" uses "+skill.name()+"."; //combat log
        if(skill.damage()>0) {
            int dealt=enemy.takeDamage(skill.damage()+player.getPower()); //scaled dmg
            result+=" Deals "+dealt+" damage.";
        }
        if(skill.block()>0) {
            int shield=skill.block()+player.getDefense(); //scaled block
            block+=shield;
            result+=" Gains "+shield+" block.";
        }
        if(skill.heal()>0) {
            int before=player.getHealth(); //hp before healing
            player.heal(skill.heal()+player.getPower()/2);
            result+=" Heals "+(player.getHealth()-before)+" HP.";
        }
        if(skill.gain()>0) {
            player.gainResource(skill.gain());
            result+=" Gains "+player.getResourceName()+".";
        }
        return result;
    }

    /** manually resolves enemy action and refreshes ap
     * @return enemy action summary
     */
    public String endTurn() {
        if(isOver()) return "Battle already ended.";
        String result; //enemy action message
        if(enemy.willAttack()) {
            int taken=Math.max(0,enemy.getDamage()-block); //unblocked dmg
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
