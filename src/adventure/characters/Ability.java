package adventure.characters;

/** stores fixed skill stats
 * @param name skill label
 * @param apCost required ap
 * @param resourceCost required resource
 * @param damage base dmg
 * @param block base shield
 * @param heal base healing
 * @param gain resource gained
 */
public record Ability(String name,int apCost,int resourceCost,int damage,int block,int heal,int gain) {
    /** validates skill stats */
    public Ability {
        if(name==null||name.isBlank()||apCost<1||resourceCost<0||damage<0||block<0||heal<0||gain<0)
            throw new IllegalArgumentException("Invalid ability");
    }
}
