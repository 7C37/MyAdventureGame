package adventure.items;
import java.util.ArrayList;
import java.util.EnumMap;
import adventure.characters.GameCharacter;

/** stores gear and manages worn bonuses */
public class Inventory {
    /** carried gear */
    private final ArrayList<Equipment> bag=new ArrayList<>(); //unequipped items
    /** worn gear by slot */
    private final EnumMap<Equipment.Slot,Equipment> equipped=new EnumMap<>(Equipment.Slot.class); //active slots


    /** creates empty inventory */
    public Inventory() {}

    /** collects new gear
     * @param item collected equipment
     */
    public void add(Equipment item) {
        if(item==null) throw new IllegalArgumentException("Missing item");
        bag.add(item);
    }
    /** applies or removes item bonuses
     * @param player owning character
     * @param item equipment to adjust
     * @param sign +1 for equip, -1 for removal
     */
    private void apply(GameCharacter player,Equipment item,int sign) {
        player.addDefense(item.getDefense()*sign);
        player.addResourceGain(item.getResourceGain()*sign);
    }
    /** wears bag item and returns replaced gear
     * @param index zero-based bag index
     * @param player owning character
     * @return equip result
     */
    public String equip(int index,GameCharacter player) {
        if(index<0||index>=bag.size()) return "Invalid inventory item.";
        Equipment next=bag.remove(index); //selected item
        Equipment old=equipped.put(next.getSlot(),next); //replaced item
        if(old!=null) { apply(player,old,-1); bag.add(old); }
        apply(player,next,1);
        return "Equipped "+next.getName()+".";
    }
    /** removes worn gear to bag
     * @param slotIndex zero-based slot index
     * @param player owning character
     * @return unequip result
     */
    public String unequip(int slotIndex,GameCharacter player) {
        Equipment.Slot[] slots=Equipment.Slot.values(); //slot list
        if(slotIndex<0||slotIndex>=slots.length) return "Invalid equipment slot.";
        Equipment old=equipped.remove(slots[slotIndex]); //removed gear
        if(old==null) return "That slot is empty.";
        apply(player,old,-1);
        bag.add(old);
        return "Unequipped "+old.getName()+".";
    }
    /** lists equipment and bag contents
     * @return inventory summary
     */
    public String describe() {
        StringBuilder msg=new StringBuilder("\nEquipped gear:\n"); //menu text
        Equipment.Slot[] slots=Equipment.Slot.values(); //slot order
        for(int i=0;i<slots.length;i++) {
            Equipment gear=equipped.get(slots[i]); //current slot
            msg.append("U").append(i+1).append(") ").append(slots[i]).append(": ").append(gear==null?"Empty":gear).append("\n");
        }
        msg.append("Inventory:\n");
        if(bag.isEmpty()) msg.append("(empty)\n");
        for(int i=0;i<bag.size();i++) msg.append(i+1).append(") ").append(bag.get(i)).append("\n");
        return msg.toString();
    }
}

