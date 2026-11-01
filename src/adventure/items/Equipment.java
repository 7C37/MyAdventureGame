package adventure.items;

/** gear w/ slot and stat bonuses */
public class Equipment {
    /** wearable categories */
    public enum Slot { HELMET,ARMOR,LEGGINGS,RING }
    /** gear name */
    private final String name; //item label
    /** assigned slot */
    private final Slot slot; //equip location
    /** defense increase */
    private final int defense; //def bonus
    /** resource gain increase */
    private final int resourceGain; //gain bonus
    /** purchase cost */
    private final int price; //gold cost
    /** elite reward flag */
    private final boolean elite; //rare drop

    /** creates a gear item
     * @param name item label
     * @param slot wearable slot
     * @param defense defense increase
     * @param resourceGain resource gain increase
     * @param price shop price
     * @param elite whether elite-only
     */
    public Equipment(String name,Slot slot,int defense,int resourceGain,int price,boolean elite) {
        if(name==null||name.isBlank()||slot==null||defense<0||resourceGain<0||price<0) throw new IllegalArgumentException("Invalid gear");
        this.name=name;
        this.slot=slot;
        this.defense=defense;
        this.resourceGain=resourceGain;
        this.price=price;
        this.elite=elite;
    }
    /** gets item name
     * @return label
     */
    public String getName() { return name; }
    /** gets wearable slot
     * @return slot
     */
    public Slot getSlot() { return slot; }
    /** gets defense bonus
     * @return defense
     */
    public int getDefense() { return defense; }
    /** gets gain bonus
     * @return resource gain
     */
    public int getResourceGain() { return resourceGain; }
    /** gets purchase cost
     * @return gold price
     */
    public int getPrice() { return price; }
    /** describes item bonuses
     * @return item summary
     */
    @Override
    public String toString() {
        return name+" ["+slot+"] +"+defense+" DEF, +"+resourceGain+" gain"+(elite?" (elite)":"");
    }
}

